package io.github.gutyerrez.signshop.inventories;

import io.github.gutyerrez.core.spigot.inventory.CustomInventory;
import io.github.gutyerrez.core.spigot.misc.utils.old.ItemBuilder;
import io.github.gutyerrez.signshop.api.SignShop;

/**
 * @author SrGutyerrez
 */
public class SignShopPreviewItemInventory extends CustomInventory {

    public SignShopPreviewItemInventory(SignShop signShop) {
        super(3 * 9, "Informações da loja");

        this.setItem(
                13,
                new ItemBuilder(signShop.getPreviewItemStack())
                        .name(signShop.getFancyName())
                        .amount(signShop.getQuantity())
                        .make()
        );
    }

}
