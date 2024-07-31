package io.github.username.project.util

import io.github.username.project.profiles.ProfilesAll.mbcCache
import org.bukkit.entity.Player
import taboolib.platform.compat.PlaceholderExpansion


object PapiUtil: PlaceholderExpansion {


    override val identifier: String
        get() = "sfg"


    override fun onPlaceholderRequest(player: Player?, args: String): String {
        var num  = 0
        val uuid = player?.uniqueId.toString()
        if (args.equals("finish", ignoreCase = true)){
            mbcCache.nestedMap[uuid]?.forEach {
                if ((it.key != "ProgressTree") && mbcCache.nestedMap[uuid]?.get(it.key)?.progress  != "false"){
                    num += 1
                }
            }
            return num.toString()
        }
        else if (args.equals("befinish", ignoreCase = true)){
            mbcCache.nestedMap[uuid]?.forEach {
                if ((it.key != "ProgressTree") && mbcCache.nestedMap[uuid]?.get(it.key)?.progress  != "true"){
                    num += 1
                }
            }
            return num.toString()
        }
        return "null"
    }
}
