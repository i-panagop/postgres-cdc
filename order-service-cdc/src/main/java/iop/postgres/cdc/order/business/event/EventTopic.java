package iop.postgres.cdc.order.business.event;

import iop.postgres.cdc.order.business.event.commerceitem.CommerceItemCreationEvent;
import iop.postgres.cdc.order.business.event.order.OrderCreationEvent;
import lombok.Getter;

public enum EventTopic {

    ORDER_CREATION("order-creation-topic", "order-routing-key", OrderCreationEvent.class),

    COMMERCE_ITEM_CREATION("commerce-item-creation-topic", "commerce-item-routing-key",
        CommerceItemCreationEvent.class);

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
