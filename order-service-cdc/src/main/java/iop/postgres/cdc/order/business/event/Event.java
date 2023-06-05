package iop.postgres.cdc.order.business.event;

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
    private static final long serialVersionUID = -5514625762074995173L;

    private EventType type;
    private String error;

    public Event(EventType type) {
        this.type = type;
    }
}
