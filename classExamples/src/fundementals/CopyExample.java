package fundementals;

import java.util.Arrays;

public class CopyExample {
    public static class ShallowCopy {
        String[] data;

        public ShallowCopy() {
            data = new String[]{"a", "b", "c"};
        }

        public ShallowCopy(ShallowCopy copy) {
            data = copy.data;
        }

        public static void main(String[] args) {
            var source = new ShallowCopy();
            var copy = new ShallowCopy(source);

            // Change the source data
            source.data[0] = "x";

            // The copy outputs 'x'
            System.out.println(copy.data[0]);
        }
    }

    public static class DeepCopy {
        String[] data;

        public DeepCopy() {
            data = new String[]{"a", "b", "c"};
        }

        public DeepCopy(DeepCopy copy) {
            data = Arrays.copyOf(copy.data, copy.data.length);
        }

        public static void main(String[] args) {
            var source = new DeepCopy();
            var copy = new DeepCopy(source);

            // Change the source data
            source.data[0] = "x";

            // The copy is not effected by the source
            // outputs 'a'
            System.out.println(copy.data[0]);
        }
    }


    public static class CloneCopy implements Cloneable {
        String[] data;

        public CloneCopy() {
            data = new String[]{"a", "b", "c"};
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            var clone = new CloneCopy();
            clone.data = Arrays.copyOf(data, data.length);
            return clone;
        }

        public static void main(String[] args) throws CloneNotSupportedException {
            var source = new CloneCopy();
            var copy = (CloneCopy) source.clone();

            // Change the source data
            source.data[0] = "x";

            // The copy is not effected by the source
            // outputs 'a'
            System.out.println(copy.data[0]);
        }
    }
}
