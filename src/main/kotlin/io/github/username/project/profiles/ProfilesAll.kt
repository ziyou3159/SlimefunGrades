package io.github.username.project.profiles

import io.github.username.project.ProgressNodes
import io.github.username.project.cache.MultiBlockCraftCache
import io.github.username.project.cache.MultiBlockCraftMenuCache
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile


object ProfilesAll {

    //多方块结构合成物品事件的配置文件!
    @Config("MultiBlockCraftEvent.yml", autoReload = true)
    lateinit var mbce: ConfigFile

    //插件配置文件
    @Config("config.yml", autoReload = true)
    lateinit var config: ConfigFile

    @Config("lang.yml", autoReload = true)
    lateinit var langs: ConfigFile

    //任务节点总类树
    lateinit var progressNodes: ProgressNodes

    //缓存的数据库类
    lateinit var mbcCache: MultiBlockCraftCache

    //缓存的菜单数据类
    lateinit var menucache : MultiBlockCraftMenuCache


}