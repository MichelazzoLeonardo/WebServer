package configuration.util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;

/**
 * The Json class provides methods for parsing, serializing, and deserializing JSON data using the Jackson library.
 */
public class Json {


    private static ObjectMapper myObjectMapper = defaultObjectMapper();

    /**
     * Returns a default ObjectMapper with configured settings.
     *
     * @return the default ObjectMapper instance
     */
    private static ObjectMapper defaultObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return om;
    }

    public static JsonNode parse(String jsonSrc) throws IOException {
        return myObjectMapper.readTree(jsonSrc);
    }

    /**
     * Converts a JsonNode object to an instance of the specified class using the default ObjectMapper.
     *
     * @param node  The JsonNode object representing the JSON data.
     * @param clazz The Class object representing the target class for deserialization.
     * @param <A>   The type of the target class.
     * @return An instance of the target class.
     * @throws JsonProcessingException If there's an error during the deserialization process.
     */
    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return myObjectMapper.treeToValue(node, clazz);
    }


    public static JsonNode toJson(Object obj) {
        return myObjectMapper.valueToTree(obj);
    }


    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }

    public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node, true);
    }


    /**
     * Generates a JSON string representation of an object.
     *
     * @param o      The object to be serialized to JSON.
     * @param pretty Flag indicating whether the output JSON should be formatted with indentation and line breaks.
     * @return The JSON string representation of the object.
     * @throws JsonProcessingException if there is an error during the serialization process.
     */
    private static String generateJson(Object o, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = myObjectMapper.writer();
        if (pretty)
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        return objectWriter.writeValueAsString(o);
    }
}

