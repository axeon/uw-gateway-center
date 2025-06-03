package uw.gateway.center.croner;

import org.springframework.stereotype.Component;
import uw.common.app.constant.CommonState;
import uw.common.dto.ResponseData;
import uw.common.util.DateUtils;
import uw.dao.DaoManager;
import uw.gateway.center.acme.AcmeHelper;
import uw.gateway.center.entity.MscAcmeCert;
import uw.gateway.center.helper.DingSendService;
import uw.gateway.center.runner.AcmeOrderCertRunner;
import uw.task.TaskCroner;
import uw.task.TaskData;
import uw.task.TaskFactory;
import uw.task.entity.TaskContact;
import uw.task.entity.TaskCronerConfig;
import uw.task.entity.TaskCronerLog;

import java.util.Date;

@Component
public class AcmeOrderCroner extends TaskCroner {

    private final DaoManager dao = DaoManager.getInstance();

    /**
     * 运行任务。
     *
     * @param taskCronerLog
     */
    @Override
    public String runTask(TaskCronerLog taskCronerLog) throws Exception {
        //需要提前15天续期。
        Date now = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
        dao.list(MscAcmeCert.class, "select * from msc_acme_cert where state=? and expire_date<=?", new Object[]{CommonState.ENABLED.getValue(), now}).onSuccess(dataList -> {
            for (MscAcmeCert mscAcmeCert : dataList) {
                if (AcmeHelper.isSupportAcmeOrder(mscAcmeCert.getDomainId())) {
                    TaskData<Long, ResponseData<MscAcmeCert>> taskData = TaskData.builder(AcmeOrderCertRunner.class, mscAcmeCert.getDomainId()).build();
                    TaskFactory.getInstance().sendToQueue(taskData);
                } else {
                    // 不支持ACME续期的证书，发送钉钉消息。
                    long daysDiff = DateUtils.daysDiff(now, mscAcmeCert.getExpireDate());
                    StringBuilder info = new StringBuilder().append("域名[").append(mscAcmeCert.getDomainName()).append("]SSL证书");
                    if (daysDiff > 0) {
                        //还有几天过期
                        info.append("还有[").append(daysDiff).append("]天过期，请注意提前更新证书！！！");
                    } else {
                        //已经过期了
                        info.append("已经过期[").append(Math.abs(daysDiff)).append("]天，请立即更新证书！！！");
                    }
                    String title = info.toString();
                    String content = "### " + title;
                    DingSendService.sendAlert(title, content);
                }
            }
        });
        return "";
    }

    /**
     * 初始化配置信息。
     */
    @Override
    public TaskCronerConfig initConfig() {
        return TaskCronerConfig.builder("ACME证书更新定时任务", "0 30 3 * * ?").runType(TaskCronerConfig.RUN_TYPE_SINGLETON).build();
    }

    /**
     * 初始化联系人信息。
     *
     * @return
     */
    @Override
    public TaskContact initContact() {
        return TaskContact.builder("axeon").email("23231269@qq.com").build();
    }

}
