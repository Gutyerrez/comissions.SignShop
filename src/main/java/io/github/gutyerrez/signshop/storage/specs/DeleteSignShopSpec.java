package io.github.gutyerrez.signshop.storage.specs;

import io.github.gutyerrez.core.shared.storage.repositories.specs.DeleteSqlSpec;
import io.github.gutyerrez.core.shared.storage.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.signshop.SignShopConstants;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class DeleteSignShopSpec extends DeleteSqlSpec<Void> {

    private final Integer id;

    @Override
    public Void parser(int affectedRows) {
        return null;
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "DELETE FROM `%s` WHERE `id`=?;",
                    SignShopConstants.Databases.Mysql.Tables.SIGN_SHOP_TABLE_NAME
            );

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, this.id);

            return preparedStatement;
        };
    }
}
