package testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ParameterizationExample {

    @ParameterizedTest
    @ValueSource(classes = {ArrayList.class, LinkedList.class, Stack.class})
    public void addAndGetToList(Class<? extends List> listClass) throws Exception {
        var list = listClass.getDeclaredConstructor().newInstance();
        var expectedItem = "item";

        list.add(expectedItem);
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(expectedItem, list.get(0));
    }
}
