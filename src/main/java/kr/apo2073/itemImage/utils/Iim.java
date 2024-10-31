package kr.apo2073.itemImage.utils;

import kr.apo2073.itemImage.ex.NoItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.eclipse.sisu.Description;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Iim {
    private static final int MAX_PIXEL_SIZE = 32;
    private ItemStack item;
    private Player player;
    private String url;

    public Iim(ItemStack item) {
        this.item = item;
    }

    public Iim(Player player) {
        this.player = player;
    }

    public Iim(String url) {
        this.url = url;
    }

    public ItemStack getItem() {
        if (item == null) throw new NoItem();
        ItemMeta meta = item.getItemMeta();
        meta.lore(getItemImage(item));
        item.setItemMeta(meta);
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Component> getImage() {
        if (this.player != null) {
            return getPlayerImageLore(this.player.getUniqueId());
        } else if (this.item != null) {
            return getItemImage(this.item);
        } else if (this.url != null) {
            return getImageFromURL(this.url);
        }
        return null;
    }

    private List<Component> imageGenerator(BufferedImage image) {
        List<Component> lore = new ArrayList<>();

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
        return lore;
    }

    private List<Component> getPlayerImageLore(UUID uuid) {
        try {
            URL url = new URL("https://api.mineatar.io/face/" + uuid + "?scale=1&format=png");
            BufferedImage image = ImageIO.read(url);
            return imageGenerator(image);
        } catch (Exception e) {
            System.err.println("Failed to load player image: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<Component> getItemImage(ItemStack item) {
        if (item.getType().isAir()) return new ArrayList<>();

        if (item.getItemMeta() instanceof SkullMeta) {
            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
            if (skullMeta.getOwningPlayer() != null) {
                UUID uuid = skullMeta.getOwningPlayer().getUniqueId();
                return getPlayerImageLore(uuid);
            }
        }

        try {
            String baseUrl = "https://assets.mcasset.cloud/1.20.1/assets/minecraft/textures/";
            String path = item.getType().isBlock() ? "block/" : "item/";
            String translationKey = item.getType().getItemTranslationKey()
                    .replace("item.minecraft.", "")
                    .replace("block.minecraft.", "");
            URL url = new URL(baseUrl + path + translationKey + ".png");
            BufferedImage image = ImageIO.read(url);

            return imageGenerator(image);
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error loading item image: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    private List<Component> getImageFromURL(String url) {
        try {
            URL urls = new URL(url);
            BufferedImage image = ImageIO.read(urls);

            if (image.getWidth() > MAX_PIXEL_SIZE || image.getHeight() > MAX_PIXEL_SIZE) {
                BufferedImage resized = new BufferedImage(MAX_PIXEL_SIZE, MAX_PIXEL_SIZE, image.getType());
                Graphics2D grph = resized.createGraphics();

                grph.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                grph.drawImage(image, 0, 0, MAX_PIXEL_SIZE, MAX_PIXEL_SIZE, null);
                grph.dispose();

                image = resized;
            }

            return imageGenerator(image);
        } catch (Exception e) {
            System.err.println("Failed to load or resize image: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
