package ua.edu.cdu.vu.event.notification.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.UserState;

import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class FlowService {

    private final UserStateService userStateService;

    public void start(int id, long userId) {
        start(id, userId, UnaryOperator.identity());
    }

    public void start(int id, long userId, UnaryOperator<UserState> userStateCustomizer) {
        UserState userState = userStateCustomizer.apply(UserState.initial(userId, id));
        userStateService.save(userState);
    }
}
