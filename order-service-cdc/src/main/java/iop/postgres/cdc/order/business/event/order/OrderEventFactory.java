package iop.postgres.cdc.order.business.event.order;

import iop.postgres.cdc.order.business.event.Event;
import iop.postgres.cdc.order.business.event.EventType;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventFactory {

    public Event createOrderEvent(@Nonnull EventType eventType) {
        switch (eventType) {
            case INSERT -> {
                return new OrderCreationEvent(eventType);
            }
            case UPDATE -> {
                return new OrderUpdateEvent(eventType);
            }
            default -> {
                log.info("Event type not handled: {}", eventType);
                return null;
            }
        }
    }

}
