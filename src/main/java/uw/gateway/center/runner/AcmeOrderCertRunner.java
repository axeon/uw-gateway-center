package uw.gateway.center.runner;

import org.springframework.stereotype.Component;
import uw.common.dto.ResponseData;
import uw.common.util.DateUtils;
import uw.gateway.center.acme.AcmeHelper;
import uw.gateway.center.entity.MscAcmeCert;
import uw.gateway.center.helper.DingSendService;
import uw.task.TaskData;
import uw.task.TaskRunner;
import uw.task.entity.TaskContact;
import uw.task.entity.TaskRunnerConfig;
import uw.task.exception.TaskPartnerException;

/**
 * ACME证书下单任务。
 */
@Component
public class AcmeOrderCertRunner extends TaskRunner<Long, ResponseData<MscAcmeCert>> {

    /**
     * 执行任务。
     * 业务层面的异常请根据实际情况手动Throw TaskException:
     * 目前支持的异常:
     * 1. TaskPartnerException 任务合作方异常，此异常会引发任务重试。
     * 2. TaskDataException 任务数据异常，此异常不会引发任务重试。
     * ！！！其它未捕获异常一律认为是程序异常，不会引发任务重试。
     *
     * @param taskData 数据
     * @return 指定的返回对象
     * @throws Exception 异常
     */
    @Override
    public ResponseData<MscAcmeCert> runTask(TaskData<Long, ResponseData<MscAcmeCert>> taskData) throws Exception {
        ResponseData<MscAcmeCert> responseData = AcmeHelper.fetchCert(taskData.getTaskParam());
        MscAcmeCert mscAcmeCert = responseData.getData();
        String title = "域名[" + mscAcmeCert.getDomainName() + "]SSL证书更新" + (responseData.isSuccess() ? "成功！" : "失败!");
        StringBuilder sb = new StringBuilder();
        sb.append("### ").append(title).append("\n");
        if (mscAcmeCert.getActiveDate() != null) {
            sb.append("### 证书开始时间：").append(DateUtils.dateToString(mscAcmeCert.getActiveDate(), DateUtils.DATE_TIME)).append("\n");
        }
        if (mscAcmeCert.getExpireDate() != null) {
            sb.append("### 证书过期时间：").append(DateUtils.dateToString(mscAcmeCert.getExpireDate(), DateUtils.DATE_TIME)).append("\n");
        }
        sb.append("### 证书申请日志：\n");
        sb.append(mscAcmeCert.getCertLog());
        DingSendService.sendNotify(title, sb.toString());
        if (responseData.isNotSuccess()) {
            throw new TaskPartnerException("证书下单失败！" + mscAcmeCert.getCertLog());
        }
        return responseData;
    }

    /**
     * 初始化配置信息
     *
     * @return TaskRunnerConfig配置
     */
    @Override
    public TaskRunnerConfig initConfig() {
        TaskRunnerConfig config = new TaskRunnerConfig();
        config.setTaskName("ACME证书下单任务");
        config.setTaskDesc("ACME证书下单任务");
        //设定限速类型
        config.setRateLimitType(TaskRunnerConfig.RATE_LIMIT_LOCAL_TASK);
        //设定限速秒数
        config.setRateLimitTime(1);
        //设定限速次数
        config.setRateLimitValue(1);
        //设定限速超时等待时间
        config.setRateLimitWait(900);
        //设定因为对方接口错，重试的次数，默认为0
        config.setRetryTimesByPartner(3);
        //设定因为超过限速的错误，重试的次数，默认为0
        config.setRetryTimesByOverrated(3);
        //并发数，会开启几个并发进程处理任务。
        config.setConsumerNum(1);
        //预取数，大批量任务可以设置为5，一般任务建议设置为1
        config.setPrefetchNum(1);
        //总失败率百分比数值
        config.setAlertFailRate(10);
        //程序失败率百分比数值
        config.setAlertFailProgramRate(10);
        //接口失败率百分比数值
        config.setAlertFailPartnerRate(10);
        //程序失败率百分比数值
        config.setAlertFailConfigRate(10);
        //队列排队超
        config.setAlertQueueOversize(1000);
        //队列排队超时ms数
        config.setAlertQueueTimeout(600000);
        //限速等待超时ms数
        config.setAlertWaitTimeout(10000);
        //运行超时ms数
        config.setAlertRunTimeout(1000);
        return config;
    }

    /**
     * 初始化联系人信息
     *
     * @return TaskContact联系人信息
     */
    @Override
    public TaskContact initContact() {
        return TaskContact.builder("axeon").email("23231269@qq.com").build();
    }
}
