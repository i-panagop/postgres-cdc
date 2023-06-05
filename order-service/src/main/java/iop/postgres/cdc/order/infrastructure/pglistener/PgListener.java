package iop.postgres.cdc.order.infrastructure.pglistener;

import iop.postgres.cdc.order.connector.rabbitmq.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.PollerFactory;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import javax.sql.DataSource;


@Configuration
public class PgListener {

    @Bean
    JdbcPollingChannelAdapter inbound(PgRowMapper customerRowMapper, DataSource dataSource) {
        var jdbc = new JdbcPollingChannelAdapter(dataSource,
            """
                SELECT * FROM pg_logical_slot_get_changes(
                'replication_slot',
                NULL,
                NULL
                )
                """
        );
        jdbc.setRowMapper(customerRowMapper);
        return jdbc;
    }

    @Bean
    IntegrationFlow jdbcInboundFlow(JdbcPollingChannelAdapter inbound, PgFilter filter, MessageHandler outbound) {
        return IntegrationFlow
            .from(inbound, poller -> poller.poller(pm -> PollerFactory.fixedRate(Duration.of(5, ChronoUnit.SECONDS))))
            .handle(filter)
            .split()
            .handle(outbound)
            .get();
    }
}
