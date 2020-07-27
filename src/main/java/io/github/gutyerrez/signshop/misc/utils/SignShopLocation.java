package io.github.gutyerrez.signshop.misc.utils;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bukkit.block.Block;

/**
 * @author SrGutyerrez
 */
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = { "worldName", "x", "y", "z" })
public class SignShopLocation {

    private final String worldName;
    private final Integer x, y, z;

    public SignShopLocation(Block block) {
        this(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
    }

}
