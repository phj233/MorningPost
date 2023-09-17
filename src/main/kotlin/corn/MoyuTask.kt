package top.phj233.corn

import cn.hutool.core.img.ImgUtil
import cn.hutool.cron.task.Task
import cn.hutool.http.HttpRequest
import cn.hutool.json.JSONUtil
import kotlinx.coroutines.launch
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.messageChainOf
import top.phj233.MorningPost
import top.phj233.common.MessageUtil
import top.phj233.common.MessageUtil.sendMessageToGroup
import top.phj233.config.Config
import java.net.URL

/**
 * 摸鱼人日历定时任务
 * @author phj233
 * @since 2023/8/29 14:17
 * @version
 */
class MoyuTask : Task {
    override fun execute() {
        val moyuApi = Config.moyu_api
        val moyuImage = if (MessageUtil.isImage(moyuApi)) {
            ImgUtil.getImage(URL(moyuApi))
        } else {
            val moyuData = HttpRequest.get(moyuApi)
                .setFollowRedirects(true)
                .execute()
                .body()
            val moyuImgUrl = JSONUtil.parseObj(moyuData).getObj("data").let {
                JSONUtil.parseObj(it).getStr("moyu_url")
            }
            ImgUtil.getImage(URL(moyuImgUrl))
        }
        if (moyuImage != null) {
            Config.group.forEach { group ->
                MorningPost.launch {
                    val moyuImgStream = ImgUtil.toStream(moyuImage, "png")
                    val image = MessageUtil.uploadImageToGroup(group, moyuImgStream)
                    if (image != null) {
                        val messageChain = messageChainOf(PlainText("今日摸鱼人日历"), image)
                        sendMessageToGroup(group, messageChain)
                    } else {
                        sendMessageToGroup(group, "上传摸鱼图片失败")
                    }
                }
            }
        }
    }
}