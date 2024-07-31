package io.github.username.project.cache

import io.github.username.project.ProgressNodes
import io.github.username.project.profiles.ProfilesAll.progressNodes
import java.util.concurrent.ConcurrentHashMap

class MultiBlockCraftMenuCache {

    private val nestedMap: ConcurrentHashMap<String, ProgressNodes> = ConcurrentHashMap()

    fun getMenuData(uuid: String): ProgressNodes? {
        val data = nestedMap[uuid]
        if (data != null){
            return data
        }else{
            addMenuData(uuid,progressNodes)
            return progressNodes
        }
    }

    fun addMenuData(uuid:String,prog: ProgressNodes){
        nestedMap[uuid] = prog
    }

}