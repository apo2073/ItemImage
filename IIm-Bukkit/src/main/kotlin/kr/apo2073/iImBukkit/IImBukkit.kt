package kr.apo2073.iImBukkit

import kr.apo2073.iimAPI.utils.Iim
import kr.apo2073.lib.Items.asItemBuilder
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class IImBukkit : JavaPlugin() {
    override fun onEnable() {
        getCommand("apply")?.setExecutor { sender, _, _, args ->
            if (sender !is Player) false
            val item=(sender as Player).inventory.itemInMainHand
            if (!item.type.isAir) true
            try {
                sender.inventory.setItemInMainHand(
                    item.asItemBuilder()
                        .setDescription(Iim(item).image).build()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            true
        }
    }

    override fun onDisable() {

    }
}
