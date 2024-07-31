package io.github.username.project

import io.github.username.project.profiles.ProfilesAll.mbce
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.function.console
import taboolib.common.platform.function.info
import taboolib.library.xseries.getItemStack
import taboolib.module.chat.colored
import taboolib.platform.util.buildItem


class ProgressNodes {

    var id: String = "ProgressTree"
    var slime_fun_id:String? = null
    var path: String = "ProgressTree"
    var previous: ProgressNodes? = null
    var childnodes: List<ProgressNodes> = listOf()
    var itemstack: ItemStack? = null

    //一层层获取任务节点
    fun createNodeClass(nodes: ProgressNodes):ProgressNodes {
        //获取到当前节点的子节点
        val sectionkey = mbce.getConfigurationSection(nodes.path)?.getKeys(false)
        val nodeList = mutableListOf<ProgressNodes>()
        //递归子节点
        sectionkey?.forEach { key ->
            val layoutkey = mbce.getString("Icons.$key.slime_fun_id")
            val childNode = ProgressNodes()
            val itemstack = mbce.getItemStack("Icons.$key.display")?.let {
                buildItem(it){
                    customModelData = mbce.getInt("Icons.$key.customModelData")
                }
            }
            if (itemstack != null && layoutkey != null){
                console().sendMessage("&7[&aSlimefunGrades&7] &e进度节点 &6${key} &e已成功加载!".colored())
            }else{
                console().sendMessage("&7[&aSlimefunGrades&7] &e进度节点 &6${key} &c物品配置未正确!".colored())
            }
            childNode.setNodeAb(key,"${nodes.path}.$key",
                listOf(),nodes,layoutkey,itemstack
            )
            createNodeClass(childNode)
            nodeList.add(childNode)
        }
        nodes.childnodes = nodeList
        return nodes
    }



    fun setNodeAb(
        ID: String,
        Path: String,
        ChildNodes: List<ProgressNodes>,
        Previous:ProgressNodes,
        Slime_Fun_Id:String?,
        item: ItemStack?)
    {
        this.id = ID;
        this.slime_fun_id = Slime_Fun_Id
        this.path = Path;
        this.childnodes = ChildNodes;
        this.previous = Previous;
        this.itemstack = item
    }


    //根据id去查找
    fun getLayoutSlimeFunId(slime_fun_id: String): ProgressNodes? {
        if (this.slime_fun_id == slime_fun_id){
            return this
        }
        for (chid in childnodes){
            val result = chid.getLayoutSlimeFunId(slime_fun_id)
            if (result != null){
                return result
            }
        }
        return null
    }

}
