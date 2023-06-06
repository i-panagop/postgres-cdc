package iop.postgres.cdc.commerceitem.business.command;

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
    private static final long serialVersionUID = 459408094074344037L;

    private String error;
}
