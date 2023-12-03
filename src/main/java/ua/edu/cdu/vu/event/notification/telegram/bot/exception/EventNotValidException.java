package ua.edu.cdu.vu.event.notification.telegram.bot.exception;

import lombok.Getter;

@Getter
public class EventNotValidException extends RuntimeException {

    private static final String EVENT_NOT_VALID = "Event is not valid: %s";

    public EventNotValidException(String reason) {
        super(EVENT_NOT_VALID.formatted(reason));
    }
}
