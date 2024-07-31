package io.github.username.project.event

import io.github.username.project.datatable.MultiBlockCraftTable.Companion.door
import io.github.username.project.profiles.ProfilesAll.menucache
import io.github.username.project.profiles.ProfilesAll.progressNodes
import io.github.username.project.util.PlayerDataUtil.initializeData
import io.github.username.project.util.PlayerDataUtil.removeQUitData
import io.github.username.project.util.PlayerDataUtil.resetData
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent

object PlayerEvent {

    //玩家加入时的事件监听
    @SubscribeEvent
    fun playerJoin(event: PlayerJoinEvent){
        val uuid = event.player.uniqueId.toString()
        val dataList = door.getDatasByUuid(uuid)
        if (dataList.isEmpty()){
            //初始化新玩家数据
            initializeData(event.player)
        }else{

            //非新玩家重载数据
            resetData(dataList,event.player)
        }
    }

    //玩家退出时的事件监听
    @SubscribeEvent
    fun playerQuit(event: PlayerQuitEvent){
        removeQUitData(event.player)
    }

}