package io.github.gutyerrez.signshop.listener;

import com.google.common.collect.ImmutableSet;
import io.github.gutyerrez.core.shared.misc.utils.ChatColor;
import io.github.gutyerrez.core.shared.misc.utils.NumberUtils;
import io.github.gutyerrez.core.spigot.misc.utils.InventoryUtils;
import io.github.gutyerrez.core.spigot.misc.utils.ItemBuilder;
import io.github.gutyerrez.signshop.SignShopProvider;
import io.github.gutyerrez.signshop.api.SignShop;
import io.github.gutyerrez.signshop.inventories.SignShopEditInventory;
import io.github.gutyerrez.signshop.inventories.SignShopPreviewItemInventory;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

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
                    event.setCancelled(true);

                    if (player.isSneaking()) {
                        if (player.hasPermission("signshop.shops.edit")) {
                            player.openInventory(
                                    new SignShopEditInventory(signShop)
                            );
                        } else {
                            player.openInventory(
                                    new SignShopPreviewItemInventory(signShop)
                            );
                        }
                        return;
                    }

                    ImmutableSet<String> tryBuy = signShop.tryBuy(player);

                    if (!tryBuy.isEmpty()) {
                        tryBuy.forEach(player::sendMessage);
                        return;
                    }

                    if (SignShopProvider.Hooks.ECONOMY.isActive()) {
                        SignShopProvider.Hooks.ECONOMY.get().withdrawPlayer(player, signShop.getPrice());
                    }

                    player.sendMessage(String.format(
                            "§aVocê comprou %dx %s por %s coins.",
                            signShop.getQuantity(),
                            signShop.getName() == null ? ChatColor.stripColor(signShop.getFancyName()) : signShop.getName(),
                            NumberUtils.format(signShop.getPrice())
                    ));

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
                SignShop signShop = this.getSignShop(event);

                if (signShop != null) {
                    event.setCancelled(true);

                    ImmutableSet<String> trySell = signShop.trySell(player);

                    if (!trySell.isEmpty()) {
                        trySell.forEach(player::sendMessage);
                        return;
                    }

                    Integer count = InventoryUtils.countItems(
                            player.getInventory(),
                            signShop.getItem()
                    );

                    Integer removed = InventoryUtils.removeItems(
                            player.getInventory(),
                            signShop.getItem(),
                            player.isSneaking() ? count : signShop.getQuantity()
                    );

                    if (removed > 0) {
                        Double moneyReceived = signShop.getPrice() * removed / signShop.getQuantity();

                        if (SignShopProvider.Hooks.ECONOMY.isActive()) {
                            SignShopProvider.Hooks.ECONOMY.get().depositPlayer(player, moneyReceived);
                        }

                        player.sendMessage(String.format(
                                "§aVocê vendeu %d itens e ganhou %s coins.",
                                removed,
                                NumberUtils.format(moneyReceived)
                        ));
                    }
                    return;
                }
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
            return SignShopProvider.Cache.Local.SIGN_SHOP.provide().get(block);
        }

        return null;
    }

}
