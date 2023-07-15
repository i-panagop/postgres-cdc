package iop.postgres.cdc.orchestrator.business;

import iop.postgres.cdc.orchestrator.business.commerceitem.CommerceItem;
import iop.postgres.cdc.orchestrator.business.order.CreateOrderCommand;
import iop.postgres.cdc.orchestrator.connector.rabbitmq.OutboundMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestHandler {

    private final OutboundMessageHandler outboundMessageHandler;

    public Boolean createOrder(String userEmail, double amount, List<CommerceItem> commerceItems) {
        outboundMessageHandler.send(new CreateOrderCommand(userEmail, amount, commerceItems));
        return true;
    }
}
