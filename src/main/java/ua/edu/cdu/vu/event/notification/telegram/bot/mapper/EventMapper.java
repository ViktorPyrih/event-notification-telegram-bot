package ua.edu.cdu.vu.event.notification.telegram.bot.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.cdu.vu.event.notification.telegram.bot.client.request.EventRequest;
import ua.edu.cdu.vu.event.notification.telegram.bot.client.response.EventResponse;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.Event;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.UserState;
import ua.edu.cdu.vu.event.notification.telegram.bot.util.DateTimeUtils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static ua.edu.cdu.vu.event.notification.telegram.bot.util.EventConstants.*;

@Mapper(componentModel = SPRING, uses = ReminderMapper.class)
public abstract class EventMapper {

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    public Event convertToDomain(UserState userState, long chatId) {
        return Event.builder()
                .id(userState.findDataEntry(EVENT_ID)
                        .map(Long::parseLong)
                        .orElse(null))
                .key(String.valueOf(chatId))
                .name(userState.getDataEntry(EVENT_NAME))
                .notes(userState.getDataEntry(EVENT_NOTES, null))
                .dateTime(ZonedDateTime.of(DateTimeUtils.parse(userState.getDataEntry(EVENT_DATETIME)), ZoneOffset.UTC))
                .reminders(objectMapper.readValue(userState.getDataEntry(EVENT_REMINDERS), new TypeReference<>() {}))
                .build();
    }

    public abstract List<Event> convertToDomain(List<EventResponse> eventResponses);

    public abstract Event convertToDomain(EventResponse eventResponse);

    public abstract EventRequest convertToDto(Event event);
}
