package io.github.gutyerrez.signshop.storage.specs;

import io.github.gutyerrez.core.shared.storage.repositories.specs.InsertSqlSpec;
import io.github.gutyerrez.core.shared.storage.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.core.shared.world.location.SerializedLocation;
import io.github.gutyerrez.signshop.SignShopConstants;
import io.github.gutyerrez.signshop.api.SignShop;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class InsertSignShopSpec extends InsertSqlSpec<SignShop> {

    private final String name;
    private final SignShop.Type type;
    private final Integer quantity;
    private final Double price;
    private final SerializedLocation serializedLocation;

    @Override
    public SignShop parser(int affectedRows, ResultSet keyHolder) throws SQLException {
        return new SignShop(
                keyHolder.getInt("id"),
                this.name,
                this.type,
                null,
                this.quantity,
                this.price,
                this.serializedLocation
        );
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "INSERT INTO `%s` " +
                            "(" +
                            "`name`," +
                            "`type`," +
                            "`quantity`," +
                            "`price`," +
                            "`world_name`," +
                            "`x`," +
                            "`y`," +
                            "`z`" +
                            ") VALUES (?,?,?,?,?,?,?,?);",
                    SignShopConstants.Databases.Mysql.Tables.SIGN_SHOP_TABLE_NAME
            );

            PreparedStatement preparedStatement = connection.prepareStatement(
                    query,
                    Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.type.toString());
            preparedStatement.setInt(3, this.quantity);
            preparedStatement.setDouble(4, this.price);
            preparedStatement.setString(5, this.serializedLocation.getWorldName());
            preparedStatement.setDouble(6, this.serializedLocation.getX());
            preparedStatement.setDouble(7, this.serializedLocation.getY());
            preparedStatement.setDouble(8, this.serializedLocation.getZ());

            return preparedStatement;
        };
    }

}
