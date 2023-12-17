package ua.edu.cdu.vu.event.notification.telegram.bot.component.step;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.step.event.UpdateEventDateTimeStep;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.step.event.UpdateEventNameStep;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.step.event.UpdateEventNotesStep;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.step.event.UpdateEventRemindersStep;
import ua.edu.cdu.vu.event.notification.telegram.bot.mapper.EventMapper;
import ua.edu.cdu.vu.event.notification.telegram.bot.mapper.ReminderMapper;
import ua.edu.cdu.vu.event.notification.telegram.bot.service.EventNotificationService;
import ua.edu.cdu.vu.event.notification.telegram.bot.service.TelegramSenderService;

import java.time.Clock;

@Configuration
public class StepConfiguration {

    public static final int CREATE_EVENT_FLOW_ID = 1;

    @Bean
    public Step updateEventNameForCreateStep(TelegramSenderService telegramSenderService, Clock clock) {
        return new UpdateEventNameStep(telegramSenderService, clock, CREATE_EVENT_FLOW_ID, 1);
    }

    @Bean
    public Step updateEventDateTimeForCreateStep(TelegramSenderService telegramSenderService, Clock clock) {
        return new UpdateEventDateTimeStep(telegramSenderService, clock, CREATE_EVENT_FLOW_ID, 2);
    }

    @Bean
    public Step updateEventNotesForCreateStep(TelegramSenderService telegramSenderService) {
        return new UpdateEventNotesStep(telegramSenderService, CREATE_EVENT_FLOW_ID, 3);
    }

    @Bean
    public Step updateEventRemindersForCreateStep(TelegramSenderService telegramSenderService, ReminderMapper reminderMapper, ObjectMapper objectMapper, EventMapper eventMapper, EventNotificationService eventNotificationService) {
        return new UpdateEventRemindersStep(telegramSenderService, reminderMapper, objectMapper, eventMapper, eventNotificationService, CREATE_EVENT_FLOW_ID, 4);
    }
}
