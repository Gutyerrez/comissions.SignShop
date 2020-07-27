package io.github.gutyerrez.signshop.cache.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.gutyerrez.core.shared.cache.LocalCache;
import io.github.gutyerrez.signshop.api.SignShop;
import io.github.gutyerrez.signshop.misc.utils.SignShopLocation;
import org.bukkit.Location;

/**
 * @author SrGutyerrez
 */
public class SignShopLocalCache implements LocalCache {

    private final Cache<SignShopLocation, SignShop> SHOPS = Caffeine.newBuilder()
            .build();

    public SignShop get(Location location) {
        return this.SHOPS.getIfPresent(new SignShopLocation(location.getBlock()));
    }

    public void add(Location location, SignShop signShop) {
        this.SHOPS.put(new SignShopLocation(location.getBlock()), signShop);
    }

    public void remove(Location location) {
        this.SHOPS.invalidate(location);
    }

}
