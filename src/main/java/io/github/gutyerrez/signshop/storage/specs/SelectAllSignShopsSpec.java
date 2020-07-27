package io.github.gutyerrez.signshop.storage.specs;

import com.google.common.collect.Maps;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.ResultSetExtractor;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.SelectSqlSpec;
import io.github.gutyerrez.core.shared.world.location.SerializedLocation;
import io.github.gutyerrez.core.spigot.CoreSpigotConstants;
import io.github.gutyerrez.core.spigot.misc.utils.InventoryUtils;
import io.github.gutyerrez.signshop.SignShopConstants;
import io.github.gutyerrez.signshop.api.SignShop;
import org.bukkit.Location;

import java.util.Map;

/**
 * @author SrGutyerrez
 */
public class SelectAllSignShopsSpec extends SelectSqlSpec<Map<Location, SignShop>> {

    @Override
    public ResultSetExtractor<Map<Location, SignShop>> getResultSetExtractor() {
        return resultSet -> {
            Map<Location, SignShop> map = Maps.newHashMap();

            while (resultSet.next()) {
                SerializedLocation serializedLocation = new SerializedLocation(
                        resultSet.getString("world_name"),
                        resultSet.getDouble("x"),
                        resultSet.getDouble("y"),
                        resultSet.getDouble("z")
                );

                map.put(
                        serializedLocation.parser(CoreSpigotConstants.LOCATION_PARSER),
                        new SignShop(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                SignShop.Type.valueOf(
                                        resultSet.getString("type")
                                ),
                                InventoryUtils.deserializeContents(
                                        resultSet.getString("serialized_item")
                                )[0],
                                resultSet.getInt("quantity"),
                                resultSet.getDouble("prie"),
                                serializedLocation
                        )
                );
            }

            return map;
        };
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "SELECT * FROM `%s`;",
                    SignShopConstants.Databases.Mysql.Tables.SIGN_SHOP_TABLE_NAME
            );

            return connection.prepareStatement(query);
        };
    }
}
