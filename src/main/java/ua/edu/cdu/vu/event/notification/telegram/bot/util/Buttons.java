package ua.edu.cdu.vu.event.notification.telegram.bot.util;

import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ua.edu.cdu.vu.event.notification.telegram.bot.model.TimeLabel;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.join;
import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static ua.edu.cdu.vu.event.notification.telegram.bot.component.command.BotCommand.DELETE;
import static ua.edu.cdu.vu.event.notification.telegram.bot.component.command.BotCommand.UPDATE;

@UtilityClass
public class Buttons {

    public static final String COMPLETE = "COMPLETE";

    public static KeyboardButton button(String text) {
        return KeyboardButton.builder()
                .text(text)
                .build();
    }

    public static InlineKeyboardButton button(String text, String data) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(data)
                .build();
    }

    public static InlineKeyboardButton delete(String data) {
        return button("Delete X", data);
    }

    public static InlineKeyboardButton update(String data) {
        return button("Edit ✎", data);
    }

    public static InlineKeyboardMarkup eventKeyboard(long eventId) {
        String eventIdText = valueOf(eventId);
        return keyboard(Buttons.update(join(SPACE, UPDATE, eventIdText)), Buttons.delete(join(SPACE, DELETE, eventIdText)));
    }

    public static ReplyKeyboardMarkup remindersKeyboard() {
        var rows = Lists.partition(List.of(TimeLabel.values()), 5)
                .stream()
                .map(timeLabels -> timeLabels.stream()
                        .map(TimeLabel::getName)
                        .map(Buttons::button)
                        .toList())
                .map(KeyboardRow::new)
                .collect(Collectors.toList());
        rows.add(new KeyboardRow(List.of(button(COMPLETE))));

        return keyboard(rows);
    }

    private InlineKeyboardMarkup keyboard(InlineKeyboardButton... buttons) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(buttons))
                .build();
    }

    private static ReplyKeyboardMarkup keyboard(List<KeyboardRow> rows) {
        return ReplyKeyboardMarkup.builder()
                .isPersistent(true)
                .resizeKeyboard(true)
                .keyboard(rows)
                .build();
    }
}
