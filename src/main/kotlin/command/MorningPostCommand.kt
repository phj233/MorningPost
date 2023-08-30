package top.phj233.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.message.data.ForwardMessageBuilder
import net.mamoe.mirai.message.data.PlainText
import top.phj233.MorningPost
import top.phj233.config.Config

object MorningPostCommand : CompositeCommand(
    owner = MorningPost,
    "morningpost",
    description = "MorningPost 插件指令"
) {
    @SubCommand("help")
    suspend fun help(sender: CommandSender){
        val helpText = """
            |MorningPost 插件指令
            | /morningpost help - 显示帮助
            | /morningpost enable <true|false> - 启用/禁用插件
            | /morningpost admin <add|remove|list> <qq> - 添加/删除/查看管理员
            | /morningpost group <add|remove|list> <group> - 添加/删除/查看发送群组
            | /morningpost moyu <time|api> <value> - 设置摸鱼人日历发送时间/设置摸鱼人日历api
            | /morningpost news <time|api|token> <value> - 设置每日60s早报发送时间/api/token
            | /morningpost reload - 重载插件
        """.trimMargin()
        sender.user?.let {
            val message = ForwardMessageBuilder(it).add(sender.bot!!.id, sender.bot!!.nick, PlainText(helpText)).build()
            sender.sendMessage(message)
        }
    }

    @SubCommand("enable")
    suspend fun enable(sender: CommandSender){
        val enable = Config.enable
        sender.sendMessage("MorningPost 目前已${if (enable) "启用" else "禁用"}")
    }

    @SubCommand("enable")
    suspend fun enable(sender: CommandSender, enable: Boolean){
        Config.enable = enable
        sender.sendMessage("MorningPost 已${if (enable) "启用" else "禁用"}")
    }

    @SubCommand("admin")
    suspend fun admin(sender: CommandSender, action:String){
        when(action){
            "add" -> {
                when(val qq = sender.user?.id){
                    null -> sender.sendMessage("请在群组中使用此指令")
                    in Config.admin -> sender.sendMessage("管理员 $qq ,已存在")
                    else -> {
                        Config.admin.add(qq)
                        sender.sendMessage("添加管理员 $qq ,成功")
                    }
                }
            }
            "remove" -> {
                when(val qq = sender.user?.id){
                    null -> sender.sendMessage("请在群组中使用此指令")
                    !in Config.admin -> sender.sendMessage("管理员 $qq ,不存在")
                    else -> {
                        Config.admin.remove(qq)
                        sender.sendMessage("删除管理员 $qq ,成功")
                    }
                }
            }
            "list" -> {
                sender.sendMessage("管理员列表: ${Config.admin}")
            }
            else -> {
                sender.sendMessage("未知指令")
            }
        }
    }

    @SubCommand("admin")
    suspend fun admin(sender: CommandSender, action:String, qq:Long){
        when(action){
            "add" -> {
                when(qq){
                    in Config.admin -> sender.sendMessage("管理员 $qq ,已存在")
                    else -> {
                        Config.admin.add(qq)
                        sender.sendMessage("添加管理员 $qq ,成功")
                    }
                }
            }
            "remove" -> {
                when(qq){
                    !in Config.admin -> sender.sendMessage("管理员 $qq ,不存在")
                    else -> {
                        Config.admin.remove(qq)
                        sender.sendMessage("删除管理员 $qq ,成功")
                    }
                }
            }
            else -> {
                sender.sendMessage("未知指令")
            }
        }
    }

    @SubCommand("group")
    suspend fun group(sender: CommandSender, action:String){
        when(action){
            "add" -> {
                when(val group = sender.subject?.id){
                    null -> sender.sendMessage("请在群组中使用此指令")
                    in Config.group -> sender.sendMessage("群组 $group ,已存在")
                    else -> {
                        Config.group.add(group)
                        sender.sendMessage("添加群组 $group ,成功")
                    }
                }
            }
            "remove" -> {
                when(val group = sender.subject?.id){
                    null -> sender.sendMessage("请在群组中使用此指令")
                    !in Config.group -> sender.sendMessage("群组 $group ,不存在")
                    else -> {
                        Config.group.remove(group)
                        sender.sendMessage("删除群组 $group ,成功")
                    }
                }
            }
            "list" -> {
                sender.sendMessage("群组列表: ${Config.group}")
            }
            else -> {
                sender.sendMessage("未知指令")
            }
        }
    }

    @SubCommand("group")
    suspend fun group(sender: CommandSender, action:String, group:Long){
        when(action){
            "add" -> {
                when(group){
                    in Config.group -> sender.sendMessage("群组 $group ,已存在")
                    else -> {
                        Config.group.add(group)
                        sender.sendMessage("添加群组 $group ,成功")
                    }
                }
            }
            "remove" -> {
                when(group){
                    !in Config.group -> sender.sendMessage("群组 $group ,不存在")
                    else -> {
                        Config.group.remove(group)
                        sender.sendMessage("删除群组 $group ,成功")
                    }
                }
            }
            else -> {
                sender.sendMessage("未知指令")
            }
        }
    }

    @SubCommand("moyu")
    suspend fun moyu(sender: CommandSender, action:String){
        when(action) {
            "time" -> {
                sender.sendMessage("现在摸鱼人日历发送时间为: ${Config.moyu_time}")
            }

            "api" -> {
                sender.sendMessage("现在摸鱼人日历api为: ${Config.moyu_api}")
            }

            else -> {
                sender.sendMessage("未知指令")
            }
        }
    }

    @SubCommand("moyu")
    suspend fun moyu(sender: CommandSender, action:String, value:String){
        when(action) {
            "time" -> {
                Config.moyu_time = value
                sender.sendMessage("摸鱼人日历发送时间已设置为: $value")
            }

            "api" -> {
                Config.moyu_api = value
                sender.sendMessage("摸鱼人日历api已设置为: $value")
            }

            else -> {
                sender.sendMessage("未知指令")
            }
        }
    }

    @SubCommand("news")
    suspend fun news(sender: CommandSender, action: String){
        when(action){
            "time" -> {
                sender.sendMessage("现在每日60s早报发送时间为: ${Config.news_time}")
            }
            "api" -> {
                sender.sendMessage("现在每日60s早报api为: ${Config.news_api}")
            }
            "token" -> {
                sender.sendMessage("现在每日60s早报api token为: ${Config.news_api_token}")
            }
            else -> {
                sender.sendMessage("未知指令")
            }
        }
    }

    @SubCommand("news")
    suspend fun news(sender: CommandSender, action: String, value: String){
        when(action){
            "time" -> {
                Config.news_time = value
                sender.sendMessage("每日60s早报发送时间已设置为: $value")
            }
            "api" -> {
                Config.news_api = value
                sender.sendMessage("每日60s早报api已设置为: $value")
            }
            "token" -> {
                Config.news_api_token = value
                sender.sendMessage("每日60s早报api token已设置为: $value")
            }
            else -> {
                sender.sendMessage("未知指令")
            }
        }
    }

    @SubCommand("reload")
    suspend fun reload(sender: CommandSender){
        // TODO: 重载插件
        sender.sendMessage("MorningPost 重载成功")
    }

}