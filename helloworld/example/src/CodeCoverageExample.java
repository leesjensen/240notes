public class CodeCoverageExample {
    public static boolean branch(Boolean result) {
        if (result != null) {
            if (Boolean.TRUE.equals(result)) {
                System.out.println("Branch is true");
                return true;
            } else {
                System.out.println("Branch is false");
                return false;
            }
        }

        throw new RuntimeException("Invalid branch");
    }

    public static void oneHundredPercentCoverage(Object o) {
        o.toString();
    }
}
