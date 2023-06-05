package iop.postgres.cdc.payment.business.event;

import iop.postgres.cdc.payment.business.event.order.OrderCreationEvent;
import iop.postgres.cdc.payment.business.event.payment.PaymentCreationEvent;
import lombok.Getter;

public enum EventTopic {

    ORDER_CREATION("order-creation-topic", "order-routing-key", OrderCreationEvent.class),
    PAYMENT_CREATION("order-topic", "order-routing-key", PaymentCreationEvent.class);

    @Getter
    private final String topic;
    @Getter
    private final String routingKey;
    private final Class<?> eventClass;

    EventTopic(String topic, String routingKey, Class<?> eventClass) {
        this.topic = topic;
        this.routingKey = routingKey;
        this.eventClass = eventClass;
    }

    public static EventTopic forClass(Class<?> eventClass) {
        for (EventTopic eventTopic : EventTopic.values()) {
            if (eventTopic.eventClass.equals(eventClass)) {
                return eventTopic;
            }
        }
        return null;
    }
}
