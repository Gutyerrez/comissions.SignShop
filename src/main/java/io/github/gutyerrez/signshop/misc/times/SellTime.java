package io.github.gutyerrez.signshop.misc.times;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * @author SrGutyerrez
 */
@Getter
@RequiredArgsConstructor
public class SellTime
{

    private final String startTime;
    private final String endTime;
    private final Double percent;

    public final Boolean isBetweenStartTimeAndEndTime()
    {
        Date startDate = new Date(this.startTime);
        Date endDate = new Date(this.endTime);

        Long currentTime = System.currentTimeMillis();

        Long startTime = startDate.getTime();
        Long endTime = endDate.getTime();

        if (currentTime >= startTime && endTime <= currentTime) {
            return true;
        }

        return false;
    }

}
