package ua.edu.cdu.vu.event.notification.telegram.bot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static ua.edu.cdu.vu.event.notification.telegram.bot.model.TimeUnit.*;

@Getter
@RequiredArgsConstructor
public enum TimeLabel {

    ONE_MINUTE("1m", 1, MINUTES), FIVE_MINUTES("5m", 5, MINUTES), TEN_MINUTES("10m", 10, MINUTES), FIFTEEN_MINUTES("15m", 15, MINUTES), THIRTY_MINUTES("30m", 30, MINUTES),
    ONE_HOUR("1h", 1, HOURS), TWO_HOURS("2h", 2, HOURS), FOUR_HOURS("3h", 3, HOURS), EIGHT_HOURS("8h",8, HOURS), TWELVE_HOURS("12h", 12, HOURS),
    ONE_DAY("1d", 1, DAYS), TWO_DAYS("2d", 2, DAYS), THREE_DAYS("3d", 3, DAYS), FIVE_DAYS("5d", 5, DAYS), SEVEN_DAYS("7d", 7, DAYS);

    private final String name;
    private final int amount;
    private final TimeUnit units;

    public static TimeLabel byName(String name) {
        return Arrays.stream(values())
                .filter(timeLabel -> timeLabel.getName().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No time label for name: " + name));
    }
}
