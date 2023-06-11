package iop.postgres.cdc.catalog.business.command;

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
    private static final long serialVersionUID = -2997463373457579502L;

    private String error;
    private String domain;
    private String className;

    public Command(String domain, String className) {
        this.domain = domain;
        this.className = className;
    }
}
