package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RuntimeExample {

    public static void main(String[] args) {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Automobile.class, )
            Gson gson = builder.create();

            // Generate a json string
            String jsonString = gson.toJson(garage);
            System.out.println(jsonString);
    }

//        public static void xmain(String[] args) {
//            Garage garage = new Garage();
//
//            garage.addAutomobile(new Car("Ford", "Mustang", 1965));
//            garage.addAutomobile(new Car("Chevrolet", "Corvette", 2023));
//            garage.addAutomobile(new Truck("Toyota", "Tundra", 2021, true));
//
//            final RuntimeTypeAdapterFactory<Automobile> typeFactory = RuntimeTypeAdapterFactory
//                    .of(Automobile.class, "type")
//                    .registerSubtype(Car.class)
//                    .registerSubtype(Truck.class);
//
//            // Register a type adapter for the automobile interface
//            GsonBuilder builder = new GsonBuilder();
//            builder.registerTypeAdapterFactory(typeFactory);
//            Gson gson = builder.create();
//
//            // Generate a json string
//            String jsonString = gson.toJson(garage);
//            System.out.println(jsonString);
//
//            // Parse the json string
//            Garage parsedGarage = gson.fromJson(jsonString, Garage.class);
//            System.out.println(parsedGarage);
//        }


    public interface Automobile {
        String getMake();

        String getModel();

        int getYear();
    }


    static public class Truck extends AutomobileImpl {
        private final boolean fourWheelDrive;

        public Truck(String make, String model, int year, boolean fourWheelDrive) {
            super(make, model, year);
            this.fourWheelDrive = fourWheelDrive;
        }

        public boolean isFourWheelDrive() {
            return fourWheelDrive;
        }

        @Override
        public String toString() {
            return "Truck{" +
                    "fourWheelDrive=" + fourWheelDrive +
                    "} " + super.toString();
        }
    }


    static public abstract class AutomobileImpl implements Automobile {
        private final String make;
        private final String model;
        private final int year;

        public AutomobileImpl(String make, String model, int year) {
            this.make = make;
            this.model = model;
            this.year = year;
        }

        @Override
        public String getMake() {
            return make;
        }

        @Override
        public String getModel() {
            return model;
        }

        @Override
        public int getYear() {
            return year;
        }

        @Override
        public String toString() {
            return "AutomobileImpl{" +
                    "make='" + make + '\'' +
                    ", model='" + model + '\'' +
                    ", year=" + year +
                    '}';
        }
    }

    static public class Car extends AutomobileImpl {
        public Car(String make, String model, int year) {
            super(make, model, year);
        }

        @Override
        public String toString() {
            return "Car{} " + super.toString();
        }
    }

    static public class Garage {
        private final List<Automobile> automobiles = new ArrayList<>();

        public void addAutomobile(Automobile automobile) {
            automobiles.add(automobile);
        }

        @Override
        public String toString() {
            return "Garage{" +
                    "automobiles=" + automobiles +
                    '}';
        }
    }
}
