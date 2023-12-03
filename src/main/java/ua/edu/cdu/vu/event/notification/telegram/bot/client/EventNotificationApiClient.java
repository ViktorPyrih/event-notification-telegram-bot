package ua.edu.cdu.vu.event.notification.telegram.bot.client;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.*;
import ua.edu.cdu.vu.event.notification.telegram.bot.client.request.EventRequest;
import ua.edu.cdu.vu.event.notification.telegram.bot.client.response.EventResponse;

import java.util.List;

@HttpExchange("/events")
public interface EventNotificationApiClient {

    @PostExchange
    EventResponse createEvent(@RequestBody EventRequest eventRequest);

    @PutExchange("/{id}")
    void updateEvent(@PathVariable long id, @RequestBody EventRequest eventRequest);

    @DeleteExchange("/{id}")
    void deleteEvent(@PathVariable long id);

    @GetExchange
    List<EventResponse> getAll(@RequestParam String key);

    @GetExchange("/{id}")
    EventResponse getById(@PathVariable long id);
}
