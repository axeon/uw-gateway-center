package uw.gateway.center.runner;

import org.springframework.stereotype.Component;
import uw.common.dto.ResponseData;
import uw.gateway.center.acme.AcmeHelper;
import uw.gateway.center.entity.MscAcmeDeployLog;
import uw.gateway.center.helper.DingSendService;
import uw.task.TaskData;
import uw.task.TaskRunner;
import uw.task.entity.TaskContact;
import uw.task.entity.TaskRunnerConfig;
import uw.task.exception.TaskPartnerException;

/**
 * ACME证书部署任务。
 */
@Component
public class AcmeDeployCertRunner extends TaskRunner<AcmeDeployCertRunner.DeployParam, ResponseData<MscAcmeDeployLog>> {

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
    public ResponseData<MscAcmeDeployLog> runTask(TaskData<DeployParam, ResponseData<MscAcmeDeployLog>> taskData) throws Exception {
        DeployParam deployParam = taskData.getTaskParam();
        ResponseData<MscAcmeDeployLog> responseData = AcmeHelper.deployCert(deployParam.getDeployId(), deployParam.getCertId());
        MscAcmeDeployLog deployLog = responseData.getData();

        String title = deployLog.getDeployInfo();
        StringBuilder sb = new StringBuilder();
        sb.append("### ").append(title).append("\n");
        sb.append("### 证书部署日志：\n");
        sb.append(deployLog.getDeployLog());
        DingSendService.sendNotify(title, sb.toString());

        if (responseData.isNotSuccess()) {
            throw new TaskPartnerException("部署证书失败！" + deployLog.getDeployLog());
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
        config.setTaskName("ACME证书部署任务");
        config.setTaskDesc("ACME证书部署任务");
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

    /**
     * 任务参数.
     */
    public static class DeployParam {
        /**
         * 证书ID.
         */
        private long certId;

        /**
         * 部署ID.
         */
        private long deployId;

        public DeployParam(long certId, long deployId) {
            this.certId = certId;
            this.deployId = deployId;
        }

        public DeployParam() {
        }

        public long getCertId() {
            return certId;
        }

        public void setCertId(long certId) {
            this.certId = certId;
        }

        public long getDeployId() {
            return deployId;
        }

        public void setDeployId(long deployId) {
            this.deployId = deployId;
        }
    }
}
