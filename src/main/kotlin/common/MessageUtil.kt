package top.phj233.common

import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import net.mamoe.mirai.message.data.Message
import top.phj233.config.Config
import java.io.ByteArrayInputStream

/**
 * 消息发送工具类
 * @author phj233
 * @since 2023/8/30 14:29
 * @version
 */
object MessageUtil {
    fun sendGroupTextMessage(text : String) {
        Bot.instances.filter { it.isOnline }.forEach { bot ->
            Config.group.forEach { group ->
                runBlocking {
                    bot.getGroup(group)?.sendMessage(text)
                }
            }
        }
    }

    fun sendGroupImageMessage(image : ByteArrayInputStream) {
        Bot.instances.filter { it.isOnline }.forEach { bot ->
            Config.group.forEach { group ->
                runBlocking {
                    bot.getGroup(group)?.sendImage(image)
                    image.close()
                }
            }
        }
    }

    fun sendGroupMessageChain(messageChain : Message) {
        Bot.instances.filter { it.isOnline }.forEach { bot ->
            Config.group.forEach { group ->
                runBlocking {
                    bot.getGroup(group)?.sendMessage(messageChain)
                }
            }
        }
    }
}