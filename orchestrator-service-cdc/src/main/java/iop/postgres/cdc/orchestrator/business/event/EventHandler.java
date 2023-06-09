package iop.postgres.cdc.orchestrator.business.event;

import iop.postgres.cdc.orchestrator.business.command.Command;
import iop.postgres.cdc.orchestrator.business.command.CommandFactory;
import iop.postgres.cdc.orchestrator.connector.rabbitmq.OutboundMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventHandler {

    private final EventFactory eventFactory;
    private final CommandFactory commandFactory;
    private final OutboundMessageHandler outboundMessageHandler;

    public void handle(PgMessageBody messageBody) {
        List<Event> events = toEvents(messageBody);
        if (Objects.isNull(events) || events.isEmpty()) {
            return;
        }
        outboundMessageHandler.send(toCommands(events));
    }

    public List<Event> toEvents(PgMessageBody messageBody) {
        if (Objects.isNull(messageBody) || Objects.isNull(messageBody.data())) {
            return new ArrayList<>();
        }
        return eventFactory.generateEvents(messageBody.data().get("change"));
    }

    public List<Command> toCommands(List<Event> events) {
        List<Command> commands = new ArrayList<>();
        for (Event event : events) {
            commands.addAll(commandFactory.createCommands(event));
        }
        return commands;
    }

}
