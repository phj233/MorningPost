package top.phj233.corn

import cn.hutool.core.img.ImgUtil
import cn.hutool.cron.task.Task
import cn.hutool.http.HttpRequest
import cn.hutool.json.JSONUtil
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.messageChainOf
import top.phj233.common.MessageUtil
import top.phj233.config.Config
import java.net.URL

/**
 * @author phj233
 * @since 2023/8/29 14:17
 * @version
 */
class MoyuTask : Task {
    override fun execute() {
        val moyuData = HttpRequest.get(Config.moyu_api).setFollowRedirects(true).execute().body()
        val moyuUrl = JSONUtil.parseObj(moyuData).getJSONObject("data").getStr("moyu_url")
        val image = ImgUtil.getImage(URL(moyuUrl)) ?: ImgUtil.getImage(URL(Config.moyu_api))
        if (image == null) {
            MessageUtil.sendGroupTextMessage("无法获取摸鱼图,请检查${Config.moyu_api}是否可用")
            return
        }
        val imgStream = ImgUtil.toStream(image, "png")
        Bot.instances.filter { it.isOnline }.forEach { bot ->
            Config.group.forEach { group ->
                runBlocking {
                    val imageId = bot.getGroup(group)?.uploadImage(imgStream)?.imageId
                    if (imageId != null) {
                        val messageChain = messageChainOf(PlainText("摸鱼时间到啦"), Image(imageId))
                        MessageUtil.sendGroupMessageChain(messageChain)
                    }else {
                        MessageUtil.sendGroupTextMessage("图片上传失败")
                    }
                }
            }
        }
    }
}