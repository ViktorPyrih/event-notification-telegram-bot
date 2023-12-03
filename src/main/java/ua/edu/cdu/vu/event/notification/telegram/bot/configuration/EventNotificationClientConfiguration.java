package ua.edu.cdu.vu.event.notification.telegram.bot.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ua.edu.cdu.vu.event.notification.telegram.bot.client.EventNotificationApiClient;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "event.notification.api")
public class EventNotificationClientConfiguration {

    private String url;
    private String clientId;
    private String clientSecret;

    @Bean
    public EventNotificationApiClient eventNotificationApiClient(WebClient eventNotificationApiWebClient) {
        return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(eventNotificationApiWebClient))
                .build().createClient(EventNotificationApiClient.class);
    }

}
