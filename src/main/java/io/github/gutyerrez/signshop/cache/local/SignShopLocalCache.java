package io.github.gutyerrez.signshop.cache.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.gutyerrez.core.shared.cache.LocalCache;
import io.github.gutyerrez.signshop.api.SignShop;
import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 * @author SrGutyerrez
 */
public class SignShopLocalCache implements LocalCache {

    private final Cache<Block, SignShop> SHOPS = Caffeine.newBuilder()
            .build();

    public SignShop get(Block block) {
        return this.SHOPS.getIfPresent(block);
    }

    public void add(Location location, SignShop signShop) {
        this.add(location.getBlock(), signShop);
    }

    public void add(Block block, SignShop signShop) {
        this.SHOPS.put(block, signShop);
    }

    public void remove(Block block) {
        this.SHOPS.invalidate(block);
    }

}
