package ua.edu.cdu.vu.event.notification.telegram.bot.component.command.impl.event;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.command.BotCommand;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.command.Command;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.Event;
import ua.edu.cdu.vu.event.notification.telegram.bot.service.EventNotificationService;
import ua.edu.cdu.vu.event.notification.telegram.bot.service.TelegramSenderService;

import java.util.List;

import static ua.edu.cdu.vu.event.notification.telegram.bot.util.Buttons.eventKeyboard;

@Command(BotCommand.GET_ALL)
@RequiredArgsConstructor
public class GetAllEventsCommand implements BotCommand {

    private final EventNotificationService eventNotificationService;
    private final TelegramSenderService telegramSenderService;

    @Override
    public BotCommand.Result execute(Update update) throws TelegramApiException {
        if (!update.hasCallbackQuery()) {
            long chatId = update.getMessage().getChatId();
            List<Event> events = eventNotificationService.getAllEvents(chatId);
            for (var event: events) {
                InlineKeyboardMarkup keyboard = eventKeyboard(event.getId());
                telegramSenderService.send(chatId, event.toString(), keyboard);
            }
        }

        return BotCommand.Result.empty();
    }
}
