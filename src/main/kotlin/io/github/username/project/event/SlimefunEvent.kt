package io.github.username.project.event

import io.github.thebusybiscuit.slimefun4.api.events.MultiBlockCraftEvent
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem.getByItem
import io.github.username.project.datatable.MultiBlockCraftTable
import io.github.username.project.profiles.ProfilesAll.mbcCache
import io.github.username.project.profiles.ProfilesAll.mbce
import io.github.username.project.profiles.ProfilesAll.progressNodes
import io.github.username.project.util.ConfiguUtill.runKether
import taboolib.common.platform.event.SubscribeEvent
import java.time.LocalDateTime


object SlimefunEvent {

    //用于处理多方块结构的合成事件
    @SubscribeEvent
    fun slimefunitem(event: MultiBlockCraftEvent) {
        event.player
        val uuid = event.player.uniqueId.toString()
        val itemID = getByItem(event.getOutput())?.id
        if (itemID != null){
            val currentNodess = progressNodes.getLayoutSlimeFunId(itemID)
            if (currentNodess != null ){
                val currentpath = currentNodess.path
                val lastpath = currentNodess.previous?.path
                if (lastpath != null){
                    val currentmbcc = mbcCache.getMBCL(uuid,currentpath)
                    if (currentmbcc?.progress == "false" && mbcCache.getMBCL(uuid,lastpath)?.progress == "true"){
                        currentmbcc.progress = "true"
                        val data = LocalDateTime.now()
                        currentmbcc.date = "${data.year}-${data.monthValue}-${data.dayOfMonth}: ${data.hour}h${data.minute}d${data.second}s"
                        MultiBlockCraftTable().updateData(currentmbcc)
                        runKether(mbce.getStringList("Icons.${currentNodess.id}.actions"),event.player)
                    }
                }
            }
        }
    }
}
