package io.github.gutyerrez.signshop.listener;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import io.github.gutyerrez.core.shared.world.location.SerializedLocation;
import io.github.gutyerrez.core.spigot.CoreSpigotConstants;
import io.github.gutyerrez.signshop.SignShopConstants;
import io.github.gutyerrez.signshop.SignShopProvider;
import io.github.gutyerrez.signshop.api.SignShop;
import io.github.gutyerrez.signshop.misc.utils.SignShopUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * @author SrGutyerrez
 */
public class SignChangeListener implements Listener {

    @EventHandler
    public void onChange(SignChangeEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("signshop.shops.create")) {
            return;
        }

        Block block = event.getBlock();

        String shopNameLine = event.getLine(0);

        if (!shopNameLine.equals(SignShopConstants.SHOP_NAME)) {
            return;
        }

        String typeAndPriceLine = event.getLine(1);

        if (!SignShopUtils.isValid(typeAndPriceLine)) {
            block.breakNaturally();

            player.sendMessage("§cEsta loja não é válida!");
            return;
        }

        SignShop.Type type = SignShop.Type.tryParse(typeAndPriceLine);

        assert type != null;

        Double price = Doubles.tryParse(typeAndPriceLine.split(String.format(
                "%s ",
                type.getCharacter()
        ))[1]);

        if (price == null || price.isNaN() || price < 1) {
            block.breakNaturally();

            player.sendMessage("§cVocê inseriu um preço inválido!");
            return;
        }

        event.setLine(1, String.format("%s %.2f", type.getPrefix(), price));

        Integer quantity = Ints.tryParse(event.getLine(2));

        if (quantity == null || quantity < 1) {
            block.breakNaturally();

            player.sendMessage("§cVocê inseriu uma quantia inválida!");
            return;
        }

        event.setLine(3, "???");

        SerializedLocation serializedLocation = new SerializedLocation(
                block.getWorld().getName(),
                block.getX(),
                block.getY(),
                block.getZ()
        );

        SignShop signShop = SignShopProvider.Repositories.SIGN_SHOP.provide().insert(
                null,
                type,
                quantity,
                price,
                serializedLocation
        );

        SignShopProvider.Cache.Local.SIGN_SHOP.provide().add(
                serializedLocation.parser(CoreSpigotConstants.LOCATION_PARSER),
                signShop
        );
    }

}
