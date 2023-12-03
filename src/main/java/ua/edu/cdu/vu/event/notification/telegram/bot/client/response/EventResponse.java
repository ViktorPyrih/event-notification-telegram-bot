package ua.edu.cdu.vu.event.notification.telegram.bot.client.response;

import ua.edu.cdu.vu.event.notification.telegram.bot.client.request.EventReminder;

import java.time.ZonedDateTime;
import java.util.List;

public record EventResponse(Long id, String name, String notes, ZonedDateTime dateTime, List<EventReminder> reminders) {
}
