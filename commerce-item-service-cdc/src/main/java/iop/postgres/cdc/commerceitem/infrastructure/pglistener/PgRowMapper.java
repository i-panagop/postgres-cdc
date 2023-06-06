package iop.postgres.cdc.commerceitem.infrastructure.pglistener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PgRowMapper implements RowMapper<PgRowMapper.PgSlotChange> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public PgSlotChange mapRow(ResultSet rs, int rowNum) throws SQLException {
        PgSlotChange pgSlotChange;
        try {
            pgSlotChange = new PgSlotChange(
                rs.getLong("xid"),
                rs.getString("lsn"),
                mapper.readTree(rs.getString("data"))
            );
        } catch (JsonProcessingException e) {
            return null;
        }
        return pgSlotChange;
    }

    public record PgSlotChange(Long transactionId, String lsn, JsonNode data) {

    }
}
