package fundementals;

import java.util.Objects;


public class RecordExample {

    public static void main(String[] args) {
        System.out.println(new PetClass(1, "Fido", "dog"));
        System.out.println(new PetRecord(1, "Fido", "dog"));
    }

        public record PetRecord(int id, String name, String type) {
            PetRecord rename(String newName) {
                return new PetRecord((id), newName, type);
            }
        }

    static public class PetClass {
        private final int id;
        private final String name;
        private final String type;

        PetClass(int id, String name, String type) {

            this.id = id;
            this.name = name;
            this.type = type;
        }

        PetClass rename(String newName) {
            return new PetClass(id, newName, type);
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PetClass petClass = (PetClass) o;

            if (id != petClass.id) return false;
            if (!Objects.equals(name, petClass.name)) return false;
            return Objects.equals(type, petClass.type);
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + (type != null ? type.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "PetClass[" +
                    "id=" + id +
                    ", name=" + name +
                    ", type=" + type +
                    ']';
        }
    }
}
