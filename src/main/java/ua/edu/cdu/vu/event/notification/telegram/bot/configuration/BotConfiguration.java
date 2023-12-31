package ua.edu.cdu.vu.event.notification.telegram.bot.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "bot.configuration")
public class BotConfiguration {

    private String token;
    private String username;

}
