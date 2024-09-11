package fundamentals;

import java.util.Arrays;

public class GetSetExample {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Keep fields private
    private String name;


    public int[] getScores() {
        int[] copy = new int[scores.length];
        System.arraycopy(scores, 0, copy, 0, scores.length);
        return copy;
    }

    public void setScores(int[] scores) {
        for (int score : scores) {
            if (score > 100) {
                return;
            }
        }
        this.scores = scores.clone();
    }

    @Override
    public String toString() {
        return String.format("%s: %s", name, Arrays.toString(scores));
    }

    private int[] scores = new int[10];

    public static void main(String[] args) {
        var e = new GetSetExample();
        e.setName("James");

        int[] newScores = {1, 2, 3};
        e.setScores(newScores);
        newScores[0] = 5;
        System.out.println(e);

        int[] badScores = {1, 200, 3};
        e.setScores(badScores);
        System.out.println(e);
    }
}
