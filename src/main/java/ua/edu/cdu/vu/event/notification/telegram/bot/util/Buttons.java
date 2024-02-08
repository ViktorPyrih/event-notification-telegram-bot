package ua.edu.cdu.vu.event.notification.telegram.bot.util;

import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ua.edu.cdu.vu.event.notification.telegram.bot.model.TimeLabel;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.join;
import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static ua.edu.cdu.vu.event.notification.telegram.bot.component.command.BotCommand.DELETE;
import static ua.edu.cdu.vu.event.notification.telegram.bot.component.command.BotCommand.UPDATE;
import static ua.edu.cdu.vu.event.notification.telegram.bot.util.DateTimeUtils.format;

@UtilityClass
public class Buttons {

    public static final String COMPLETE = "COMPLETE";

    public static KeyboardButton button(String text) {
        return KeyboardButton.builder()
                .text(text)
                .build();
    }

    public static KeyboardButton button(LocalDateTime localDateTime) {
        return button(format(localDateTime));
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

        return keyboard(rows, true);
    }

    public static ReplyKeyboardMarkup dateTimeKeyboard(ZonedDateTime dateTime) {
        LocalDateTime localDateTime = dateTime.toLocalDateTime();
        LocalDateTime localDateTimeAtStartOfDay = dateTime.toLocalDate().atStartOfDay();
        return keyboard(List.of(
                new KeyboardRow(List.of(button(localDateTime.plusMinutes(5)), button(localDateTime.plusMinutes(10)))),
                new KeyboardRow(List.of(button(localDateTime.plusMinutes(15)), button(localDateTime.plusMinutes(30)))),
                new KeyboardRow(List.of(button(localDateTime.plusMinutes(45)), button(localDateTime.plusHours(1)))),
                new KeyboardRow(List.of(button(localDateTime.plusHours(2)), button(localDateTime.plusHours(3)))),
                new KeyboardRow(List.of(button(localDateTime.plusHours(6)), button(localDateTime.plusHours(9)))),
                new KeyboardRow(List.of(button(localDateTime.plusHours(12)), button(localDateTime.plusHours(18)))),
                new KeyboardRow(List.of(button(localDateTimeAtStartOfDay.plusDays(1)), button(localDateTimeAtStartOfDay.plusDays(1).plusHours(3)))),
                new KeyboardRow(List.of(button(localDateTimeAtStartOfDay.plusDays(1).plusHours(6)), button(localDateTimeAtStartOfDay.plusDays(1).plusHours(9)))),
                new KeyboardRow(List.of(button(localDateTimeAtStartOfDay.plusDays(1).plusHours(12)), button(localDateTimeAtStartOfDay.plusDays(1).plusHours(15)))),
                new KeyboardRow(List.of(button(localDateTimeAtStartOfDay.plusDays(1).plusHours(18)), button(localDateTimeAtStartOfDay.plusDays(1).plusHours(21)))),
                new KeyboardRow(List.of(button(localDateTimeAtStartOfDay.plusDays(2)), button(localDateTimeAtStartOfDay.plusDays(2).plusHours(6)))),
                new KeyboardRow(List.of(button(localDateTimeAtStartOfDay.plusDays(2).plusHours(12)), button(localDateTimeAtStartOfDay.plusDays(2).plusHours(18)))),
                new KeyboardRow(List.of(button(localDateTimeAtStartOfDay.plusDays(3)), button(localDateTimeAtStartOfDay.plusDays(3).plusHours(12)))),
                new KeyboardRow(List.of(button(localDateTimeAtStartOfDay.plusDays(4)), button(localDateTimeAtStartOfDay.plusDays(5))))
        ), false);
    }

    private InlineKeyboardMarkup keyboard(InlineKeyboardButton... buttons) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(buttons))
                .build();
    }

    private static ReplyKeyboardMarkup keyboard(List<KeyboardRow> rows, boolean isPersistent) {
        return ReplyKeyboardMarkup.builder()
                .isPersistent(isPersistent)
                .resizeKeyboard(true)
                .keyboard(rows)
                .build();
    }
}
