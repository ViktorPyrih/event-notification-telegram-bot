package ua.edu.cdu.vu.event.notification.telegram.bot.client.request;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.List;

@Value
@Builder
public class EventRequest {

    String name;
    String notes;
    String key;
    ZonedDateTime dateTime;
    List<EventReminder> reminders;

}
