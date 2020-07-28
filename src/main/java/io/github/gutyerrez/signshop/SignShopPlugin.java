package io.github.gutyerrez.signshop;

import io.github.gutyerrez.core.spigot.CustomPlugin;
import io.github.gutyerrez.signshop.listener.AsyncPlayerChatListener;
import io.github.gutyerrez.signshop.listener.BlockBreakListener;
import io.github.gutyerrez.signshop.listener.PlayerInteractListener;
import io.github.gutyerrez.signshop.listener.SignChangeListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.plugin.PluginManager;

/**
 * @author SrGutyerrez
 */
public class SignShopPlugin extends CustomPlugin {

    @Getter
    private static SignShopPlugin instance;

    public SignShopPlugin() {
        SignShopPlugin.instance = this;
    }

    @Override
    public void onEnable() {
        SignShopProvider.prepare();

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new SignChangeListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new AsyncPlayerChatListener(), this);

        SignShopProvider.Repositories.SIGN_SHOP.provide().fetchAll()
                .forEach((location, signShop) -> {
                    Chunk chunk = location.getChunk();

                    if (!chunk.isLoaded()) {
                        chunk.load();
                    }

                    SignShopProvider.Cache.Local.SIGN_SHOP.provide().add(location, signShop);
                });
    }

    public static Object get(String key) {
        return SignShopPlugin.instance.getConfig().get(key);
    }

}
