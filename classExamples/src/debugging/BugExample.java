package debugging;

import java.util.ArrayList;
import java.util.List;

public class BugExample {
    public static void main(String[] args) {
        var words = List.of("cattle", "dog", "appalachian", "apple", "pig");
        var results = filterToCWordsAnyLengthAndAWordsGreaterThanFive(words);
        System.out.println(results);
        // Expecting: cattle, appalachian
    }

    static List<String> filterToCWordsAnyLengthAndAWordsGreaterThanFive(List<String> words) {
        var result = new ArrayList<String>();
        try {
            for (var i = words.size() - 1; i >= 0; i--) {
                var word = words.get(i);
                if (word.matches("^(c.*|a.{5,100})$")) {
                    result.add(word);
                }
            }
        } catch (Exception ignore) {
        }
        return result;
    }
}
