package kr.apo2073.itemImage;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.UUID;

public final class ItemImage extends JavaPlugin {
    @Override
    public void onEnable() {
        new onEvents(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public List<Component> getPlayerImageLore(UUID uuid) {
        List<Component> lore = new ArrayList<>();
        try {
            URL url = new URL("https://api.mineatar.io/face/" + uuid + "?scale=1&format=png");
            BufferedImage image = ImageIO.read(url);

            for (int y = 0; y < image.getHeight(); y++) {
                Component rowLore = Component.text("");

                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    String hex = String.format("#%06X", 0xFFFFFF & rgb);

                    rowLore = rowLore.append(Component.text("■")
                            .color(TextColor.fromHexString(hex))
                            .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                    );
                }

                lore.add(rowLore);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lore;
    }

    public List<Component> getItemImage(ItemStack item) {
        List<Component> lore = new ArrayList<>();
        BufferedImage image;

        if (item.getType().isAir()) return lore;

        try { //https://assets.mcasset.cloud/
            String baseUrl = "https://assets.mcasset.cloud/1.20.1/assets/minecraft/textures/";
            String path = item.getType().isBlock() ? "block/" : "item/";
            String translationKey = item.getType().getItemTranslationKey()
                    .replace("item.minecraft.", "")
                    .replace("block.minecraft.", "");
            URL url = new URL(baseUrl + path + translationKey + ".png");
            System.out.println(url);

            image = ImageIO.read(url);

            for (int y = 0; y < image.getHeight(); y++) {
                Component rowLore = Component.text("");

                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    String hex = String.format("#%06X", 0xFFFFFF & rgb);

                    rowLore = rowLore.append(Component.text("■")
                            .color(TextColor.fromHexString(hex))
                            .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
                }

                lore.add(rowLore);
            }
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading image: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lore;
    }

}

