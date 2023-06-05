package iop.postgres.cdc.order.business.event;

public enum EventType {

    INSERT("insert"),

    UPDATE("update"),
    UNHANDLED("unhandled");

    private final String value;

    EventType(String value) {
        this.value = value;
    }

    public static EventType of(String type) {
        for (EventType eventType : EventType.values()) {
            if (eventType.value.equalsIgnoreCase(type)) {
                return eventType;
            }
        }
        return null;
    }

}
