package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GsonAdapterExample {

    public static void main(String[] args) {
        var obj = new String[]{"cat", "dog", "cow"};

        var serializer = new GsonBuilder()
                .registerTypeAdapter(String.class, getJsonTypeAdapter())
                .create();

        var json = serializer.toJson(obj);
        System.out.println("JSON:   " + json);

        var objFromJson = serializer.fromJson(json, obj.getClass());
        System.out.println("Object: " + Arrays.toString(objFromJson));
    }


    public static TypeAdapter<String> getJsonTypeAdapter() {
        final String prefix = "x-";
        return new TypeAdapter<>() {
            @Override
            public void write(JsonWriter w, String text) throws IOException {
                w.value(prefix + text);
            }

            @Override
            public String read(JsonReader r) throws IOException {
                var text = r.nextString().substring(prefix.length());
                return text;
            }
        };
    }
}
