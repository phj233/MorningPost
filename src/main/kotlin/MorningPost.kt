package top.phj233

import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotOnlineEvent
import top.phj233.command.MorningPostCommand
import top.phj233.config.Config
import top.phj233.corn.MorningPostSchedule

object MorningPost : KotlinPlugin(
    JvmPluginDescription(
        id = "top.phj233.morning-post",
        name = "MorningPost",
        version = "0.1.0",
    ) {
        author("phj233")
        info("指定时间发送 摸鱼人日历 以及 每日60s早报")
    }
) {
    lateinit var BOT_INSTANCE: Bot
    override fun onEnable() {
        Config.reload()
        CommandManager.registerCommand(MorningPostCommand)
        GlobalEventChannel.subscribeAlways<BotOnlineEvent> {
            if (Config.enable) {
                BOT_INSTANCE = it.bot
                MorningPostSchedule().start()
                logger.info("MorningPost 已启动!!")
            }else{
                logger.info("MorningPost 已禁用!!")
            }
        }
    }

    override fun onDisable() {
        MorningPostSchedule().disable()
        logger.info("MorningPost 已停止!!")
    }
}