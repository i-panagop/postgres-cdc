package iop.postgres.cdc.shipping.business.exception;

import java.io.Serial;
import java.util.UUID;

public class UserNotFoundException extends Throwable {

    @Serial
    private static final long serialVersionUID = -2119342659480477379L;

    private static final String MESSAGE = "User with id %s not found";

    public UserNotFoundException(UUID userId) {
        super(String.format(MESSAGE, userId));
    }
}
