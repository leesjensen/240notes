package serialization;

import com.google.gson.Gson;

public class GsonExample {

    static class Cow {
        private String x;
        public int y;
    }

    public static void main(String[] args) {
        var obj = new Cow();
        obj.x = "hello";
        obj.y = 3;
        var serializer = new Gson();

        String json = serializer.toJson(obj);
        System.out.println("JSON: " + json);

        var objFromJson = serializer.fromJson(json, Cow.class);
        System.out.println("Object: " + objFromJson);
    }
}
