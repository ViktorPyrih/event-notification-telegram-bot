package ua.edu.cdu.vu.event.notification.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ua.edu.cdu.vu.event.notification.telegram.bot.client.EventNotificationApiClient;
import ua.edu.cdu.vu.event.notification.telegram.bot.client.exception.EventNotFoundException;
import ua.edu.cdu.vu.event.notification.telegram.bot.client.exception.EventNotValidException;
import ua.edu.cdu.vu.event.notification.telegram.bot.client.request.EventRequest;
import ua.edu.cdu.vu.event.notification.telegram.bot.client.response.ErrorResponse;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.Event;
import ua.edu.cdu.vu.event.notification.telegram.bot.mapper.EventMapper;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class EventNotificationService {

    private final EventNotificationApiClient eventNotificationApiClient;
    private final EventMapper eventMapper;

    public long upsertEvent(Event event) {
        try {
            EventRequest eventRequest = eventMapper.convertToDto(event);
            if (isNull(event.getId())) {
                return eventNotificationApiClient.createEvent(eventRequest).id();
            }
            eventNotificationApiClient.updateEvent(event.getId(), eventRequest);

            return event.getId();
        } catch (WebClientResponseException.BadRequest e) {
            String reason = requireNonNull(e.getResponseBodyAs(ErrorResponse.class)).detail();
            throw new EventNotValidException(reason);
        } catch (WebClientResponseException.NotFound e) {
            throw new EventNotFoundException(event.getId());
        }
    }

    public void deleteEvent(long id) {
        try {
            eventNotificationApiClient.deleteEvent(id);
        } catch (WebClientResponseException.NotFound e) {
            throw new EventNotFoundException(id);
        }
    }

    public List<Event> getAllEvents(long chatId) {
        var events = eventNotificationApiClient.getAll(String.valueOf(chatId));
        return eventMapper.convertToDomain(events);
    }

    public Event getEventById(long id) {
        return eventMapper.convertToDomain(eventNotificationApiClient.getById(id));
    }
}
