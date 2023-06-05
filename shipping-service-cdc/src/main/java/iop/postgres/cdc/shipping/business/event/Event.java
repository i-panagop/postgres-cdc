package iop.postgres.cdc.shipping.business.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Serializable {

    @Serial
    private static final long serialVersionUID = -4054672029742705525L;

    private EventType type;
    private String error;

    public Event(EventType type) {
        this.type = type;
    }

}
