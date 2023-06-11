package iop.postgres.cdc.catalog.business.event;

import com.fasterxml.jackson.databind.JsonNode;

public record PgMessageBody(Long transactionId, String lsn, JsonNode data) { }