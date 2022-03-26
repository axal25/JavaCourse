package collections.list.interview;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class InsertIntoList {

    public static <T extends Object> boolean insert(List<T> list, T insertable, int index) {
        validateInsertArguments(list, index);
        return index == list.size()
                ? list.add(insertable)
                : insertNotAfterLastElement(list, insertable, index);
    }

    private static <T extends Object> void validateInsertArguments(List<T> list, int index) {
        if (list == null) {
            throw new NullPointerException("List argument cannot be null");
        }
        if (index < 0)
            throw new ArrayIndexOutOfBoundsException(String.format(
                    "List index argument cannot be lower than 0. Was: %d.", index
            ));
        if (index > list.size())
            throw new ArrayIndexOutOfBoundsException(String.format(
                    "List index argument cannot be higher than size. Was: %d, while list size was: %d.",
                    index, list.size()
            ));
    }

    private static <T extends Object> boolean insertNotAfterLastElement(List<T> list, T insertable, int index) {
        final boolean added = list.add(list.get(list.size() - 1));
        final AtomicReference<T> replacer = new AtomicReference<>(insertable);
        IntStream.range(index, list.size()).forEach(i -> {
            T replaced = list.get(i);
            list.set(i, replacer.get());
            replacer.set(replaced);
        });
        return added;
    }
}
