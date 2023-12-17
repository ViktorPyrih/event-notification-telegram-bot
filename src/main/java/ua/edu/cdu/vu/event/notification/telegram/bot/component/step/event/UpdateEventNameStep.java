package ua.edu.cdu.vu.event.notification.telegram.bot.component.step.event;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.UserState;
import ua.edu.cdu.vu.event.notification.telegram.bot.service.TelegramSenderService;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.step.Step;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Optional;

import static ua.edu.cdu.vu.event.notification.telegram.bot.util.Buttons.dateTimeKeyboard;
import static ua.edu.cdu.vu.event.notification.telegram.bot.util.EventConstants.EVENT_NAME;

@RequiredArgsConstructor
public class UpdateEventNameStep implements Step {

    private static final String ENTER_EVENT_DATE_TIME = "Enter event datetime in UTC (example: 24/10/2023T12:00):";

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
        telegramSenderService.send(getChatId(update), ENTER_EVENT_DATE_TIME, dateTimeKeyboard(ZonedDateTime.now(clock).toLocalDate()));
        return Optional.of(userState.nextStep().addDataEntry(EVENT_NAME, update.getMessage().getText()));
    }
}
