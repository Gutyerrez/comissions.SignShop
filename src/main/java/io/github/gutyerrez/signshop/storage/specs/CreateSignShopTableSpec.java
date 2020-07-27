package io.github.gutyerrez.signshop.storage.specs;

import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.ExecuteSqlSpec;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCallback;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.signshop.SignShopConstants;

/**
 * @author SrGutyerrez
 */
public class CreateSignShopTableSpec extends ExecuteSqlSpec<Void> {

    @Override
    public PreparedStatementCallback<Void> getPreparedStatementCallback() {
        return null;
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "CREATE TABLE IF NOT EXISTS `%s` " +
                            "(" +
                            "`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                            "`name` VARCHAR(255)," +
                            "`type` VARCHAR(255) NOT NULL," +
                            "`serialized_item` VARCHAR(255)," +
                            "`quantity` INTEGER NOT NULL," +
                            "`price` DOUBLE NOT NULL," +
                            "`world_name` VARCHAR(25)," +
                            "`x` DOUBLE NOT NULL," +
                            "`y` DOUBLE NOT NULL," +
                            "`z` DOUBLE NOT NULL" +
                            ");",
                    SignShopConstants.Databases.Mysql.Tables.SIGN_SHOP_TABLE_NAME
            );

            return connection.prepareStatement(query);
        };
    }
}
