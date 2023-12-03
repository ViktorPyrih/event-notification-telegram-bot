package ua.edu.cdu.vu.event.notification.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.Event;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.Notification;
import ua.edu.cdu.vu.event.notification.telegram.bot.exception.TelegramException;

import static ua.edu.cdu.vu.event.notification.telegram.bot.util.Buttons.eventKeyboard;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final String UPCOMING = "<b>UPCOMING</b> ";

    private final EventNotificationService eventNotificationService;
    private final TelegramSenderService telegramSenderService;

    public void processNotification(Notification notification) {
        Event event = eventNotificationService.getEventById(notification.eventId());
        try {
            String message = UPCOMING + event.toString();
            telegramSenderService.send(Long.parseLong(notification.key()), message, eventKeyboard(event.getId()));
        } catch (TelegramApiException e) {
            throw new TelegramException(e);
        }
    }
}
