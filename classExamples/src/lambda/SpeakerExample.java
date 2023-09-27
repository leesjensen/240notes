package lambda;

public class SpeakerExample {

    interface Speaker {
        String sayHello();
    }


    static void speak(Speaker speaker) {
        System.out.println(speaker.sayHello());
    }


    public static void main(String[] args) {
        classSpeakers();
        anonymousSpeakers();
        lambdaSpeakers();

        var englishSpeaker = speakerFactory("hello");
        speak(englishSpeaker);
    }


    /**
     * classSpeakers require you to create a class representation for everything.
     */
    static void classSpeakers() {
        speak(new FrenchSpeaker());
        speak(new GermanSpeaker());
        speak(new EnglishSpeaker());
    }


    static class FrenchSpeaker implements Speaker {
        public String sayHello() {
            return "bonjour";
        }
    }

    // GermanSpeaker.java
    static class GermanSpeaker implements Speaker {
        public String sayHello() {
            return "hallo";
        }
    }

    // EnglishSpeaker.java
    static class EnglishSpeaker implements Speaker {
        public String sayHello() {
            return "hello";
        }
    }


    /**
     * anonymousSpeakers make it so you don't have to declare the classes.
     */
    static void anonymousSpeakers() {
        speak(new Speaker() {
            public String sayHello() {
                return "bonjour";
            }
        });
        speak(new Speaker() {
            public String sayHello() {
                return "hallo";
            }
        });
        speak(new Speaker() {
            public String sayHello() {
                return "hello";
            }
        });
    }

    /**
     * lambdaSpeakers use anonymous classes and a shortened syntax.
     */
    static void lambdaSpeakers() {
        speak(() -> "bonjour");
        speak(() -> "hallo");
        speak(() -> "hello");
    }


    /**
     * Lambda also supports closure. This makes them idea for factory patterns.
     */
    static Speaker speakerFactory(String msg) {
        return () -> msg;
    }
}
