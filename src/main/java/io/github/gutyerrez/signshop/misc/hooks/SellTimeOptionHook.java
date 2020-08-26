package io.github.gutyerrez.signshop.misc.hooks;

import io.github.gutyerrez.core.shared.misc.hooks.Hook;
import io.github.gutyerrez.signshop.SignShopPlugin;

/**
 * @author SrGutyerrez
 */
public class SellTimeOptionHook extends Hook<Boolean>
{

    @Override
    public Boolean prepare()
    {
        return this.setInstance(
                SignShopPlugin.getInstance().getConfig().getBoolean("settings.sell_options.enable")
        );
    }

}
