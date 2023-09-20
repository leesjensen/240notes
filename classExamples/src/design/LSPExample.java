package design;

import java.util.ArrayList;
import java.util.List;

public class LSPExample extends Object {

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    void lspViolation2(List list) {
        var arrayList = (ArrayList) list;
        arrayList.subList(0, 3);
    }
}
