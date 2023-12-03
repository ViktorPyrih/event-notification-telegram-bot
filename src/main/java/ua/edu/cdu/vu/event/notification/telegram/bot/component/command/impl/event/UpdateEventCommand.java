package ua.edu.cdu.vu.event.notification.telegram.bot.component.command.impl.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.command.BotCommand;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.command.Command;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.command.parser.BotCommandParser;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.Event;
import ua.edu.cdu.vu.event.notification.telegram.bot.service.EventNotificationService;
import ua.edu.cdu.vu.event.notification.telegram.bot.service.FlowService;
import ua.edu.cdu.vu.event.notification.telegram.bot.util.DateTimeUtils;

import static ua.edu.cdu.vu.event.notification.telegram.bot.component.step.StepConfiguration.CREATE_EVENT_FLOW_ID;
import static ua.edu.cdu.vu.event.notification.telegram.bot.util.EventConstants.*;

@Command(BotCommand.UPDATE)
@RequiredArgsConstructor
public class UpdateEventCommand implements BotCommand {

    private static final String ENTER_EVENT_NAME = "Enter event name, please ('-', if nothing changed)";

    private final BotCommandParser botCommandParser;
    private final EventNotificationService eventNotificationService;
    private final FlowService flowService;
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public BotCommand.Result execute(Update update) {
        if (update.hasCallbackQuery()) {
            long eventId = botCommandParser.parseLongArgument(update.getCallbackQuery().getData());
            Event event = eventNotificationService.getEventById(eventId);
            String remindersJson = objectMapper.writeValueAsString(event.getReminders());
            flowService.start(CREATE_EVENT_FLOW_ID, update.getCallbackQuery().getFrom().getId(),
                    userState -> userState.addDataEntry(EVENT_ID, eventId)
                            .addDataEntry(EVENT_NAME, event.getName())
                            .addDataEntry(EVENT_DATETIME, DateTimeUtils.format(event.getDateTime().toLocalDateTime()))
                            .addDataEntry(EVENT_NOTES, event.getNotes())
                            .addDataEntry(EVENT_REMINDERS, remindersJson));
        }

        return BotCommand.Result.builder()
                .response(ENTER_EVENT_NAME)
                .build();
    }
}
