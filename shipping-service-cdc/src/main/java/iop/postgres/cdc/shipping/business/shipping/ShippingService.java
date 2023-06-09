package iop.postgres.cdc.shipping.business.shipping;

import iop.postgres.cdc.shipping.business.command.CreateShippingCommand;
import iop.postgres.cdc.shipping.business.exception.UserNotFoundException;
import iop.postgres.cdc.shipping.connector.user.User;
import iop.postgres.cdc.shipping.connector.user.UserServiceClient;
import iop.postgres.cdc.shipping.infrastructure.shipping.ShippingEntity;
import iop.postgres.cdc.shipping.infrastructure.shipping.ShippingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingService {

    private final ShippingRepository shippingRepository;
    private final UserServiceClient userServiceClient;

    public void handleCreateShippingCommand(CreateShippingCommand createShippingCommand) throws UserNotFoundException {
        log.info("Creating shipping for order {}", createShippingCommand.getOrderId());
        User user = userServiceClient.getById(createShippingCommand.getUserId());
        if (Objects.isNull(user)) {
            throw new UserNotFoundException(createShippingCommand.getUserId());
        }
        shippingRepository.save(ShippingEntity.of(createShippingCommand, user));
    }
}
