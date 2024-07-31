package io.github.username.project.cache

import io.github.username.project.datatable.MultiBlockCraftListing
import io.github.username.project.datatable.MultiBlockCraftTable
import taboolib.common.platform.function.info
import java.util.concurrent.ConcurrentHashMap


class MultiBlockCraftCache {

    var nestedMap: ConcurrentHashMap<String, ConcurrentHashMap<String, MultiBlockCraftListing>> = ConcurrentHashMap()


    //插入列表类型数据的方法
    fun addItemsFromList(data:MutableList<MultiBlockCraftListing>){
        data.forEach {
            addItemsFrom(it)
        }
    }

    //插入数据的方法
    fun addItemsFrom(data: MultiBlockCraftListing){
        val innerMap = nestedMap.computeIfAbsent(data.uuid){
            ConcurrentHashMap()
        }
        innerMap[data.path] = data
    }

    //获取数据的方法
    fun getMBCL(uuid: String,path: String):MultiBlockCraftListing?{
        var data = nestedMap[uuid]?.get(path)
        if (data == null){
            //如果缓存中没有则从数据库中获取
            val a = MultiBlockCraftTable().getDataByUidPath(uuid,path)
            if (a==null){
                return null
            }else{
                //并添加到缓存中
                addItemsFrom(a)
                return a
            }
        }
        return data
    }
    fun getByUidall(uuid:String){
        nestedMap[uuid]?.forEach {
            if(it.key != "ProgressTree"){
                nestedMap[uuid]?.get(it.key)?.progress  = "false"
            }
        }
    }
    //删除数据的方法
    fun removes(data:MultiBlockCraftListing){
        nestedMap[data.uuid]?.remove(data.path,data)
    }

    //更新数据的方法
    fun updateData(data: MultiBlockCraftListing){
        nestedMap[data.uuid]?.set(data.path,data)
    }
}
