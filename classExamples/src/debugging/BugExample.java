package debugging;

import com.google.gson.Gson;

import java.util.*;

public class BugExample {
    public static void main(String[] args) {
        var list = List.of(args);
        var filteredList = filterToCWordsAnyLengthAndAWordsGreaterThanFive(list);
        System.out.println(new Gson().toJson(filteredList));
    }

    static Collection<String> filterToCWordsAnyLengthAndAWordsGreaterThanFive(List<String> words) {
        var result = new ArrayList<String>();
        try {
            for (var i = words.size(); i >= 0; i--) {
                var word = words.get(i);
                if (word.matches("^(c|a).{3,100}$")) {
                    result.add(word);
                }
            }
        } catch (Exception ignore) {
        }
        return result;
    }
}
