package kr.apo2073.iimAPI

import org.bukkit.plugin.java.JavaPlugin

class IimAPI : JavaPlugin() {
    override fun onEnable() {
        logger.info("Iim By.아포칼립스")
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
