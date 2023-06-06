package iop.postgres.cdc.commerceitem.business.event;

import lombok.Getter;

public enum EventTopic {

    COMMERCE_ITEM_CREATION("order-topic", "order-routing-key", CommerceItemCreationEvent.class);

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
