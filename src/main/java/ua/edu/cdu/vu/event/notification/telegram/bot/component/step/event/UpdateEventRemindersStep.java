package ua.edu.cdu.vu.event.notification.telegram.bot.component.step.event;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.edu.cdu.vu.event.notification.telegram.bot.client.exception.EventNotFoundException;
import ua.edu.cdu.vu.event.notification.telegram.bot.client.exception.EventNotValidException;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.Event;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.Reminder;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.UserState;
import ua.edu.cdu.vu.event.notification.telegram.bot.mapper.EventMapper;
import ua.edu.cdu.vu.event.notification.telegram.bot.mapper.ReminderMapper;
import ua.edu.cdu.vu.event.notification.telegram.bot.model.TimeLabel;
import ua.edu.cdu.vu.event.notification.telegram.bot.service.EventNotificationService;
import ua.edu.cdu.vu.event.notification.telegram.bot.service.TelegramSenderService;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.step.Step;

import java.util.Optional;
import java.util.Set;

import static ua.edu.cdu.vu.event.notification.telegram.bot.util.Buttons.eventKeyboard;
import static ua.edu.cdu.vu.event.notification.telegram.bot.util.EventConstants.EVENT_REMINDERS;

@RequiredArgsConstructor
public class UpdateEventRemindersStep implements Step {

    private static final String REMINDER_ADDED = "Reminder: '%s' added";
    private static final String EVENT_CREATED_UPDATED = "Event with id: '%d' created/updated";
    private static final String RECREATE_EVENT = "Please, recreate your event";
    private static final String REMINDER_IS_IN_WRONG_FORMAT = "Error. Reminder is in a wrong format";

    private static final String EMPTY_LIST = "[]";
    private static final String COMPLETE = "COMPLETE";

    private final TelegramSenderService telegramSenderService;
    private final ReminderMapper reminderMapper;
    private final ObjectMapper objectMapper;
    private final EventMapper eventMapper;
    private final EventNotificationService eventNotificationService;

    private final int flowId;
    private final int stepId;

    @Override
    public int flowId() {
        return flowId;
    }

    @Override
    public int stepId() {
        return stepId;
    }

    @Override
    @SneakyThrows
    public Optional<UserState> process(Update update, UserState userState) {
        if (COMPLETE.equals(update.getMessage().getText())) {
            return processCompleteCommand(update, userState);
        }

        return processUserText(update, userState);
    }

    private Optional<UserState> processCompleteCommand(Update update, UserState userState) throws TelegramApiException {
        long chatId = getChatId(update);
        Event event = eventMapper.convertToDomain(userState, update.getMessage().getChatId());
        try {
            long eventId = eventNotificationService.upsertEvent(event);
            InlineKeyboardMarkup keyboard = eventKeyboard(eventId);
            telegramSenderService.send(chatId, EVENT_CREATED_UPDATED.formatted(eventId), keyboard);
        } catch (EventNotValidException e) {
            telegramSenderService.send(chatId, e.getMessage());
            telegramSenderService.send(chatId, RECREATE_EVENT);

            return Optional.of(userState.firstStep());
        } catch (EventNotFoundException e) {
            telegramSenderService.send(chatId, e.getMessage());

            return Optional.of(userState);
        }

        return Optional.empty();
    }

    private Optional<UserState> processUserText(Update update, UserState userState) throws Exception {
        long chatId = getChatId(update);
        try {
            TimeLabel timeLabel = TimeLabel.byName(update.getMessage().getText());
            String remindersJson = userState.findDataEntry(EVENT_REMINDERS).orElse(EMPTY_LIST);

            var reminders = objectMapper.readValue(remindersJson, new TypeReference<Set<Reminder>>() {});
            reminders.add(reminderMapper.convertToDomain(timeLabel));

            telegramSenderService.send(chatId, REMINDER_ADDED.formatted(timeLabel.getName()));

            return Optional.of(userState.addDataEntry(EVENT_REMINDERS, objectMapper.writeValueAsString(reminders)));
        } catch (IllegalArgumentException e) {
            telegramSenderService.send(chatId, REMINDER_IS_IN_WRONG_FORMAT);

            return Optional.of(userState);
        }
    }
}
