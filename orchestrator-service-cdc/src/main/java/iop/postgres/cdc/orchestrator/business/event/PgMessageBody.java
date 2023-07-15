package iop.postgres.cdc.orchestrator.business.event;

import com.fasterxml.jackson.databind.JsonNode;

public record PgMessageBody(Long transactionId, String lsn, JsonNode data) { }