package ru.otus.hw08;

import com.google.gson.Gson;
import lombok.extern.java.Log;
import ru.otus.hw08.json.serialization.JsonSerializationException;
import ru.otus.hw08.json.serialization.VunderVafelJSONSerializator;

@Log
public class JSONTest {

    public static void main(String[] args) {
        ClassToTestJsonSerialization classToTestJsonSerialization = new ClassToTestJsonSerialization();
        System.out.println(classToTestJsonSerialization);
        String json = null;
        VunderVafelJSONSerializator serializator = new VunderVafelJSONSerializator();
        try {
            json = serializator.serializeToJson(classToTestJsonSerialization);
        } catch (JsonSerializationException e) {

        }
        System.out.println(json);
        Gson gson = new Gson();

        ClassToTestJsonSerialization classToTestJsonSerialization2 = gson.fromJson(json, ClassToTestJsonSerialization.class);
        System.out.println(classToTestJsonSerialization.equals(classToTestJsonSerialization2));
        System.out.println(classToTestJsonSerialization2);
    }
}
