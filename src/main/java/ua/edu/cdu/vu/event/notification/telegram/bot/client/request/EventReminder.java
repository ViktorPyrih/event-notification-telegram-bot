package ua.edu.cdu.vu.event.notification.telegram.bot.client.request;

import ua.edu.cdu.vu.event.notification.telegram.bot.model.TimeUnit;

public record EventReminder(Integer unitsBeforeStart, TimeUnit units) {
}
