package io.github.gutyerrez.signshop.listener;

import io.github.gutyerrez.signshop.SignShopProvider;
import io.github.gutyerrez.signshop.api.SignShop;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @author SrGutyerrez
 */
public class BlockBreakListener implements Listener {

    @EventHandler
    public void on(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (ArrayUtils.contains(new Material[] {
                Material.SIGN,
                Material.SIGN_POST,
                Material.WALL_SIGN
        }, block.getType())) {
            return;
        }

        SignShop signShop = SignShopProvider.Cache.Local.SIGN_SHOP.provide().get(block);

        if (signShop != null && player.hasPermission("signshop.shops.delete")) {
            SignShopProvider.Cache.Local.SIGN_SHOP.provide().remove(block);
            SignShopProvider.Repositories.SIGN_SHOP.provide().delete(signShop.getId());
        } else if (!player.hasPermission("signshop.shops.delete")) {
            event.setCancelled(true);
        }
    }

}
