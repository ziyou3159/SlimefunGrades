package io.github.username.project.util

import io.github.username.project.ProgressNodes
import io.github.username.project.datatable.MultiBlockCraftListing
import io.github.username.project.datatable.MultiBlockCraftTable
import io.github.username.project.datatable.MultiBlockCraftTable.Companion.door
import io.github.username.project.profiles.ProfilesAll
import io.github.username.project.profiles.ProfilesAll.mbcCache
import io.github.username.project.profiles.ProfilesAll.mbce
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.function.info

object PlayerDataUtil {

    //玩家加入时执行 获取树的所有节点
    fun progressfor(nodes:String,list:MutableList<String>): MutableList<String> {
        var lists = list
        lists.add(nodes)
        mbce.getConfigurationSection(nodes)?.getKeys(false)?.forEach {
            progressfor("$nodes.$it",lists)
        }
        return lists
    }

    //新玩家数据的初始化
    fun initializeData(player: Player){
        val nodesList = progressfor("ProgressTree",mutableListOf<String>())
        val mbcls = mutableListOf<MultiBlockCraftListing>()
        nodesList.forEach {
            mbcls.add(MultiBlockCraftListing(
                uuid = player.player?.uniqueId.toString(),
                player_name= player.name,
                progress = if (it == "ProgressTree") "true" else "false",
                path = it,
                date= ""))
        }
        door.appendListData(mbcls)
    }
    //非新玩家数据的重置
    fun resetData(dataList:List<MultiBlockCraftListing>,player: Player){
        val pathlist = mutableListOf<String>()
        val nodeslist = progressfor("ProgressTree",mutableListOf<String>())
        val uuid = player.uniqueId.toString()
        dataList.forEach {
            if (mbcCache.nestedMap[uuid]?.get(it.path) == null){
                mbcCache.addItemsFrom(it)
            }
            pathlist.add(it.path)
        }
        val data = mutableListOf<MultiBlockCraftListing>()
        nodeslist.forEach {
            if (it !in pathlist){
                data.add(MultiBlockCraftListing(
                    uuid = uuid,
                    player_name= player.name,
                    progress = if (it == "ProgressTree") "true" else "false",
                    path = it,
                    date= ""
                ))
            }
        }
        if (data.size != 0){
            door.appendListData(data)
        }
        val removedata = mutableListOf<MultiBlockCraftListing>()
        dataList.forEach {
            if (it.path !in nodeslist){
                removedata.add(it)
            }
        }
        if (removedata.size != 0){
            door.removeListData(removedata)
        }

    }

    //玩家退出服务器时删除数据
    fun removeQUitData(player:Player){
        mbcCache.nestedMap.remove(player.uniqueId.toString())
    }

    //重载所有在线玩家的数据
    fun reloadAllData(){
        val onlinePlayers = Bukkit.getOnlinePlayers()
        onlinePlayers.forEach {
            val dataList = door.getDatasByUuid(it.uniqueId.toString())
            resetData(dataList, it)
        }
    }
}