package iop.postgres.cdc.shipping.business.command;

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
    private static final long serialVersionUID = 1973665668549058555L;

    private String error;
    private String domain;
    private String className;

    public Command(String domain, String className) {
        this.domain = domain;
        this.className = className;
    }
}
