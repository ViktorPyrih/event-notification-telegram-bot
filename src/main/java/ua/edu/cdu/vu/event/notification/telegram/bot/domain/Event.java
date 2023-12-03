package ua.edu.cdu.vu.event.notification.telegram.bot.domain;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static ua.edu.cdu.vu.event.notification.telegram.bot.util.TelegramBotConstants.NO_DATA;

@Value
@Builder
public class Event {

    Long id;
    String key;
    String name;
    String notes;
    ZonedDateTime dateTime;
    List<Reminder> reminders;

    @Override
    public String toString() {
        return String.join(System.lineSeparator(),
                "Event #%d".formatted(id),
                "Name: %s".formatted(name),
                "Notes: %s".formatted(Optional.ofNullable(notes).orElse(NO_DATA)),
                "DateTime: %s".formatted(dateTime),
                "Reminders: %s".formatted(reminders)
        );
    }
}
