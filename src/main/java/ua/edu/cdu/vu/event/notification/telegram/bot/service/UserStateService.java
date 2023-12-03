package ua.edu.cdu.vu.event.notification.telegram.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.cdu.vu.event.notification.telegram.bot.domain.UserState;
import ua.edu.cdu.vu.event.notification.telegram.bot.mapper.UserStateMapper;
import ua.edu.cdu.vu.event.notification.telegram.bot.redis.hash.UserStateHash;
import ua.edu.cdu.vu.event.notification.telegram.bot.redis.repository.UserStateRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserStateService {

    private final UserStateRepository userStateRepository;
    private final UserStateMapper userStateMapper;

    public Optional<UserState> findUserState(long userId) {
        return userStateRepository.findById(userId)
                .map(userStateMapper::convertToDomain);
    }

    public void save(UserState userState) {
        UserStateHash userStateHash = userStateMapper.convertToHash(userState);
        userStateRepository.save(userStateHash);
    }

    public void delete(UserState userState) {
        userStateRepository.deleteById(userState.getUserId());
    }
}
