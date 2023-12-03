package ua.edu.cdu.vu.event.notification.telegram.bot.component.command.impl.event;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.command.BotCommand;
import ua.edu.cdu.vu.event.notification.telegram.bot.component.command.Command;
import ua.edu.cdu.vu.event.notification.telegram.bot.service.FlowService;

import static ua.edu.cdu.vu.event.notification.telegram.bot.component.step.StepConfiguration.CREATE_EVENT_FLOW_ID;

@Command(BotCommand.CREATE)
@RequiredArgsConstructor
public class CreateEventCommand implements BotCommand {

    private static final String ENTER_EVENT_NAME = "Enter event name, please:";

    private final FlowService flowService;

    @Override
    public BotCommand.Result execute(Update update) {
        Long userId = getUserId(update);

        flowService.start(CREATE_EVENT_FLOW_ID, userId);

        return BotCommand.Result.builder()
                .response(ENTER_EVENT_NAME)
                .build();
    }
}
