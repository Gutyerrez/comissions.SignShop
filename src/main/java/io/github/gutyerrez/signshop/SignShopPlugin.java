package io.github.gutyerrez.signshop;

import io.github.gutyerrez.core.shared.exceptions.ServiceAlreadyPreparedException;
import io.github.gutyerrez.core.spigot.CustomPlugin;
import io.github.gutyerrez.signshop.listener.AsyncPlayerChatListener;
import io.github.gutyerrez.signshop.listener.SignChangeListener;
import lombok.Getter;
import org.bukkit.Bukkit;
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
        saveDefaultConfig();

        try {
            SignShopProvider.prepare();
        } catch (ServiceAlreadyPreparedException exception) {
            exception.printStackTrace();
        }

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new SignChangeListener(), this);
        pluginManager.registerEvents(new AsyncPlayerChatListener(), this);

        SignShopProvider.Repositories.SIGN_SHOP.provide().fetchAll()
                .forEach(SignShopProvider.Cache.Local.SIGN_SHOP.provide()::add);
    }

    public static Object get(String key) {
        return SignShopPlugin.instance.getConfig().get(key);
    }

}
