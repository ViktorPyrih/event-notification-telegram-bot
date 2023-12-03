package ua.edu.cdu.vu.event.notification.telegram.bot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.Notification;
import ua.edu.cdu.vu.event.notification.telegram.bot.mapper.NotificationMapper;
import ua.edu.cdu.vu.event.notification.telegram.bot.model.NotificationRequest;
import ua.edu.cdu.vu.event.notification.telegram.bot.service.NotificationService;

@Slf4j
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationMapper notificationMapper;
    private final NotificationService notificationService;

    @PostMapping
    public void processNotification(@RequestBody NotificationRequest notificationRequest) {
        log.info("Notification received: {}", notificationRequest);
        Notification notification = notificationMapper.convertToDomain(notificationRequest);
        notificationService.processNotification(notification);
    }
}
