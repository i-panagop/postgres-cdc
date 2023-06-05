package iop.postgres.cdc.order.business.event;

import lombok.Getter;

public enum EventTopic {

    ORDER("order-topic", "order-routing-key", OrderEvent.class);

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
