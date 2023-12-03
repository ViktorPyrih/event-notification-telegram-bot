package ua.edu.cdu.vu.event.notification.telegram.bot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.edu.cdu.vu.event.notification.telegram.bot.client.request.EventReminder;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.Reminder;
import ua.edu.cdu.vu.event.notification.telegram.bot.model.TimeLabel;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ReminderMapper {

    @Mapping(target = "unitsBeforeStart", source = "amount")
    Reminder convertToDomain(TimeLabel timeLabel);

    EventReminder convertToDto(Reminder reminder);
}
