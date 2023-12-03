package ua.edu.cdu.vu.event.notification.telegram.bot.component.command.impl.event;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.command.BotCommand;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.command.Command;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.command.parser.BotCommandParser;
import ua.edu.cdu.vu.event.notification.telegram.bot.exception.EventNotFoundException;
import ua.edu.cdu.vu.event.notification.telegram.bot.service.EventNotificationService;

@Command(BotCommand.DELETE)
@RequiredArgsConstructor
public class DeleteEventCommand implements BotCommand {

    private static final String EVENT_DELETED = "Event with id: '%d' deleted";

    private final BotCommandParser botCommandParser;
    private final EventNotificationService eventNotificationService;

    @Override
    public BotCommand.Result execute(Update update) {
        if (update.hasCallbackQuery()) {
            long eventId = botCommandParser.parseLongArgument(update.getCallbackQuery().getData());
            String response;
            try {
                eventNotificationService.deleteEvent(eventId);
                response = EVENT_DELETED.formatted(eventId);
            } catch (EventNotFoundException e) {
                response = e.getMessage();
            }

            return BotCommand.Result.builder()
                    .response(response)
                    .build();
        }

        return BotCommand.Result.empty();
    }
}
