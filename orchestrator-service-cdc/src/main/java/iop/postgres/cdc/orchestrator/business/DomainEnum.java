package iop.postgres.cdc.orchestrator.business;

import lombok.Getter;

public enum DomainEnum {

    ORDER("order"), COMMERCE_ITEM("commerce_item"), PAYMENT("payment"), SHIPPING("shipping");

    @Getter
    private final String name;

    DomainEnum(String name) {
        this.name = name;
    }
}
