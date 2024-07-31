package io.github.username.project


import io.github.username.project.cache.MultiBlockCraftCache
import io.github.username.project.cache.MultiBlockCraftMenuCache
import io.github.username.project.datatable.MultiBlockCraftTable.Companion.door
import io.github.username.project.profiles.ProfilesAll.mbcCache
import io.github.username.project.profiles.ProfilesAll.menucache
import io.github.username.project.profiles.ProfilesAll.progressNodes
import org.bukkit.Bukkit
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.common.platform.function.info
import taboolib.module.chat.colored
import java.util.*

object SlimefunGrades : Plugin() {

    override fun onEnable() {
        console().sendMessage("&7[&aSlimefunGrades&7] &3插件已成功启动!".colored())
        door.getDatasByUuid(UUID.randomUUID().toString())

    }
    override fun onActive(){
        console().sendMessage("&7[&aSlimefunGrades&7] &3现开始自动加载所有进度节点！".colored())
        progressNodes = ProgressNodes().createNodeClass(ProgressNodes())
        console().sendMessage("&7[&aSlimefunGrades&7] &a所有节点已全部加载！配置错误节点将不在gui中显示!".colored())
        mbcCache = MultiBlockCraftCache()
        menucache = MultiBlockCraftMenuCache()
    }

}