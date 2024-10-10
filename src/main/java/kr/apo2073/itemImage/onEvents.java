package kr.apo2073.itemImage;

import io.papermc.paper.event.player.PlayerPickItemEvent;
import kr.apo2073.lib.Items.IsTYPEKt;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class onEvents implements Listener {
    public onEvents(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    ItemImage image=new ItemImage().plugin;
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(InventoryClickEvent e) {
        Player player=e.getWhoClicked().getKiller();
        assert player != null;
        if (e.getCurrentItem()==null) return;
        ItemStack item=e.getCurrentItem();
        ItemStack cursor=e.getCursor();
        if (item!=null) {
            ItemMeta meta=item.getItemMeta();
            if (IsTYPEKt.isSkull(item))
                meta.lore(image.getPlayerImageLore(player.getUniqueId()));
            else meta.lore(image.getItemImage(item));
            item.setItemMeta(meta);
        }
        if (cursor!=null) {
            ItemMeta meta=cursor.getItemMeta();
            if (IsTYPEKt.isSkull(item))
                meta.lore(image.getPlayerImageLore(player.getUniqueId()));
            else meta.lore(image.getItemImage(item));
            cursor.setItemMeta(meta);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEat(PlayerPickItemEvent e) {
        Player player=e.getPlayer();
        ItemStack item=e.getPlayer().getActiveItem();
        ItemMeta meta= item.getItemMeta();
        if (IsTYPEKt.isSkull(item))
            meta.lore(image.getPlayerImageLore(player.getUniqueId()));
        else meta.lore(image.getItemImage(item));
        item.setItemMeta(meta);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onHeld(PlayerItemHeldEvent e) {
        Player player=e.getPlayer();
        ItemStack item=e.getPlayer().getActiveItem();
        ItemMeta meta= item.getItemMeta();
        if (IsTYPEKt.isSkull(item))
            meta.lore(image.getPlayerImageLore(player.getUniqueId()));
        else meta.lore(image.getItemImage(item));
        item.setItemMeta(meta);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDrop(PlayerDropItemEvent e) {
        Player player=e.getPlayer();
        ItemStack item=e.getItemDrop().getItemStack();
        ItemMeta meta= item.getItemMeta();
        if (IsTYPEKt.isSkull(item))
            meta.lore(image.getPlayerImageLore(player.getUniqueId()));
        else meta.lore(image.getItemImage(item));
        item.setItemMeta(meta);
    }
}
