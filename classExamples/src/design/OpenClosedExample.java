package design;

import java.util.ArrayList;
import java.util.List;

public class OpenClosedExample {

    public static void main(String[] args) {
        var violation = new OpenForModificationList(new String[]{"a", "b", "c"});
        violation.formatCommaSeparated();
        violation.formatQuotedCommaSeparated();
        // What about dash separated or double quotes?

    }

    public static class OpenForModificationList {
        final private String[] items;

        public OpenForModificationList(String[] items) {
            this.items = items;
        }

        public String formatCommaSeparated() {
            return String.join(",", items);
        }

        public String formatQuotedCommaSeparated() {
            var formattedItems = new ArrayList<String>();
            for (var item : items) {
                formattedItems.add(String.format("'%s'", item));
            }

            return String.join(",", formattedItems);
        }
    }


    public interface Formatter<T> {
        String format(T s);
    }

    public static class OpenForExtensionList<T> {
        final private List<T> items;

        public OpenForExtensionList(List<T> items) {
            this.items = items;
        }

        public String format(Formatter formatter, String separator) {
            var formattedItems = new ArrayList<String>();
            for (var item : items) {
                formattedItems.add(formatter.format(item));
            }

            return String.join(separator, formattedItems);
        }
    }

}
