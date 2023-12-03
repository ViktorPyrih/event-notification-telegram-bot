package ua.edu.cdu.vu.event.notification.telegram.bot.mapper;

import org.mapstruct.Mapper;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.Notification;
import ua.edu.cdu.vu.event.notification.telegram.bot.model.NotificationRequest;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface NotificationMapper {

    Notification convertToDomain(NotificationRequest request);
}
