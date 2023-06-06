package iop.postgres.cdc.commerceitem.business.command;

import lombok.Getter;

public enum CommandTopic {

    CREATE_COMMERCE_ITEM("commerce-item-creation-topic", "commerce-item-routing-key",
        CommerceItemCreateCommand.class);

    @Getter
    private final String topic;
    @Getter
    private final String routingKey;
    private final Class<?> eventClass;

    CommandTopic(String topic, String routingKey, Class<?> eventClass) {
        this.topic = topic;
        this.routingKey = routingKey;
        this.eventClass = eventClass;
    }

    public static CommandTopic forClass(Class<?> eventClass) {
        for (CommandTopic eventTopic : CommandTopic.values()) {
            if (eventTopic.eventClass.equals(eventClass)) {
                return eventTopic;
            }
        }
        return null;
    }
}
