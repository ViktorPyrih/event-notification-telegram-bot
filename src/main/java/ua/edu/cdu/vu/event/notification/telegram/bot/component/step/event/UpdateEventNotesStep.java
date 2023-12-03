package ua.edu.cdu.vu.event.notification.telegram.bot.component.step.event;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.UserState;
import ua.edu.cdu.vu.event.notification.telegram.bot.service.TelegramSenderService;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.step.Step;

import java.util.Optional;

import static ua.edu.cdu.vu.event.notification.telegram.bot.util.Buttons.remindersKeyboard;
import static ua.edu.cdu.vu.event.notification.telegram.bot.util.EventConstants.EVENT_NOTES;

@RequiredArgsConstructor
public class UpdateEventNotesStep implements Step {

    private static final String CHOOSE_EVENT_REMINDERS = "Choose event reminders, please:";

    private final TelegramSenderService telegramSenderService;

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
        telegramSenderService.send(getChatId(update), CHOOSE_EVENT_REMINDERS, remindersKeyboard());
        return Optional.of(userState.nextStep().addDataEntry(EVENT_NOTES, update.getMessage().getText()));
    }
}
