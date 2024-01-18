package fundamentals;

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
        for (var i = 0; i < scores.length; i++) {
            if (scores[i] > 100) {
                return;
            }
        }
        this.scores = scores;
    }

    private int[] scores = new int[10];
}
