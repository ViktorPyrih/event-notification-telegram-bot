package ua.edu.cdu.vu.event.notification.telegram.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.edu.cdu.vu.event.notification.telegram.bot.configuration.BotConfiguration;
import ua.edu.cdu.vu.event.notification.telegram.bot.service.TelegramBotService;

@SpringBootApplication
public class EventNotificationTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventNotificationTelegramBotApplication.class, args);
    }

    @Slf4j
    @Component
    public static class EventNotificationTelegramBot extends TelegramLongPollingBot {

        private final BotConfiguration botConfiguration;
        private final TelegramBotService telegramBotService;

        public EventNotificationTelegramBot(BotConfiguration botConfiguration, TelegramBotService telegramBotService) {
            super(botConfiguration.getToken());
            this.botConfiguration = botConfiguration;
            this.telegramBotService = telegramBotService;
        }

        @Override
        public void onUpdateReceived(Update update) {
            log.info("Update received: {}", update);
            telegramBotService.process(update);
        }

        @Override
        public String getBotUsername() {
            return botConfiguration.getUsername();
        }
    }

}
