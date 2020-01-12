package ru.otus.hw08.json.serialization;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class VunderVafelJSONSerializator {

    /**
     * Получить JSON представление переданного объекта
     *
     * @param objectToConvert объект для сериализации
     * @return JSON сериализованный объект
     */
    public String serializeToJson(Object objectToConvert) throws JsonSerializationException {
        if (objectToConvert == null) {
            throw new JsonSerializationException();
        }
        try {
            JsonValue jsonValue = getJsonValue(objectToConvert);
            return jsonValue != null ? jsonValue.toString() : "";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";
    }

    private List<Field> getAllFields(Class clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }

        List<Field> result = new ArrayList<>(getAllFields(clazz.getSuperclass()));
        result.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return result;
    }

    private JsonValue getJsonValue(Object objectToConvert) throws IllegalAccessException {
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
                } else if (field.getType().isInstance(Collection.class)) {
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
                || primitive.getClass().equals(Byte.class)) {
            return ((Byte) primitive).toString();
        } else if (primitive.getClass().equals(short.class)
                || primitive.getClass().equals(Short.class)) {
            return ((Short) primitive).toString();
        } else if (primitive.getClass().equals(int.class)
                || primitive.getClass().equals(Integer.class)) {
            return ((Integer) primitive).toString();
        } else if (primitive.getClass().equals(long.class)
                || primitive.getClass().equals(Long.class)) {
            return ((Long) primitive).toString();
        } else if (primitive.getClass().equals(float.class)
                || primitive.getClass().equals(Float.class)) {
            return primitive.toString();
        } else if (primitive.getClass().equals(double.class)
                || primitive.getClass().equals(Double.class)) {
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
