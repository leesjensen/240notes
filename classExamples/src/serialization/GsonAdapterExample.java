package serialization;

import com.google.gson.*;
import com.google.gson.stream.*;

import java.io.IOException;
import java.util.Arrays;

public class GsonAdapterExample {

    public static class Animal {
        final private String species;
        final private String sound;

        public Animal(String species, String sound) {
            this.species = species;
            this.sound = sound;
        }

        public String toString() {
            return "Animal";
        }
    }

    public static class Cow extends Animal {

        public Cow() {
            super("Cow", "moo");
        }

        public String toString() {
            return "Cow";
        }
    }


    public static class Snake extends Animal {

        public Snake() {
            super("Snake", "hiss");
        }

        public String toString() {
            return "Snake";
        }
    }

    public static void main(String[] args) {
        var zoo = new Animal[]{new Cow(), null, new Snake()};

        basicSerialization(zoo);

        adapterSerialization(zoo);
    }

    private static void basicSerialization(Animal[] zoo) {
        Gson gson = new Gson();

        serialize(gson, zoo);
    }

    private static void adapterSerialization(Animal[] zoo) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Animal.class, getJsonTypeAdapter())
                .create();

        serialize(gson, zoo);
    }

    private static void serialize(Gson gson, Animal[] zoo) {
        String json = gson.toJson(zoo);
        System.out.println(json);

        Animal[] rehydratedZoo = gson.fromJson(json, Animal[].class);
        Arrays.stream(rehydratedZoo).forEach(System.out::println);
    }


    public static TypeAdapter<Animal> getJsonTypeAdapter() {
        return new TypeAdapter<>() {
            @Override
            public void write(JsonWriter w, Animal animal) throws IOException {
                if (animal != null) {
                    w.beginObject();
                    w.name("species");
                    w.value(animal.species);
                    w.name("sound");
                    w.value(animal.sound);
                    w.endObject();
                } else {
                    w.nullValue();
                }
            }

            @Override
            public Animal read(JsonReader jsonReader) {
                Animal animal = new Gson().fromJson(jsonReader, Animal.class);
                if (animal != null) {
                    animal = switch (animal.species) {
                        case "Cow" -> new Cow();
                        case "Snake" -> new Snake();
                        default -> animal;
                    };
                }
                return animal;
            }
        };
    }
}
