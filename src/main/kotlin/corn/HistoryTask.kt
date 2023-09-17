package top.phj233.corn

import cn.hutool.core.img.ImgUtil
import cn.hutool.cron.task.Task
import kotlinx.coroutines.launch
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.messageChainOf
import top.phj233.MorningPost
import top.phj233.common.MessageUtil
import top.phj233.common.MessageUtil.sendMessageToGroup
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
        val historyApi = Config.history_api
        val historyImage = if (MessageUtil.isImage(historyApi)) {
            ImgUtil.getImage(URL(historyApi))
        } else {
            TODO()
        }
        if (historyImage != null) {
            Config.group.forEach { group ->
                MorningPost.launch {
                    val historyImgStream = ImgUtil.toStream(historyImage, "jpg")
                    val image = MessageUtil.uploadImageToGroup(group, historyImgStream)
                    if (image != null) {
                        val messageChain = messageChainOf(PlainText("历史上的今天"), image)
                        sendMessageToGroup(group, messageChain)
                    } else {
                        sendMessageToGroup(group, "上传历史图片失败")
                    }
                }
            }
        }
    }
}