package io.github.gutyerrez.signshop.misc.utils;

import io.github.gutyerrez.core.shared.misc.utils.ChatColor;
import io.github.gutyerrez.signshop.api.SignShop;

/**
 * @author SrGutyerrez
 */
public class SignShopUtils {

    public static Boolean isValid(String line) {
        line = ChatColor.stripColor(line);

        SignShop.Type type = SignShop.Type.tryParse(line);

        return type != null;
    }

}
