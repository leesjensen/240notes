package design;

import java.lang.reflect.Constructor;
import java.util.List;

import static java.lang.Class.forName;

public class DependencyInversionExample {

    static class Violation {
        public static void main(String[] args) {
            Honda honda = new Honda();

            new Route().drive(honda);
        }

        static class Route {
            /**
             * Highly coupled with lower class implementation.
             */
            void drive(Honda car) {
                car.go();
            }

        }

        static class Honda {
            void go() {
                System.out.println("put put");
            }
        }
    }

    static class Correct {
        public static void main(String[] args) {
            var vehicleMakerClass = args.length == 1 ? args[0] : "Honda";
            var factory = new VehicleFactory(vehicleMakerClass);

            Vehicle vehicle = factory.createVehicle();

            new Route().drive(vehicle);
        }

        interface Vehicle {
            void go();
        }

        static class Route {
            void drive(Vehicle vehicle) {
                vehicle.go();
            }
        }

        static class VehicleFactory {
            private Constructor<Vehicle> vehicleConstructor;

            VehicleFactory(String vehicleMakerClass) {
                try {
                    var vehicleClass = Class.forName(vehicleMakerClass);
                    vehicleConstructor = (Constructor<Vehicle>) vehicleClass.getDeclaredConstructor();
                } catch (Exception ignored) {
                }
            }

            Vehicle createVehicle() {
                if (vehicleConstructor != null) {
                    try {
                        return vehicleConstructor.newInstance();
                    } catch (Exception ignored) {
                    }
                }
                return new Honda();
            }
        }

        static class Honda implements Vehicle {
            public void go() {
                System.out.println("put put");
            }
        }

        static class BMW implements Vehicle {
            public void go() {
                System.out.println("vroom");
            }
        }
    }
}
