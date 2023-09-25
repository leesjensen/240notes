package general;

public class StaticOuterExample {
    public static void main(String[] args) {
        System.out.println(new StaticOuterExample());
    }

    private InnerExample inner = new InnerExample();

    private static class InnerExample {
        public String toString() {
            return this.getClass().getCanonicalName();
        }
    }

    public String toString() {
        var outer = this.getClass().getCanonicalName();
        return String.format("Outer:%s\nInner:%s", outer, inner);
    }
}
