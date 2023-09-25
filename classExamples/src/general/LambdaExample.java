package general;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LambdaExample {
    public static void main(String[] args) {
        var cow = "cow";
        var spanish = new Speaker() {
            public String sayHello() {
                return "Hallo" + cow;
            }
        };
        var german = new Speaker() {
            public String sayHello() {
                return "Hallo";
            }
        };

//        System.out.printf("Spanish: %s\nGerman: %s", spanish.sayHello(), german.sayHello());

        speak(german);
        speak(new Speaker() {
            public String sayHello() {
                return "coco";
            }
        });
        speak(() -> "rat");
        speak(() -> {
            return "pico";
        });

        var englishSpeaker = speakerFactory("hello");
        speak(englishSpeaker);

        filterList();
    }

    static Speaker speakerFactory(String hello) {
        return () -> hello;
    }

    static void speak(Speaker speaker) {
        System.out.println(speaker.sayHello());
    }

    interface Speaker {
        String sayHello();
    }

    static void filterList() {
        var list = new ArrayList<>(List.of(1, 3));
        list.removeIf(new Predicate<Integer>() {
            public boolean test(Integer n) {
                return n > 2;
            }
        });

        list.removeIf(n -> n > 2);
    }
}