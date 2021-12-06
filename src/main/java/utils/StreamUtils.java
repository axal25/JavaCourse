package utils;

import java.nio.CharBuffer;
import java.util.stream.Stream;

public class StreamUtils {
    public static Stream<Character> stream(char[] chars) {
        return CharBuffer.wrap(chars).chars().mapToObj(ch -> (char) ch);
    }
}
