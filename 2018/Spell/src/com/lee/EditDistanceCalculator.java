package com.lee;

/**
 * Created by lee on 1/16/16.
 */
public class EditDistanceCalculator {
    public int distance(String word1, String word2) {
        if (word1.equals(word2)) {
            return 0;
        } else {
            return distance(word1.toCharArray(), word2.toCharArray());
        }
    }

    public int distance(char[] word1, char[] word2) {
        int[][] dist = new int[word1.length + 1][word2.length + 1];

        for (int width = 0; width < dist.length; width++) {
            dist[width][0] = width;
        }

        for (int height = 0; height < dist[0].length; height++) {
            dist[0][height] = height;
        }

        for (int width = 1; width < dist.length; width++) {
            for (int height = 1; height < dist[0].length; height++) {
                int cost = 1;
                if (word1[width-1] == word2[height-1]) {
                    cost = 0;
                }

                int deleteCost = dist[width-1][height] + 1;
                int insertCost = dist[width][height-1] + 1;
                int replaceCost = dist[width-1][height-1] + cost;

                dist[width][height] = Math.min(deleteCost, Math.min(insertCost, replaceCost));

                if (width > 1 && height > 1 && (word1[width-1] == word2[height-2] && word1[width-2] ==  word2[height-1])) {
                    int transCost = dist[width-2][height-2] + cost;
                    dist[width][height] = Math.min(dist[width][height], transCost);
                }
            }

        }

        return dist[dist.length-1][dist[0].length-1];
    }


}
