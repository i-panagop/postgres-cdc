package iop.postgres.cdc.orchestrator.business.command;

import iop.postgres.cdc.orchestrator.business.commerceitem.CommerceItem;
import iop.postgres.cdc.orchestrator.business.commerceitem.CommerceItemEvent;
import iop.postgres.cdc.orchestrator.business.event.Event;
import iop.postgres.cdc.orchestrator.business.event.EventType;
import iop.postgres.cdc.orchestrator.business.order.OrderEvent;
import iop.postgres.cdc.orchestrator.business.order.UpdateOrderCommand;
import iop.postgres.cdc.orchestrator.business.payment.CreatePaymentCommand;
import iop.postgres.cdc.orchestrator.business.payment.PaymentEvent;
import iop.postgres.cdc.orchestrator.business.shipping.CreateShippingCommand;
import iop.postgres.cdc.orchestrator.business.shipping.ShippingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class CommandFactory {

    public List<Command> createCommands(Event event) {
        if (event instanceof OrderEvent orderEvent) {
            return handleOrderEvent(orderEvent);
        } else if (event instanceof CommerceItemEvent commerceItemEvent) {
            return handleCommerceItemEvent(commerceItemEvent);
        } else if (event instanceof PaymentEvent paymentEvent) {
            return handlePaymentEvent(paymentEvent);
        } else if (event instanceof ShippingEvent shippingEvent) {
            return handleShippingEvent(shippingEvent);
        }
        return handleCommandNotFound(event);
    }

    private List<Command> handleShippingEvent(ShippingEvent shippingEvent) {
        if (EventType.INSERT.equals(shippingEvent.getType())) {
            return List.of(
                new UpdateOrderCommand(
                    shippingEvent.getOrderId(),
                    null,
                    shippingEvent.getId()
                )
            );
        }
        return handleCommandNotFound(shippingEvent);
    }

    private List<Command> handlePaymentEvent(PaymentEvent event) {
        if (EventType.INSERT.equals(event.getType())) {
            return List.of(
                new UpdateOrderCommand(
                    event.getOrderId(),
                    event.getId(),
                    null
                )
            );
        }
        return handleCommandNotFound(event);
    }

    private List<Command> handleOrderEvent(OrderEvent orderEvent){
        if (EventType.INSERT.equals(orderEvent.getType())) {
            List<Command> commands = new ArrayList<>();
            commands.add(new CreatePaymentCommand(orderEvent.getId(), orderEvent.getAmount(), null));
            commands.add(new CreateShippingCommand(orderEvent.getId(), orderEvent.getUserId()));
            return commands;
        } else if (EventType.UPDATE.equals(orderEvent.getType())) {
            //TODO: handle update order event
        }
        return handleCommandNotFound(orderEvent);
    }

    private List<Command> handleCommerceItemEvent(CommerceItemEvent commerceItemEvent) {
        if (EventType.INSERT.equals(commerceItemEvent.getType())) {
            return List.of(
                new CreatePaymentCommand(
                    commerceItemEvent.getOrderId(),
                    0.0d,
                    Set.of(
                        CommerceItem.from(commerceItemEvent)
                    )
                )
            );
        }
        return handleCommandNotFound(commerceItemEvent);
    }

    private List<Command> handleCommandNotFound(Event event) {
        log.info("Command not found for event: {}", event);
        return null;
    }

}
