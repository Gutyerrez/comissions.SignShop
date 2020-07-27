package io.github.gutyerrez.signshop.listener;

import io.github.gutyerrez.core.spigot.misc.utils.InventoryUtils;
import io.github.gutyerrez.core.spigot.misc.utils.ItemBuilder;
import io.github.gutyerrez.signshop.SignShopProvider;
import io.github.gutyerrez.signshop.api.SignShop;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Set;

/**
 * @author SrGutyerrez
 */
public class PlayerInteractListener implements Listener {

    @EventHandler
    public void on(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        switch (action) {
            case RIGHT_CLICK_BLOCK: {
                SignShop signShop = this.getSignShop(event);

                if (signShop != null) {
                    Set<String> tryBuy = signShop.tryBuy(player);

                    if (!tryBuy.isEmpty()) {
                        tryBuy.forEach(player::sendMessage);
                        return;
                    }

                    SignShopProvider.Hooks.ECONOMY.get().withdrawPlayer(player, signShop.getPrice());

                    InventoryUtils.give(
                            player,
                            new ItemBuilder(signShop.getItem(), false)
                                    .amount(signShop.getQuantity())
                                    .make()
                    );
                    return;
                }
                break;
            }
            case LEFT_CLICK_BLOCK: {

                break;
            }
        }
    }

    protected SignShop getSignShop(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        if (ArrayUtils.contains(new Material[]{
                Material.SIGN,
                Material.SIGN_POST,
                Material.WALL_SIGN
        }, block.getType())) {
            return SignShopProvider.Cache.Local.SIGN_SHOP.provide().get(block.getLocation());
        }

        return null;
    }

}
