package io.github.gutyerrez.signshop.api;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import io.github.gutyerrez.core.shared.misc.utils.NumberUtils;
import io.github.gutyerrez.core.shared.world.location.SerializedLocation;
import io.github.gutyerrez.core.spigot.CoreSpigotConstants;
import io.github.gutyerrez.core.spigot.misc.utils.InventoryUtils;
import io.github.gutyerrez.signshop.SignShopConstants;
import io.github.gutyerrez.signshop.SignShopProvider;
import lombok.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author SrGutyerrez
 */
@Getter
@AllArgsConstructor
public class SignShop {

    @NonNull
    private final Integer id;

    @Setter
    private String name;

    @NonNull
    private Type type;

    @Setter
    private ItemStack item;

    @Setter
    @NonNull
    private Integer quantity;

    @Setter
    @NonNull
    private Double price;

    private final SerializedLocation serializedLocation;

    public void updateSign() {
        Sign sign = this.getSign();

        sign.setLine(0, SignShopConstants.SHOP_NAME);
        sign.setLine(1, String.format("%s %.2f", this.type.getPrefix(), this.price));
        sign.setLine(2, String.valueOf(this.quantity));
        sign.setLine(3, this.name == null ? "???" : this.name);

        sign.update();
    }

    public String getFancyName() {
        return this.hasValidItem() ? String.format("§f%s", CoreSpigotConstants.TRANSLATE_ITEM.get(
                this.getItem()
        )) : "§cNenhum item definido.";
    }

    public Sign getSign() {
        return (Sign) this.getBlock().getState();
    }

    public Block getBlock() {
        return this.getLocation().getBlock();
    }

    protected Location getLocation() {
        return this.serializedLocation.parser(CoreSpigotConstants.LOCATION_PARSER);
    }

    public ItemStack getPreviewItemStack() {
        return this.item == null ? new ItemStack(Material.BARRIER) : this.item;
    }

    public ImmutableSet<String> tryBuy(Player player) {
        if (this.item == null) {
            return ImmutableSet.of(
                    "§cEsta loja ainda não está funcionando."
            );
        }

        if (this.type == Type.SELL) {
            return ImmutableSet.of(
                    "§cEsta loja apenas compra."
            );
        }

        if (SignShopProvider.Hooks.ECONOMY.get().getBalance(player) < this.price) {
            return ImmutableSet.of(
                    String.format(
                            "§cVocê precisa de %s coins para comprar nesta loja.",
                            NumberUtils.format(this.price)
                    )
            );
        }

        if (!InventoryUtils.fits(player.getInventory(), this.item)) {
            return ImmutableSet.of(
                    "§cVocê não possui espaço suficiente em seu inventário para comprar este item."
            );
        }

        return ImmutableSet.of();
    }

    public ImmutableSet<String> trySell(Player player) {
        if (this.item == null) {
            return ImmutableSet.of(
                    "§cEsta loja ainda não está funcionando."
            );
        }

        if (this.type == Type.BUY) {
            return ImmutableSet.of(
                    "§cEsta loja apenas vende."
            );
        }

        if (InventoryUtils.countItems(player.getInventory(), this.item) < this.quantity) {
            return ImmutableSet.of(
                    "§cVocê não possui itens suficientes para vender."
            );
        }

        return ImmutableSet.of();
    }

    public Boolean hasValidItem() {
        return this.item != null;
    }

    @RequiredArgsConstructor
    public static enum Type {

        BUY('C', "§aC §0", Pattern.compile("C .*")),
        SELL('V', "§cV §0", Pattern.compile("V .*"));

        @Getter
        private final Character character;

        @Getter
        private final String prefix;

        protected final Pattern pattern;

        public static Type tryParse(String sequence) {
            for (Type type : Type.values()) {
                if (type.pattern.matcher(sequence).matches()) {
                    return type;
                }
            }

            return null;
        }

    }

}
