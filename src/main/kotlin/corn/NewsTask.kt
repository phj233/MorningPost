package top.phj233.corn

import cn.hutool.core.img.ImgUtil
import cn.hutool.cron.task.Task
import cn.hutool.http.HttpUtil
import cn.hutool.json.JSONUtil
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.messageChainOf
import top.phj233.common.MessageUtil.isImage
import top.phj233.common.MessageUtil.sendMessageToGroup
import top.phj233.common.MessageUtil.uploadImageToGroup
import top.phj233.config.Config
import java.net.URL

/**
 * 每日60s早报定时任务
 * @author phj233
 * @since 2023/8/29 14:35
 * @version
 */
class NewsTask : Task {
    override fun execute() {
        val newsApi = Config.news_api
        val newsApiToken = Config.news_api_token
        val newsImage = if (isImage(newsApi)) {
            ImgUtil.getImage(URL(newsApi))
        } else {
            val newsData = HttpUtil.createGet(newsApi, true)
                .form(mapOf(Pair("token", newsApiToken)))
                .form(mapOf(Pair("format", "json")))
                .addHeaders(mapOf(Pair("Content-Type", "application/json;charset=UTF-8")))
                .execute()
                .body()
            val newsImgUrl = JSONUtil.parseObj(newsData).getJSONObject("data").getStr("image")
            ImgUtil.getImage(URL(newsImgUrl))
        }
        if (newsImage != null) {
            Config.group.forEach { group ->
                runBlocking {
                    val newsImgStream = ImgUtil.toStream(newsImage, "png")
                    val image = uploadImageToGroup(group, newsImgStream)
                    if (image != null) {
                        val messageChain = messageChainOf(PlainText("今日60s早报"), image)
                        sendMessageToGroup(group, messageChain)
                    } else {
                        sendMessageToGroup(group, "上传图片失败")
                    }
                }
            }
        }
    }
}