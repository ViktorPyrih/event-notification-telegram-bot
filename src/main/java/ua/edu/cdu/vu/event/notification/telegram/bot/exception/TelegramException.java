package ua.edu.cdu.vu.event.notification.telegram.bot.exception;

public class TelegramException extends RuntimeException {

    private static final String MESSAGE = "Telegram API cannot be reached";

    public TelegramException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
