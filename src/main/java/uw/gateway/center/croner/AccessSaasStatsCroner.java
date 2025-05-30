package uw.gateway.center.croner;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import uw.common.dto.ResponseData;
import uw.common.util.DateUtils;
import uw.dao.DaoManager;
import uw.gateway.center.entity.AccessSaasStats;
import uw.gateway.center.helper.AccessLogStatsHelper;
import uw.task.TaskCroner;
import uw.task.entity.TaskContact;
import uw.task.entity.TaskCronerConfig;
import uw.task.entity.TaskCronerLog;

import java.util.Date;
import java.util.List;

/**
 * SAAS访问日志统计定时任务。
 */
@Component
public class AccessSaasStatsCroner extends TaskCroner {

    private final DaoManager dao = DaoManager.getInstance();

    /**
     * 运行任务。
     *
     * @param taskCronerLog
     */
    @Override
    public String runTask(TaskCronerLog taskCronerLog) throws Exception {
        Date now = new Date();
        Date startDate = DateUtils.beginOfYesterday(now);
        Date endDate = DateUtils.endOfYesterday(now);
        ResponseData<List<AccessSaasStats>> statsResponse = AccessLogStatsHelper.saasStats(startDate, endDate);
        if (!statsResponse.isSuccess()) {
            return statsResponse.getMsg();
        }
        List<AccessSaasStats> list = statsResponse.getData();
        List<List<AccessSaasStats>> lists = Lists.partition(list, 100);
        for (List<AccessSaasStats> sublist : lists) {
            sublist.stream().forEach(stats -> {
                stats.setId(dao.getSequenceId(AccessSaasStats.class));
                stats.setStatsDate(startDate);
                stats.setCreateDate(now);
            });
            dao.save(sublist);
        }
        return "任务执行成功！统计SAAS数量：" + list.size();
    }

    /**
     * 初始化配置信息。
     */
    @Override
    public TaskCronerConfig initConfig() {
        return TaskCronerConfig.builder("SAAS访问日志统计定时任务", "0 35 0 * * ?").runType(TaskCronerConfig.RUN_TYPE_SINGLETON).build();
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
