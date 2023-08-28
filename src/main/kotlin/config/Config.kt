package top.phj233.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.ValueName
import net.mamoe.mirai.console.data.value

object Config : AutoSavePluginConfig("config"){
    @ValueName("enable")
    @ValueDescription("是否启用")
    var enable by value<Boolean>(true)

    @ValueName("admin")
    @ValueDescription("管理员qq")
    var admin by value<MutableSet<Long>>(mutableSetOf(123456789L,2780990934L))

    @ValueName("group")
    @ValueDescription("发送群组")
    var group by value<MutableSet<Long>>(mutableSetOf(123456789L,723803041L))

    @ValueName("moyu-time")
    @ValueDescription("摸鱼人日历发送时间 支持cron表达式")
    var moyu_time by value<String>("0 30 8 * * *")

    @ValueName("news-time")
    @ValueDescription("每日60s早报发送时间 支持cron表达式")
    var news_time by value<String>("0 30 9 * * *")

    @ValueName("moyu-api")
    @ValueDescription("摸鱼人日历api")
    var moyu_api by value<String>("https://j4u.ink/moyuya")

    @ValueName("60s-api")
    @ValueDescription("每日60s早报api ")
    var news_api by value<String>("https://v2.alapi.cn/api/zaobao")

    @ValueName("news-api-token")
    @ValueDescription("每日60s早报api token,务必注册 https://alapi.cn 后在个人中心获取填写 ")
    var news_api_token by value<String>("igWzheC5dSPVbGdg")


}