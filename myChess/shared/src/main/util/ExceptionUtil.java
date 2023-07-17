package util;

import java.util.Optional;
import java.util.stream.Stream;

public class ExceptionUtil {
    public static String getMsg(Throwable e) {
        Optional<Throwable> rootCause = Stream.iterate(e, Throwable::getCause)
                .filter(element -> element.getCause() == null)
                .findFirst();

        return rootCause.isPresent() ? rootCause.get().getMessage() : "unknown";
    }
}
