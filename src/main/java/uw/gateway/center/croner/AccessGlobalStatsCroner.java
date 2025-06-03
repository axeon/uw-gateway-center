package uw.gateway.center.croner;

import org.springframework.stereotype.Component;
import uw.common.dto.ResponseData;
import uw.common.util.DateUtils;
import uw.common.util.SystemClock;
import uw.dao.DaoManager;
import uw.gateway.center.entity.AccessGlobalStats;
import uw.gateway.center.helper.AccessLogStatsHelper;
import uw.task.TaskCroner;
import uw.task.entity.TaskContact;
import uw.task.entity.TaskCronerConfig;
import uw.task.entity.TaskCronerLog;

import java.util.Date;

/**
 * 全局访问日志统计定时任务。
 */
@Component
public class AccessGlobalStatsCroner extends TaskCroner {

    private final DaoManager dao = DaoManager.getInstance();

    /**
     * 运行任务。
     *
     * @param taskCronerLog
     */
    @Override
    public String runTask(TaskCronerLog taskCronerLog) throws Exception {
        Date now = SystemClock.nowDate();
        Date startDate = DateUtils.beginOfYesterday(now);
        Date endDate = DateUtils.endOfYesterday(now);
        ResponseData<AccessGlobalStats> statsResponse = AccessLogStatsHelper.globalStats(startDate, endDate);
        if (!statsResponse.isSuccess()) {
            return statsResponse.getMsg();
        }
        AccessGlobalStats accessGlobalStats = statsResponse.getData();
        accessGlobalStats.setId(dao.getSequenceId(AccessGlobalStats.class));
        accessGlobalStats.setStatsDate(startDate);
        accessGlobalStats.setCreateDate(now);
        var savedEntity = dao.save(accessGlobalStats);
        if (!savedEntity.isSuccess()) {
            return savedEntity.getMsg();
        }
        return "任务执行成功！";
    }

    /**
     * 初始化配置信息。
     */
    @Override
    public TaskCronerConfig initConfig() {
        return TaskCronerConfig.builder("全局访问日志统计定时任务", "0 30 0 * * ?").runType(TaskCronerConfig.RUN_TYPE_SINGLETON).build();
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
