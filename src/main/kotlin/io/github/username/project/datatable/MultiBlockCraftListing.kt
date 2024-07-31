package io.github.username.project.datatable


import io.github.username.project.profiles.ProfilesAll.mbcCache
import io.github.username.project.util.CacheUtils
import taboolib.common.platform.function.info
import taboolib.expansion.Key
import taboolib.expansion.persistentContainer


data class MultiBlockCraftListing(
    @Key
    var uuid: String,
    var player_name: String,
    var progress: String,
    var path: String,
    var date: String
)
class MultiBlockCraftTable{

    private val tableName = "multiblockcrafttable"

    private val container by lazy {
        persistentContainer { new<MultiBlockCraftListing>(tableName) }
    }


    //通过uuid来查找数据
    fun getDatasByUuid(uuid: String): List<MultiBlockCraftListing>{
        val containerx = container[tableName]
        val data = mutableListOf<MultiBlockCraftListing>()
        CacheUtils.safe {
            data.addAll(
                containerx.get<MultiBlockCraftListing> {
                    "uuid" eq uuid
                }
            )
        }
        return data
    }
    //通过uuid与path准确查找
    fun getDataByUidPath(uuid:String,path: String):MultiBlockCraftListing?{
        val containerx = container[tableName]
        var data: MultiBlockCraftListing? = null
        CacheUtils.safe {
            data = containerx.getOne<MultiBlockCraftListing>{
                "UUID" eq uuid
                "path" eq path
            }
        }
        return data
    }

    //传入一个列表，将数据存入到数据库中
    fun appendListData(data: MutableList<MultiBlockCraftListing>){
        val containerx = container[tableName]
        CacheUtils.safe{
            containerx.insert(data)
        }
        //将数据存入到缓存当中
        mbcCache.addItemsFromList(data)
    }

    //传入一个数据，将数据加入到数据库中
    fun appendData(data: MultiBlockCraftListing){
        val containerx = container[tableName]
        CacheUtils.safe{
            containerx.insert(listOf(data))
        }

        //将数据存入到缓存当中
        mbcCache.addItemsFrom(data)
    }

    //传入要删除的数据
    fun removeExistingData(data: MultiBlockCraftListing){
        val containerx = container[tableName]
        val tablex = containerx.table
        val dataSource = containerx.dataSource
        CacheUtils.safe{
            tablex.delete(dataSource){
                where("uuid" eq data.uuid)
                where("path" eq data.path)
            }
        }

        //删除缓存中的数据
        mbcCache.removes(data)
    }
    //传入一个数据列表进行批量删除
    fun removeListData(data:MutableList<MultiBlockCraftListing>){
        val containerx = container[tableName]
        val tablex = containerx.table
        val dataSource = containerx.dataSource
        var arraypath: Array<Any> = arrayOf()
        val uuid = data[0].uuid
        data.forEach {
            arraypath += it.path
            mbcCache.removes(it)
        }
        tablex.delete(dataSource){
            where("uuid" eq uuid and ("path" inside arraypath))
        }


    }

    //传入一个列表，对该表数据进行更新
    fun updateListData(data: MutableList<MultiBlockCraftListing>){
        data.forEach {
            updateData(it)
        }
    }
    //传入一个uuid进行重置!
    fun updateName(name: String){
        val containerx = container[tableName]
        val tablex = containerx.table
        val dataSource = containerx.dataSource
        CacheUtils.safe{
            tablex.update(dataSource) {
                where("player_name" eq name and not("path" eq "ProgressTree"))
                set("progress", "false")
                set("date","")
            }
        }
    }
    //传入一个数据，对该表数据进行更新
    fun updateData(data: MultiBlockCraftListing){
        val containerx = container[tableName]
        val tablex = containerx.table
        val dataSource = containerx.dataSource
        CacheUtils.safe{
            tablex.update(dataSource) {
                where("uuid" eq data.uuid)
                where("path" eq data.path)
                set("progress", data.progress)
                set("date",data.date)
            }
        }
        //更新缓存中的数据
        mbcCache.updateData(data)
    }
    companion object {
        val door by lazy {
            MultiBlockCraftTable()
        }
    }

}