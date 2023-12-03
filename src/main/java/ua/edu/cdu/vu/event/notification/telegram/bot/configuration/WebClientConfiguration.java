package ua.edu.cdu.vu.event.notification.telegram.bot.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {

    private static final String BASIC = "Basic ";
    private static final String BASIC_TOKEN = "%s:%s";

    private final EventNotificationClientConfiguration eventNotificationClientConfiguration;

    @Bean
    public WebClient eventNotificationApiWebClient() {
        return WebClient.builder()
                .baseUrl(eventNotificationClientConfiguration.getUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, getClientToken(eventNotificationClientConfiguration.getClientId(), eventNotificationClientConfiguration.getClientSecret()))
                .build();
    }

    private String getClientToken(String clientId, String clientSecret) {
        return BASIC + Base64.encodeBase64String(BASIC_TOKEN.formatted(clientId, clientSecret).getBytes(StandardCharsets.UTF_8));
    }
}
