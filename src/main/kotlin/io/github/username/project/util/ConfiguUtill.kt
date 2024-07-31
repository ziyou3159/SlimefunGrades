package io.github.username.project.util

import io.github.username.project.ProgressNodes
import io.github.username.project.cache.MultiBlockCraftMenuCache
import io.github.username.project.profiles.ProfilesAll
import io.github.username.project.profiles.ProfilesAll.menucache
import io.github.username.project.profiles.ProfilesAll.progressNodes
import io.github.username.project.util.PlayerDataUtil.reloadAllData
import org.bukkit.entity.Player
import taboolib.common.platform.function.*
import taboolib.module.chat.colored
import taboolib.module.configuration.Configuration
import taboolib.module.kether.KetherShell
import taboolib.module.kether.ScriptOptions
import java.io.File

object ConfiguUtill {

    //自定义重载传入文件类,文件名
    private fun releaseResource(arousal: Configuration,string: String){
        if (!File(getDataFolder(), "$string.yml").exists()){
            releaseResourceFile("$string.yml")
        }
        arousal.reload()
        console().sendMessage("&7[&aSlimefunGrades&7] &3$string.yml &3文件已重新加载~".colored())
    }

    //自定义重载函数
    fun reload(){
        releaseResource(ProfilesAll.mbce,"MultiBlockCraftEvent")
        releaseResource(ProfilesAll.config,"config")
        releaseResource(ProfilesAll.langs,"lang")
        progressNodes = ProgressNodes().createNodeClass(ProgressNodes())
        menucache = MultiBlockCraftMenuCache()
        console().sendMessage("&7[&aSlimefunGrades&7] &6所有节点已全部加载！".colored())
        reloadAllData()
    }

    fun runKether(script: List<String>, player: Player) {
        KetherShell.eval(
            script, options = ScriptOptions(
                sender = adaptCommandSender(player)
            )
        )
    }
}