package io.github.gutyerrez.signshop.storage;

import io.github.gutyerrez.core.shared.contracts.storages.repositories.MysqlRepository;
import io.github.gutyerrez.core.shared.providers.MysqlDatabaseProvider;
import io.github.gutyerrez.core.shared.world.location.SerializedLocation;
import io.github.gutyerrez.signshop.api.SignShop;
import io.github.gutyerrez.signshop.storage.specs.CreateSignShopTableSpec;
import io.github.gutyerrez.signshop.storage.specs.DeleteSignShopSpec;
import io.github.gutyerrez.signshop.storage.specs.InsertOrUpdateSignShopSpec;
import io.github.gutyerrez.signshop.storage.specs.SelectAllSignShopsSpec;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * @author SrGutyerrez
 */
public class SignShopRepository extends MysqlRepository {

    public SignShopRepository(MysqlDatabaseProvider databaseProvider) {
        super(databaseProvider);

        this.createTable();
    }

    private void createTable() {
        this.query(new CreateSignShopTableSpec());
    }

    public Map<Location, SignShop> fetchAll() {
        return this.query(new SelectAllSignShopsSpec());
    }

    public SignShop insert(String name, SignShop.Type type, ItemStack item, Integer quantity, Double price, SerializedLocation serializedLocation) {
        return this.query(new InsertOrUpdateSignShopSpec(name, type, item, quantity, price, serializedLocation));
    }

    public void delete(Integer id) {
        this.query(new DeleteSignShopSpec(id));
    }

}
