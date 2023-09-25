package innerClasses;

public class ClosureExample {
    public interface Speaker {
        String sayHello();
    }

    public static void main(String[] args) {

        var spanish = SpeakerFactory("Hola");
        var german = SpeakerFactory("Hallo");

        System.out.printf("Spanish: %s\nGerman: %s", spanish.sayHello(), german.sayHello());
    }

    private static Speaker SpeakerFactory(String helloPhrase) {
        class InnerExample implements Speaker {
            public String sayHello() {
                return helloPhrase;
            }
        }

        return new InnerExample();
    }
}