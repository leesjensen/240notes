package fundementals;

public class EnumExample {
    public static void main(String[] args) {
        try {
            var e = Enum.valueOf(Peak.class, args[0].toUpperCase());

            if (e == Peak.LONE) {
                System.out.println("You chose Lone Peak");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Unknown peak provided");
        }
    }

    public enum Peak {
        NEBO, PROVO, SANTAQUIN, TIMPANOGOS, CASCADE, SPANISH, LONE
    }
}
