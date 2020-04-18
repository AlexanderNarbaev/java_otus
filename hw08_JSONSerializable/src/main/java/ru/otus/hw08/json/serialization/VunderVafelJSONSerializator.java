package ru.otus.hw08.json.serialization;

import lombok.extern.log4j.Log4j2;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

@Log4j2
public class VunderVafelJSONSerializator {

    /**
     * Получить JSON представление переданного объекта
     *
     * @param objectToConvert объект для сериализации
     * @return JSON сериализованный объект
     */
    public String serializeToJson(Object objectToConvert) throws JsonSerializationException {
        if (objectToConvert == null) {
            throw new JsonSerializationException("Object to serialize is null");
        }
        try {
            JsonValue jsonValue = getJsonValue(objectToConvert);
            return jsonValue != null ? jsonValue.toString() : "";
        } catch (Exception e) {
            log.error("Error when try to serialize JSON", e);
        }
        throw new JsonSerializationException("No JSON when serialization done");
    }

    private List<Field> getAllFields(Class clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }

        List<Field> result = new ArrayList<>(getAllFields(clazz.getSuperclass()));
        result.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return result;
    }

    private JsonValue getJsonValue(Object objectToConvert) throws Exception {
        if (objectToConvert == null) return null;
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        String primitiveValue = getPrimitiveValue(objectToConvert);
        Class<?> aClass = objectToConvert.getClass();
        List<Field> fields = getAllFields(aClass);
        if (primitiveValue != null) {
            return Json.createValue(primitiveValue);
        } else if (!fields.isEmpty()) {
            for (Field field : fields) {
                field.setAccessible(true);
                Object fieldValue = field.get(objectToConvert);
                String primitiveFieldValue = getPrimitiveValue(fieldValue);
                if (primitiveFieldValue != null) {
                    objectBuilder.add(field.getName(), primitiveFieldValue);
                } else if (field.getType().isArray()) {
                    int arrayLength = Array.getLength(fieldValue);
                    if (arrayLength > 0) {
                        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                        for (int i = 0; i < arrayLength; i++) {
                            arrayBuilder.add(getJsonValue(Array.get(fieldValue, i)));
                        }
                        objectBuilder.add(field.getName(), arrayBuilder);
                    }
                } else if (Collection.class.isAssignableFrom(field.getType())) {
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    for (Object o : ((Collection) fieldValue)) {
                        arrayBuilder.add(getJsonValue(o));
                    }
                    objectBuilder.add(field.getName(), arrayBuilder);
                }

            }
        }
        JsonObject build = objectBuilder.build();
        return build;
    }

    private String getPrimitiveValue(Object primitive) {
        if (primitive.getClass().equals(boolean.class)
                || primitive.getClass().equals(Boolean.class)) {
            return ((Boolean) primitive).toString();
        } else if (primitive.getClass().equals(byte.class)
                || primitive.getClass().equals(short.class)
                || primitive.getClass().equals(int.class)
                || primitive.getClass().equals(long.class)
                || primitive.getClass().equals(float.class)
                || primitive.getClass().equals(double.class)
                || Number.class.isAssignableFrom(primitive.getClass())) {
            return primitive.toString();
        } else if (primitive.getClass().equals(char.class)
                || primitive.getClass().equals(Character.class)) {
            return ((Character) primitive).toString();
        } else if (primitive.getClass().equals(String.class)) {
            return ((String) primitive);
        }
        return null;
    }
}
