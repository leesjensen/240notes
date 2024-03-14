package debugging;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class BugExampleTest {

    @Test
    void filterToCWordsAnyLengthAndAWordsGreaterThanFive() {
        var words = List.of("cattle", "dog", "appalachian", "apple", "pig", "cat");
        var results = BugExample.filterToCWordsAnyLengthAndAWordsGreaterThanFive(words);
        var expected = List.of("cattle", "appalachian", "cat");
        Assertions.assertIterableEquals(expected, results);
    }
}