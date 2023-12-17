package ua.edu.cdu.vu.event.notification.telegram.bot.component.step.event;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.UserState;
import ua.edu.cdu.vu.event.notification.telegram.bot.service.TelegramSenderService;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.step.Step;
import ua.edu.cdu.vu.event.notification.telegram.bot.util.DateTimeUtils;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import static ua.edu.cdu.vu.event.notification.telegram.bot.util.EventConstants.EVENT_DATETIME;
import static ua.edu.cdu.vu.event.notification.telegram.bot.util.TelegramBotConstants.NO_DATA;

@RequiredArgsConstructor
public class UpdateEventDateTimeStep implements Step {

    private static final String EVENT_DATE_TIME_IS_NOT_VALID = "Event datetime is not valid or is in the past. Please, try again";
    private static final String ENTER_EVENT_NOTES = "Enter event notes (-, if these are missing):";

    private final TelegramSenderService telegramSenderService;
    private final Clock clock;

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
    public Optional<UserState> process(Update update, UserState userState) throws TelegramApiException {
        long chatId = getChatId(update);
        if (NO_DATA.equals(update.getMessage().getText()) || isValid(update.getMessage().getText())) {
            telegramSenderService.send(chatId, ENTER_EVENT_NOTES);
            return Optional.of(userState.nextStep().addDataEntry(EVENT_DATETIME, update.getMessage().getText()));
        }
        telegramSenderService.send(chatId, EVENT_DATE_TIME_IS_NOT_VALID);

        return Optional.of(userState);
    }

    private boolean isValid(String eventDateTime) {
        try {
            LocalDateTime dateTime = DateTimeUtils.parse(eventDateTime);
            return dateTime.isAfter(LocalDateTime.now(clock));
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
