package kr.apo2073.itemImage;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import kr.apo2073.itemImage.skript.SkriptGetIim;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemImage extends JavaPlugin {
    public static ItemImage plugin;
    private SkriptAddon addon;
    @Override
    public void onEnable() {
        plugin=this;
        if (Bukkit.getPluginManager().getPlugin("Skript")!=null) {
            addon= Skript.registerAddon(this);
            new SkriptGetIim();
        }
    }
}

