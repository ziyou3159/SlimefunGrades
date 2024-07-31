package io.github.username.project.util

import city.norain.slimefun4.utils.InventoryUtil.closeInventory
import io.github.username.project.ProgressNodes
import io.github.username.project.cache.MultiBlockCraftMenuCache
import io.github.username.project.profiles.ProfilesAll.langs
import io.github.username.project.profiles.ProfilesAll.mbcCache
import io.github.username.project.profiles.ProfilesAll.mbce
import io.github.username.project.profiles.ProfilesAll.menucache
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.function.info
import taboolib.library.xseries.getItemStack
import taboolib.module.chat.colored
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.PageableChest
import taboolib.platform.compat.replacePlaceholder
import taboolib.platform.util.buildItem

object MenuUtil {
    fun openMenu(sender: Player,prog: ProgressNodes,num:Int){
        sender.openMenu<PageableChest<MultiBlockCraftMenuCache>>(mbce.getString("title")?.colored()?.replacePlaceholder(sender)?:"粘液科技进度树"){
            map(*mbce.getStringList("layout").toTypedArray())
            onClick { event, _ ->
                event.isCancelled = true
            }
            val uuid = sender.uniqueId.toString()
            var nums = num
            val itemlist:MutableList<List<ItemStack?>>
            val stacklist = mutableListOf<ItemStack?>()
            val soltlist = mbce.getIntegerList("nex_title")
            prog.childnodes.forEachIndexed { _: Int, it: ProgressNodes ->
                val itemStack = it.itemstack?.clone()
                val itemMeta  = itemStack?.itemMeta?.clone()
                val lore = itemMeta?.lore ?: mutableListOf<String>()
                if (mbcCache.getMBCL(uuid, it.path)?.progress == "true") {
                    langs.getStringList("finish").colored().forEach {
                        lore.add(it)
                    }
                } else {
                    langs.getStringList("no-finish").colored().forEach {
                        lore.add(it)
                    }
                }
                itemMeta?.lore = lore.replacePlaceholder(sender)
                itemStack?.itemMeta = itemMeta
                stacklist += itemStack
            }
            itemlist = stacklist.chunked(mbce.getInt("num_title",4),).toMutableList()
            if (nums < itemlist.size){
                itemlist[nums].forEachIndexed { index: Int, value: ItemStack? ->
                    if ( value != null){
                        set(soltlist[index],value){
                            if (clickEvent().isLeftClick){
                                menucache.addMenuData(sender.uniqueId.toString(),prog.childnodes[nums*mbce.getInt("num_title", 4)+index])
                                openMenu(sender,prog.childnodes[nums*mbce.getInt("num_title", 4)+index],0)

                            }
                            isCancelled = true
                        }
                    }
                }
            }
            prog.itemstack?.let {
                val itemStack = it.clone()
                val itemMeta  = itemStack.itemMeta?.clone()
                val lore = itemMeta?.lore ?: mutableListOf<String>()
                if (mbcCache.getMBCL(uuid, prog.path)?.progress == "true"){
                    langs.getStringList("mastermind-finish").colored().forEach {
                        lore.add(it)
                    }
                }else{
                    langs.getStringList("mastermind-no-finish").colored().forEach {
                        lore.add(it)
                    }
                }
                itemMeta?.lore = lore.replacePlaceholder(sender)
                itemStack.itemMeta = itemMeta
                set("@".toCharArray()[0], itemStack){
                    if (clickEvent().isLeftClick){
                        prog.previous?.let { it1 ->
                            menucache.addMenuData(sender.uniqueId.toString(),it1)
                            openMenu(sender, it1,0)
                        }
                    }
                    isCancelled = true
                }
            }
            if (0 <= nums && nums < itemlist.size-1){
                mbce.getItemStack( "Gui-title.+.display")?.let {
                    buildItem(it){
                        customModelData = mbce.getInt("Gui-title.+.customModelData")
                    }
                }?.let {
                    val itm = it.clone()
                    val ma = itm.itemMeta?.clone()
                    val lore = ma?.lore?.replacePlaceholder(sender)
                    ma?.lore = lore
                    itm.itemMeta = ma
                    set("+".toCharArray()[0], itm){
                        isCancelled = true
                        if (clickEvent().isLeftClick){
                            if (nums+1 < itemlist.size){
                                nums += 1
                                openMenu(sender,prog,nums)
                            }
                        }
                    }
                }
            }else{
                mbce.getItemStack( "Gui-title.#.display")?.let {
                    buildItem(it){
                        customModelData = mbce.getInt("Gui-title.#.customModelData")
                    }
                }?.let {
                    val itm = it.clone()
                    val ma = itm.itemMeta?.clone()
                    val lore = ma?.lore?.replacePlaceholder(sender)
                    ma?.lore = lore
                    itm.itemMeta = ma
                    set("+".toCharArray()[0], itm){

                    }
                }
            }
            if (nums > 0 && nums <= itemlist.size-1){
                mbce.getItemStack( "Gui-title.-.display")?.let {
                    buildItem(it){
                        customModelData = mbce.getInt("Gui-title.-.customModelData")
                    }
                }?.let {
                    val itm = it.clone()
                    val ma = itm.itemMeta?.clone()
                    val lore = ma?.lore?.replacePlaceholder(sender)
                    ma?.lore = lore
                    itm.itemMeta = ma
                    set("-".toCharArray()[0], itm){
                        isCancelled = true
                        if (clickEvent().isLeftClick){
                            if (nums-1 >= 0){
                                nums -= 1
                                openMenu(sender,prog,nums)
                            }
                        }
                    }
                }
            }else{
                mbce.getItemStack( "Gui-title.#.display")?.let {
                    buildItem(it){
                        customModelData = mbce.getInt("Gui-title.#.customModelData")
                    }
                }?.let {
                    val itm = it.clone()
                    val ma = itm.itemMeta?.clone()
                    val lore = ma?.lore?.replacePlaceholder(sender)
                    ma?.lore = lore
                    itm.itemMeta = ma
                    set("-".toCharArray()[0], itm){
                    }
                }
            }
            mbce.getItemStack( "Gui-title.#.display")?.let {
                buildItem(it){
                    customModelData = mbce.getInt("Gui-title.#.customModelData")
                }
            }?.let {
                val itm = it.clone()
                val ma = itm.itemMeta?.clone()
                val lore = ma?.lore?.replacePlaceholder(sender)
                ma?.lore = lore
                itm.itemMeta = ma
                set("#".toCharArray()[0], itm){
                }
            }
            mbce.getItemStack("Gui-title.r.display")?.let{
                buildItem(it){
                    customModelData = mbce.getInt("Gui-title.r.customModelData")
                }.let {
                    val itm = it.clone()
                    val ma = itm.itemMeta?.clone()
                    val lore = ma?.lore?.replacePlaceholder(sender)
                    ma?.lore = lore
                    itm.itemMeta = ma
                    set("r".toCharArray()[0],itm){
                        closeInventory(inventory)
                    }
                }
            }
        }
    }
}