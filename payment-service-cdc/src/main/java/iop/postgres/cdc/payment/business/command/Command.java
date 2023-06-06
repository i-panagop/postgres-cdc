package iop.postgres.cdc.payment.business.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Command implements Serializable {

    @Serial
    private static final long serialVersionUID = -5748727367377973020L;

    private String error;
}
