package io.github.gutyerrez.signshop.storage.specs;

import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.ResultSetExtractor;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.SelectSqlSpec;
import io.github.gutyerrez.signshop.api.SignShop;
import org.bukkit.Location;

import java.util.Map;

/**
 * @author SrGutyerrez
 */
public class SelectAllSignShopsSpec extends SelectSqlSpec<Map<Location, SignShop>> {

    @Override
    public ResultSetExtractor<Map<Location, SignShop>> getResultSetExtractor() {
        return null;
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return null;
    }
}
