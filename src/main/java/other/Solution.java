package other;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Random;

public class Solution {
    static final boolean isDebugging = false;
    private static int firstToSecondCount = 0;
    private static int secondToFistCount = 0;
    private static int firstToSecondSuccesses = 0;
    private static int secondToFistSuccesses = 0;
    private static int complexSuccesses = 0;


    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < Integer.MAX_VALUE / 100000; i++) {
            if (random.nextBoolean() == true) tryConvertFirstToSecond();
            else tryConvertSecondToFirst();
        }
        printSummary();
    }

    private static Object convertOneToAnother(Object one, Class resultClassObject) throws IOException {
        Random random = new Random();
        if (random.nextBoolean() == true) return convertOneToAnotherSimple(one, resultClassObject);
        else return convertOneToAnotherComplex(one, resultClassObject);
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "className")
    @JsonSubTypes(@JsonSubTypes.Type(value = First.class, name = "first"))
    public static class First {
        public int i;
        public String name;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "className")
    @JsonSubTypes(@JsonSubTypes.Type(value = Second.class, name = "second"))
    public static class Second {
        public int i;
        public String name;
    }

    private static void tryConvertFirstToSecond() {
        firstToSecondCount++;
        try {
            Second s = (Second) convertOneToAnother(new First(), Second.class);
            firstToSecondSuccesses++;
            System.out.println("First -> Second Succeeded");
        } catch (Exception e) {
            System.err.println(new StringBuilder().append("First -> Second Failed - Exception: ").append(e.toString()));
            if (isDebugging) e.printStackTrace();
        }
    }

    private static void tryConvertSecondToFirst() {
        secondToFistCount++;
        try {
            First f = (First) convertOneToAnother(new Second(), First.class);
            secondToFistSuccesses++;
            System.out.println("Second -> First Succeeded");
        } catch (Exception e) {
            System.err.println(new StringBuilder().append("Second -> First Failed - Exception: ").append(e.toString()));
            if (isDebugging) e.printStackTrace();
        }
    }

    private static Object convertOneToAnotherSimple(Object one, Class resultClassObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(MapperFeature.USE_ANNOTATIONS);
        String jsonString = objectMapper.writeValueAsString(one);
        return objectMapper.readValue(jsonString, resultClassObject);
    }

    private static Object convertOneToAnotherComplex(Object one, Class resultClassObject) throws JsonProcessingException {
        String jsonString = customSerialize(one, resultClassObject);
        Object object = customDeserialize(jsonString, resultClassObject);
        complexSuccesses++;
        return object;
    }

    private static String customSerialize(Object one, Class resultClassObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(MapperFeature.USE_ANNOTATIONS);
        SimpleModule simpleModule = new SimpleModule(
                "FirstSerializer",
                new Version(2, 1, 3, null, null, null)
        );
        SerializerFirst serializerFirst = new SerializerFirst(resultClassObject);
        simpleModule.addSerializer(resultClassObject, serializerFirst);
        objectMapper.registerModule(simpleModule);
        String jsonString = objectMapper.writeValueAsString(one);
        if (isDebugging) System.out.println(new StringBuilder().append("jsonString: \n").append(jsonString).toString());
        return jsonString;
    }

    private static Object customDeserialize(String jsonString, Class resultClassObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(MapperFeature.USE_ANNOTATIONS);
        SimpleModule simpleModule = new SimpleModule(
                "FirstDeserializer",
                new Version(3, 1, 8, null, null, null)
        );
        DeserializerFirst deserializerFirst = new DeserializerFirst(resultClassObject);
        simpleModule.addDeserializer(resultClassObject, deserializerFirst);
        objectMapper.registerModule(simpleModule);
        return objectMapper.readValue(jsonString, resultClassObject);
    }

    public static class DeserializerFirst extends StdDeserializer<First> {
        DeserializerFirst(Class<?> classVariable) {
            super(classVariable);
            if (isDebugging) System.out.println("DeserializerFirst - constructor");
        }

        @Override
        public First deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            if (isDebugging) System.out.println("DeserializerFirst - deserialize");
            First first = new First();
            while (!jsonParser.isClosed()) {
                JsonToken jsonToken = jsonParser.nextToken();

                if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                    String fieldName = jsonParser.getCurrentName();
                    if ("i".equals(fieldName)) {
                        first.i = jsonParser.getValueAsInt();
                    } else if ("name".equals(fieldName)) {
                        first.name = jsonParser.getValueAsString();
                    }
                }
            }
            return first;
        }
    }

    public static class SerializerFirst extends StdSerializer<First> {
        SerializerFirst(Class<First> firstClass) {
            super(firstClass);
            if (isDebugging) System.out.println("SerializerFirst - constructor");
        }

        @Override
        public void serialize(First first, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (isDebugging) System.out.println("SerializerFirst - serialize");
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("i", first.i);
            jsonGenerator.writeStringField("name", first.name);
            jsonGenerator.writeEndObject();
        }
    }

    private static void printSummary() {
        StringBuilder summary = new StringBuilder()
                .append("------------------- SUMMARY -------------------").append("\n")
                .append("Total conversions: ").append(firstToSecondCount + secondToFistCount).append("\n")
                .append("First -> Second conversions: ").append(firstToSecondCount).append("\n")
                .append("First -> Second success rate: ").append((float) firstToSecondSuccesses / firstToSecondCount).append("\n")
                .append("Second -> First conversions: ").append(secondToFistCount).append("\n")
                .append("Second -> First success rate: ").append((float) secondToFistSuccesses / secondToFistCount).append("\n")
                .append("Complex method success rate: ").append((float) complexSuccesses / (firstToSecondCount + secondToFistCount)).append("\n");
        System.out.println(summary.toString());
    }
}