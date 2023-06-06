package iop.postgres.cdc.commerceitem.infrastructure.pglistener.json;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class NodeReader {

    /**
     * Method to traverse a given JsonNode and return the data of the last specified node. If any inner node or the last
     * leaf node is missing the method will return null.
     *
     * @param fieldNames           list of node names to traverse
     * @param processingEventsNode current JsonNode
     * @param convertFunction      JsonNode return function which will be applied
     */
    public static <T> T getValueNullSafe(
        List<String> fieldNames, JsonNode processingEventsNode, Function<JsonNode, T> convertFunction) {
        JsonNode currentNode = processingEventsNode;
        for (var fieldName : fieldNames) {
            currentNode = checkNodeForNull(currentNode, fieldName);
        }
        return Optional.of(currentNode)
            .map(convertFunction)
            .orElse(null);
    }

    /**
     * Method to fetch the next node. The method will return null if:
     * <ul>
     *     <li>Current node is null</li>
     *     <li>The next node doesn't exist or is a NullNode</li>
     * </ul>
     *
     * @param jsonNode  current JsonNode
     * @param fieldName node to return
     * @return JsonNode
     */
    private static JsonNode checkNodeForNull(JsonNode jsonNode, String fieldName) {
        return Optional.ofNullable(jsonNode)
            .map(jsonNodeNotNull -> jsonNodeNotNull.get(fieldName))
            .filter(jsonNodeNotNull -> !jsonNodeNotNull.isNull())
            .orElse(null);
    }

}
