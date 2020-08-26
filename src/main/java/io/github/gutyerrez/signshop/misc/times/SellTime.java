package io.github.gutyerrez.signshop.misc.times;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author SrGutyerrez
 */
@Getter
@RequiredArgsConstructor
public class SellTime
{

    private final Integer startHourTime, startMinuteTime;
    private final Integer endHourTime, endMinuteTime;
    private final Double percent;

    public final Boolean isBetweenStartTimeAndEndTime()
    {
        Long currentTime = System.currentTimeMillis();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        String formatted = simpleDateFormat.format(currentTime);

        Integer currentHour = Ints.tryParse(formatted.split(":")[0]);
        Integer currentMinute = Ints.tryParse(formatted.split(":")[1]);

//        System.out.println("Inicia em: " + startHourTime + ":" + startMinuteTime);
//        System.out.println("Finaliza em: " + endHourTime + ":" + endMinuteTime);
//
//        System.out.println("Atualmente: " + currentHour + ":" + currentMinute);

        Boolean isStartedHour = currentHour >= this.startHourTime;
        Boolean isStartedMinutes = currentMinute >= this.startMinuteTime;
        Boolean isNotEndedHour = currentHour <= this.endHourTime;
        Boolean isNotEndedMinutes = currentMinute >= this.endMinuteTime;

//        System.out.println("Verificação 1: " + (isStartedHour));
//        System.out.println("Verificação 2: " + (isStartedMinutes));
//        System.out.println("Verificação 3: " + (isEndedHour));
//        System.out.println("Verificação 4: " + (isEndedMinutes));

        return isStartedHour && isStartedMinutes && isNotEndedHour && isNotEndedMinutes;
    }

    private Long parseToHours(String str)
    {
        return TimeUnit.HOURS.toMillis(Longs.tryParse(str.split(":")[0]));
    }

    private Long parseToMinutes(String str)
    {
        return TimeUnit.MINUTES.toMillis(Longs.tryParse(str.split(":")[1]));
    }

}
