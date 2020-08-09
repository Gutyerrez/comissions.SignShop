package io.github.gutyerrez.signshop.storage.specs;

import io.github.gutyerrez.core.shared.storage.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.core.shared.storage.repositories.specs.UpdateSqlSpec;
import io.github.gutyerrez.core.spigot.misc.utils.InventoryUtils;
import io.github.gutyerrez.signshop.SignShopConstants;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.sql.PreparedStatement;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class UpdateSignShopSpec extends UpdateSqlSpec<Void> {

    private final Integer id;
    private final String name;
    private final ItemStack item;
    private final Integer quantity;
    private final Double price;

    @Override
    public Void parser(int affectedRows) {
        return null;
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "UPDATE `%s` SET `name`=?, `serialized_item`=?, `quantity`=?, `price`=? WHERE `id`=?;",
                    SignShopConstants.Databases.Mysql.Tables.SIGN_SHOP_TABLE_NAME
            );

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.item == null ? null : InventoryUtils.serializeContents(
                    new ItemStack[]{
                            this.item
                    }
            ));
            preparedStatement.setInt(3, this.quantity);
            preparedStatement.setDouble(4, this.price);
            preparedStatement.setInt(5, this.id);

            return preparedStatement;
        };
    }

}
