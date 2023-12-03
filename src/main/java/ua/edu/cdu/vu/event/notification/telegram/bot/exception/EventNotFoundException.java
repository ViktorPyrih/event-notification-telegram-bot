package ua.edu.cdu.vu.event.notification.telegram.bot.exception;

public class EventNotFoundException extends RuntimeException {

    private static final String EVENT_NOT_FOUND = "Event with id: '%d' not found";

    public EventNotFoundException(long eventId) {
        super(EVENT_NOT_FOUND.formatted(eventId));
    }
}
