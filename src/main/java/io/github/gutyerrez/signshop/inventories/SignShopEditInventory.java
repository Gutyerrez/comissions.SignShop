package io.github.gutyerrez.signshop.inventories;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import io.github.gutyerrez.core.spigot.inventory.ConfirmInventory;
import io.github.gutyerrez.core.spigot.inventory.CustomInventory;
import io.github.gutyerrez.core.spigot.misc.utils.ItemBuilder;
import io.github.gutyerrez.signshop.SignShopProvider;
import io.github.gutyerrez.signshop.api.SignShop;
import io.github.gutyerrez.signshop.listener.AsyncPlayerChatListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

/**
 * @author SrGutyerrez
 */
public class SignShopEditInventory extends CustomInventory {

    public SignShopEditInventory(SignShop signShop) {
        super(4 * 9, String.format("Loja - #%d", signShop.getId()));

        this.setItem(
                13,
                new ItemBuilder(signShop.getPreviewItemStack())
                        .name(signShop.getFancyName())
                        .make(),
                (event) -> {
                    Player player = (Player) event.getWhoClicked();

                    player.openInventory(
                            new SignShopChangeItemInventory(signShop)
                    );
                }
        );

        this.setItem(
                27,
                new ItemBuilder(Material.BUCKET)
                        .name("§eAlterar valor")
                        .flags(
                                ItemFlag.HIDE_ATTRIBUTES
                        )
                        .make(),
                (event) -> {
                    Player player = (Player) event.getWhoClicked();

                    player.sendMessage("§aInsira o novo valor desejado abaixo.");
                    player.sendMessage("§7Caso queira cancelar digite 'cancelar'.");

                    AsyncPlayerChatListener.on(
                            player,
                            (onChat) -> {
                                String message = onChat.getMessage();

                                if (message.equalsIgnoreCase("cancelar")) {
                                    return;
                                }

                                Double price = Doubles.tryParse(message);

                                if (price == null || price.isNaN() || price < 1) {
                                    player.sendMessage("§cVocê inseriu um preço inválido!");
                                    return;
                                }

                                signShop.setPrice(price);

                                SignShopProvider.Repositories.SIGN_SHOP.provide().update(
                                        signShop.getId(),
                                        signShop.getName(),
                                        signShop.getItem(),
                                        signShop.getQuantity(),
                                        signShop.getPrice()
                                );
                            }
                    );
                }
        );

        this.setItem(
                30,
                new ItemBuilder(Material.STORAGE_MINECART)
                        .name("§eAlterar quantia")
                        .flags(
                                ItemFlag.HIDE_ATTRIBUTES
                        )
                        .make(),
                (event) -> {
                    Player player = (Player) event.getWhoClicked();

                    player.sendMessage("§aInsira a nova quantia desejada abaixo.");
                    player.sendMessage("§7Caso queira cancelar digite 'cancelar'.");

                    AsyncPlayerChatListener.on(
                            player,
                            (onChat) -> {
                                String message = onChat.getMessage();

                                if (message.equalsIgnoreCase("cancelar")) {
                                    return;
                                }

                                Integer quantity = Ints.tryParse(message);

                                if (quantity == null || quantity < 1) {
                                    player.sendMessage("§cVocê inseriu uma quantia inválida!");
                                    return;
                                }

                                signShop.setQuantity(quantity);

                                SignShopProvider.Repositories.SIGN_SHOP.provide().update(
                                        signShop.getId(),
                                        signShop.getName(),
                                        signShop.getItem(),
                                        signShop.getQuantity(),
                                        signShop.getPrice()
                                );
                            }
                    );
                }
        );

        this.setItem(
                31,
                new ItemBuilder(Material.STORAGE_MINECART)
                        .name("§cDestruir")
                        .flags(
                                ItemFlag.HIDE_ATTRIBUTES
                        )
                        .make(),
                (event) -> {
                    Player player = (Player) event.getWhoClicked();

                    ConfirmInventory confirmInventory = new ConfirmInventory(
                            target -> {
                                Block block = signShop.getBlock();

                                block.breakNaturally();

                                SignShopProvider.Cache.Local.SIGN_SHOP.provide().remove(block.getLocation());
                                SignShopProvider.Repositories.SIGN_SHOP.provide().delete(signShop.getId());
                            },
                            target -> {
                                // nada
                            },
                            new ItemBuilder(Material.PAPER)
                                    .name(String.format("Loja - #%d", signShop.getId()))
                                    .make()
                    );

                    player.openInventory(confirmInventory.make(
                            "§7Ao clicar aqui, está",
                            "§7loja será deletada."
                    ));
                }
        );


        this.setItem(
                32,
                new ItemBuilder(Material.STORAGE_MINECART)
                        .name("§cDestruir")
                        .flags(
                                ItemFlag.HIDE_ATTRIBUTES
                        )
                        .make(),
                (event) -> {
                    Player player = (Player) event.getWhoClicked();

                    player.sendMessage("§aInsira o novo nome desejado abaixo.");
                    player.sendMessage("§7Caso queira cancelar digite 'cancelar'.");

                    AsyncPlayerChatListener.on(
                            player,
                            (onChat) -> {
                                String message = onChat.getMessage();

                                if (message.equalsIgnoreCase("cancelar")) {
                                    return;
                                }

                                signShop.setName(message);

                                SignShopProvider.Repositories.SIGN_SHOP.provide().update(
                                        signShop.getId(),
                                        signShop.getName(),
                                        signShop.getItem(),
                                        signShop.getQuantity(),
                                        signShop.getPrice()
                                );
                            }
                    );
                }
        );
    }

}
