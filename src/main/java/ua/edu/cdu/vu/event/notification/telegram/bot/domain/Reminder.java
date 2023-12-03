package ua.edu.cdu.vu.event.notification.telegram.bot.domain;

import lombok.Builder;
import lombok.Value;
import ua.edu.cdu.vu.event.notification.telegram.bot.model.TimeUnit;

@Value
@Builder
public class Reminder {

    Integer unitsBeforeStart;
    TimeUnit units;

    @Override
    public String toString() {
        return "%d %s".formatted(unitsBeforeStart, units);
    }
}
