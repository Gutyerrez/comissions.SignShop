package io.github.gutyerrez.signshop.misc.hooks;

import io.github.gutyerrez.core.shared.misc.hooks.Hook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * @author SrGutyerrez
 */
public class EconomyHook<T extends Economy> extends Hook<T> {

    @Override
    public T prepare() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return null;
        }

        RegisteredServiceProvider<Economy> serviceProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

        if (serviceProvider == null) {
            return null;
        }

        return this.setInstance((T) serviceProvider.getProvider());
    }

}
