package debugging;

import java.util.*;

public class BugExample {
    public static void main(String[] args) {
        var words = List.of("cattle", "dog", "appalachian", "apple", "pig", "cat");
        var results = filterToCWordsAnyLengthAndAWordsGreaterThanFive(words);
        System.out.println(results);
        // Expecting: cattle, appalachian, cat
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