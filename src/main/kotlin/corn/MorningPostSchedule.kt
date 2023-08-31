package top.phj233.corn

import cn.hutool.cron.CronUtil
import top.phj233.config.Config

/**
 * MorningPost 定时任务
 * @author phj233
 * @since 2023/8/30 14:14
 * @version
 */
class MorningPostSchedule {
    /**
     * 启动定时任务
     */
    fun start() {
        CronUtil.setMatchSecond(true)
        CronUtil.schedule(Config.moyu_time, MoyuTask())
        CronUtil.schedule(Config.news_time, NewsTask())
        CronUtil.schedule(Config.history_time, HistoryTask())
        CronUtil.start()
    }

    /**
     * 重启定时任务
     */
    fun restart() {
        disable()
        start()
    }

    /**
     * 停止定时任务
     */
    fun disable() {
        CronUtil.getScheduler().isStarted.let { if (it) CronUtil.stop() }
    }
}