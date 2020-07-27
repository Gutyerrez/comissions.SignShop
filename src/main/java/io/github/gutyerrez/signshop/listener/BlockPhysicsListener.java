package io.github.gutyerrez.signshop.listener;

import io.github.gutyerrez.signshop.SignShopProvider;
import io.github.gutyerrez.signshop.api.SignShop;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * @author SrGutyerrez
 */
public class BlockPhysicsListener implements Listener {

    @EventHandler
    public void on(BlockPhysicsEvent event) {
        Block block = event.getBlock();

        SignShop signShop = SignShopProvider.Cache.Local.SIGN_SHOP.provide().get(block.getLocation());

        if (signShop != null) {
            SignShopProvider.Cache.Local.SIGN_SHOP.provide().remove(block.getLocation());
            SignShopProvider.Repositories.SIGN_SHOP.provide().delete(signShop.getId());
        }
    }

}
