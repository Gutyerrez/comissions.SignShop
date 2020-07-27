package io.github.gutyerrez.signshop.cache.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.gutyerrez.core.shared.cache.LocalCache;
import io.github.gutyerrez.signshop.api.SignShop;
import org.bukkit.Location;

/**
 * @author SrGutyerrez
 */
public class SignShopLocalCache implements LocalCache {

    private final Cache<Location, SignShop> SHOPS = Caffeine.newBuilder()
            .build();

    public SignShop get(Location location) {
        return this.SHOPS.getIfPresent(location);
    }

    public void add(Location location, SignShop signShop) {
        this.SHOPS.put(location, signShop);
    }

    public void remove(Location location) {
        this.SHOPS.invalidate(location);
    }

}
