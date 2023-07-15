package iop.postgres.cdc.orchestrator.business.command;

import iop.postgres.cdc.orchestrator.business.order.CreateOrderCommand;
import iop.postgres.cdc.orchestrator.business.order.UpdateOrderCommand;
import iop.postgres.cdc.orchestrator.business.payment.CreatePaymentCommand;
import iop.postgres.cdc.orchestrator.business.shipping.CreateShippingCommand;
import lombok.Getter;

public enum CommandTopic {

    CREATE_ORDER("order-topic", "create-order-rk", CreateOrderCommand.class),
    UPDATE_ORDER("order-topic", "update-order-rk", UpdateOrderCommand.class),
    CREATE_SHIPPING("shipping-topic", "create-shipping-rk", CreateShippingCommand.class),
    CREATE_PAYMENT("payment-topic", "create-payment-rk", CreatePaymentCommand.class);

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
