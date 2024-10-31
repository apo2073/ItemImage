package kr.apo2073.itemImage.skript;

import ch.njol.skript.Skript;
import kr.apo2073.itemImage.utils.Iim;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SkriptGetIim extends SimpleExpression<ItemStack> {
    private Expression<ItemStack> itemExpr;
    private Expression<Player> playerExpr;
    private Expression<ItemStack> itemstackExpr;

    static {
        Skript.registerExpression(
                SkriptGetIim.class,
                ItemStack.class,
                ExpressionType.SIMPLE,
                "[(the|a)] Iim (from|on) %itemstack/player/string% (to|on) %itemstack%"
        );
    } //String추가해야함

    @Override
    protected @Nullable ItemStack[] get(Event event) {
        ItemStack itemStack = itemstackExpr.getSingle(event);
        if (itemStack == null) return new ItemStack[0];

        Iim iim = createIimInstance(event);
        if (iim != null) {
            applyLore(itemStack, iim.getImage());
        }
        return new ItemStack[]{itemStack};
    }

    private @Nullable Iim createIimInstance(Event event) {
        ItemStack item = itemExpr != null ? itemExpr.getSingle(event) : null;
        Player player = playerExpr != null ? playerExpr.getSingle(event) : null;

        if (item != null) return new Iim(item);
        if (player != null) return new Iim(player);
        return null;
    }

    private void applyLore(ItemStack itemStack, List<Component> lore) {
        if (lore == null || lore.isEmpty()) return;
        ItemMeta meta = itemStack.getItemMeta();
        meta.lore(lore);
        itemStack.setItemMeta(meta);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "Iim from or on itemstack/player";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (expressions[0] instanceof Expression) {
            if (expressions[0].getReturnType().isAssignableFrom(ItemStack.class)) {
                itemExpr = (Expression<ItemStack>) expressions[0];
            } else if (expressions[0].getReturnType().isAssignableFrom(Player.class)) {
                playerExpr = (Expression<Player>) expressions[0];
            }
        }
        itemstackExpr = (Expression<ItemStack>) expressions[1];
        return itemExpr != null || playerExpr != null;
    }
}

