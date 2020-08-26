package io.github.gutyerrez.signshop.misc.times.cache.local;

import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import io.github.gutyerrez.core.shared.cache.LocalCache;
import io.github.gutyerrez.signshop.SignShopPlugin;
import io.github.gutyerrez.signshop.misc.times.SellTime;

import java.util.Set;

/**
 * @author SrGutyerrez
 */
public class SellTimeLocalCache implements LocalCache
{

    private Set<SellTime> SELL_TIMES = Sets.newConcurrentHashSet();

    public Set<SellTime> get() {
        return this.SELL_TIMES;
    }

    @Override
    public void populate()
    {
        SignShopPlugin.getInstance().getConfig().getStringList("settings.sell_options.times").forEach(selltime -> {
            String[] hours = selltime.split("-");

            String startTime = hours[0];
            String endTime = hours[1].split("\\|")[0];
            Double percentage = Doubles.tryParse(hours[1].split("\\|")[1]);

            Integer startHourTime = Ints.tryParse(startTime.split(":")[0]);
            Integer startMinuteTime = Ints.tryParse(startTime.split(":")[1]);

            Integer endHourTime = Ints.tryParse(endTime.split(":")[0]);
            Integer endMinuteTime = Ints.tryParse(endTime.split(":")[1]);

            if (percentage != null && !percentage.isNaN() && percentage > 0) {
                SellTime sellTime = new SellTime(
                        startHourTime,
                        startMinuteTime,
                        endHourTime,
                        endMinuteTime,
                        percentage
                );

                this.SELL_TIMES.add(sellTime);
            }
        });
    }

}
