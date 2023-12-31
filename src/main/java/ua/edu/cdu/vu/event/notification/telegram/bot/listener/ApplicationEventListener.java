package ua.edu.cdu.vu.event.notification.telegram.bot.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.edu.cdu.vu.event.notification.telegram.bot.EventNotificationTelegramBotApplication;

@Component
@RequiredArgsConstructor
public class ApplicationEventListener {

    private final TelegramBotsApi api;
    private final EventNotificationTelegramBotApplication.EventNotificationTelegramBot bot;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStartup() throws TelegramApiException {
        api.registerBot(bot);
    }
}
