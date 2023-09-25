package innerClasses;

public class AnonymousExample {
    public interface Speaker {
        String sayHello();
    }

    public static void main(String[] args) {
        var spanish = new Speaker() {
            public String sayHello() {
                return "Hola";
            }
        };
        var german = new Speaker() {
            public String sayHello() {
                return "Hallo";
            }
        };

        System.out.printf("Spanish: %s\nGerman: %s", spanish.sayHello(), german.sayHello());
    }
}
