package io.github.gutyerrez.signshop;

import io.github.gutyerrez.core.shared.CoreProvider;
import io.github.gutyerrez.core.shared.providers.LocalCacheProvider;
import io.github.gutyerrez.core.shared.providers.MysqlRepositoryProvider;
import io.github.gutyerrez.signshop.cache.local.SignShopLocalCache;
import io.github.gutyerrez.signshop.misc.hooks.EconomyHook;
import io.github.gutyerrez.signshop.misc.hooks.SellTimeOptionHook;
import io.github.gutyerrez.signshop.misc.times.cache.local.SellTimeLocalCache;
import io.github.gutyerrez.signshop.storage.SignShopRepository;

/**
 * @author SrGutyerrez
 */
public class SignShopProvider {

    public static void prepare() {
        Repositories.SIGN_SHOP.prepare();

        Cache.Local.SIGN_SHOP.prepare();
        Cache.Local.SELL_TIME.prepare();

        Hooks.ECONOMY.prepare();
        Hooks.SELL_TIME.prepare();
    }

    public static class Hooks {

        public static EconomyHook<?> ECONOMY = new EconomyHook<>();

        public static SellTimeOptionHook SELL_TIME = new SellTimeOptionHook();

    }

    public static class Cache {

        public static class Local {

            public static LocalCacheProvider<SignShopLocalCache> SIGN_SHOP = new LocalCacheProvider<>(
                    new SignShopLocalCache()
            );

            public static LocalCacheProvider<SellTimeLocalCache> SELL_TIME = new LocalCacheProvider<>(
                    new SellTimeLocalCache()
            );

        }

    }

    public static class Repositories {

        public static MysqlRepositoryProvider<SignShopRepository> SIGN_SHOP = new MysqlRepositoryProvider<>(
                () -> CoreProvider.Database.MySQL.MYSQL_MAIN,
                SignShopRepository.class
        );

    }

}
