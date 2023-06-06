package iop.postgres.cdc.commerceitem.connector.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import iop.postgres.cdc.commerceitem.business.command.CommerceItemCreateCommand;
import iop.postgres.cdc.commerceitem.business.commerceItem.CommerceItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class InboundMessageHandler {

    private final ObjectMapper objectMapper;
    private final CommerceItemService commerceItemService;

    @RabbitListener(queues = "CommerceItemQueue")
    public void receiveMessage(Message message, Channel channel) throws IOException {
        try {
            CommerceItemCreateCommand commerceItemCreateCommand =
                objectMapper.readValue(message.getBody(), CommerceItemCreateCommand.class);
            commerceItemService.createCommerceItems(commerceItemCreateCommand);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception e) {
            log.info("Error while processing message", e);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}