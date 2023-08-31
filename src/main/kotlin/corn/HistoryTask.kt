package top.phj233.corn

import cn.hutool.core.img.ImgUtil
import cn.hutool.cron.task.Task
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.messageChainOf
import top.phj233.common.MessageUtil
import top.phj233.config.Config
import java.net.URL

/**
 * 历史上的今天定时任务
 * @author phj233
 * @since 2023/8/31 22:24
 * @version
 */
class HistoryTask : Task {
    override fun execute() {
        val historyImage = if (MessageUtil.isImage(Config.history_api)) {
            ImgUtil.getImage(URL(Config.history_api))
        } else {
            TODO()
        }
        if (historyImage != null) {
            Config.group.forEach { group ->
                runBlocking {
                    val historyImgStream = ImgUtil.toStream(historyImage, "png")
                    val image = MessageUtil.uploadImageToGroup(group, historyImgStream)
                    if (image != null) {
                        val messageChain = messageChainOf(PlainText("历史上的今天"), image)
                        MessageUtil.sendMessageToGroup(group, messageChain)
                    } else {
                        MessageUtil.sendMessageToGroup(group, "上传历史图片失败")
                    }
                }
            }
        }
    }
}