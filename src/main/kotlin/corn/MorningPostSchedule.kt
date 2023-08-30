package top.phj233.corn

import cn.hutool.cron.CronUtil
import top.phj233.config.Config

/**
 * @author phj233
 * @since 2023/8/30 14:14
 * @version
 */
class MorningPostSchedule {
    fun start() {
        CronUtil.setMatchSecond(true)
        CronUtil.schedule(Config.moyu_time, MoyuTask())
        CronUtil.schedule(Config.news_time, NewsTask())
        CronUtil.start()
    }

    fun disable() {
        CronUtil.getScheduler().isStarted.let { if (it) CronUtil.stop() }
    }
}