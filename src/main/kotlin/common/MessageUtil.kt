package top.phj233.common

import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.MessageChain
import top.phj233.MorningPost.BOT_INSTANCE
import java.io.InputStream
import java.net.URL

/**
 * 消息发送工具类
 * @author phj233
 * @since 2023/8/30 14:29
 * @version
 */
object MessageUtil {
    fun isImage(url: String): Boolean {
        val connection = URL(url).openConnection()
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")
        val contentType = connection.getHeaderField("Content-Type")
        return contentType.startsWith("image/")
    }

    suspend fun uploadImageToGroup(group: Long, stream: InputStream): Image? {
        stream.use {
            return BOT_INSTANCE.getGroup(group)?.uploadImage(stream)
        }
    }

    suspend fun sendMessageToGroup(group: Long, messageChain: MessageChain) {
        BOT_INSTANCE.getGroup(group)?.sendMessage(messageChain)
    }

    suspend fun sendMessageToGroup(group: Long, text: String) {
        BOT_INSTANCE.getGroup(group)?.sendMessage(text)
    }
}