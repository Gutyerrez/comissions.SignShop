package io.github.gutyerrez.signshop.inventories;

import io.github.gutyerrez.core.spigot.inventory.CustomInventory;
import io.github.gutyerrez.core.spigot.misc.utils.ItemBuilder;
import io.github.gutyerrez.signshop.SignShopProvider;
import io.github.gutyerrez.signshop.api.SignShop;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author SrGutyerrez
 */
public class SignShopChangeItemInventory extends CustomInventory {

    private SignShop signShop;

    public SignShopChangeItemInventory(SignShop signShop, CustomInventory backInventory) {
        super(4 * 9, "Coloque o item no slot");

        this.signShop = signShop;

        for (int i = 0; i < this.getSize(); i++) {
            if (i == 13) {
                continue;
            }

            this.setItem(
                    i,
                    new ItemBuilder(Material.STAINED_GLASS_PANE)
                            .durability(5)
                            .name("§aColoque o item no meio do inventário.")
                            .make()
            );
        }

        if (backInventory != null) {
            this.backItem(backInventory);
        }
    }

    public SignShopChangeItemInventory(SignShop signShop) {
        this(signShop, null);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory == null) {
            return;
        }

        if (clickedInventory.equals(this) && event.getSlot() != 13) {
            event.setCancelled(true);
            return;
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        super.onClose(event);

        Inventory inventory = event.getInventory();

        ItemStack itemStack = inventory.getItem(13);

        if (itemStack == null || itemStack.getType() == Material.AIR || (this.signShop.getItem() != null && itemStack.isSimilar(this.signShop.getItem()))) {
            return;
        }

        if (this.signShop == null) return;

        this.signShop.setItem(itemStack);

        SignShopProvider.Repositories.SIGN_SHOP.provide().update(
                this.signShop.getId(),
                this.signShop.getName(),
                this.signShop.getItem(),
                this.signShop.getQuantity(),
                this.signShop.getPrice()
        );
    }
}
