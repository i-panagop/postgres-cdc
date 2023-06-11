package iop.postgres.cdc.catalog.business.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
@ConfigurationProperties(prefix = "domain-tables")
public class DomainTableProperties {

    @NotEmpty
    private String order;
    @NotEmpty
    private String commerceItem;
    @NotEmpty
    private String payment;
    @NotEmpty
    private String shipping;

}
