package io.github.username.project.commands


import io.github.username.project.ProgressNodes
import io.github.username.project.datatable.MultiBlockCraftTable
import io.github.username.project.datatable.MultiBlockCraftTable.Companion.door
import io.github.username.project.profiles.ProfilesAll
import io.github.username.project.profiles.ProfilesAll.langs
import io.github.username.project.profiles.ProfilesAll.mbcCache
import io.github.username.project.profiles.ProfilesAll.menucache
import io.github.username.project.profiles.ProfilesAll.progressNodes
import io.github.username.project.util.ConfiguUtill
import io.github.username.project.util.MenuUtil.openMenu
import io.github.username.project.util.PlayerDataUtil.resetData
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.subCommand
import taboolib.module.chat.colored
import java.util.concurrent.ConcurrentHashMap


@CommandHeader(name = "SlimefunGrades", aliases = ["sfg"], permission = "SlimefunGrades.use")
object Commands {
    @CommandBody(permission = "SlimefunGrades.reload")
    val reload = subCommand {
        execute<CommandSender>{ sender, _, _ ->
            ConfiguUtill.reload()
            sender.sendMessage(langs.getString("reload")?.colored())
        }
    }
    @CommandBody(permission = "SlimefunGrades.reset")
    val reset = subCommand {
        dynamic(optional = true, comment = "name") {
            val playername = mutableListOf<String>()
            val players = Bukkit.getOnlinePlayers()
            val maplist:ConcurrentHashMap<String,Player> = ConcurrentHashMap()
            suggestion<CommandSender> { _, _ ->
                players.forEachIndexed { _, player ->
                    maplist[player.name] = player
                    playername.add(player.name)
                }
                playername
            }
            execute<CommandSender>{ sender, context, _ ->
                door.updateName(context["name"])
                mbcCache.getByUidall(maplist[context["name"]]?.uniqueId.toString())
                sender.sendMessage(langs.getString("reset")?.replace("{0}", context["name"])?.colored())
            }
        }
    }
    @CommandBody(permission = "SlimefunGrades.open")
    val open = subCommand {
        dynamic(optional = true, comment = "menu") {
            suggestion<CommandSender> { _, _ ->
                listOf("tree")
            }
            execute<Player>{ sender, context, _ ->
                if (context["menu"] == "tree"){
                    val uuid = sender.uniqueId.toString()
                    val prog = menucache.getMenuData(uuid)
                    prog?.let {
                        openMenu(sender, it,0)
                    }
                }
            }
        }
    }
}