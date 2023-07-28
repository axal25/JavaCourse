package interview.count.inputs;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputsCounter {

    public static void main(String[] args) {
        countWordFrequencies(
                "turtle",
                "tortoise",
                "the", "dog",
                "the", "cat",
                "the", "mouse",
                "the", "dog",
                "the", "mouse",
                "the", "mouse",
                "tortoise",
                "",
                " ",
                null);
    }

    private static int[] countWordFrequencies(String... inputWords) {
        return countFrequencies(inputWords);
    }

    /**
     * Unchanged method signature
     */
    private static int[] countFrequencies(String[] words) {
        System.out.println("Input words: "
                + Arrays.stream(words)
                .map(InputsCounter::toQuotedString)
                .collect(Collectors.joining(", ")));
        Map<String, Integer> wordsToCountMap = getWordsToCountMap(Arrays.stream(words));
        System.out.println("Words-to-Count map: " + toString(wordsToCountMap));
        int[] counts = wordsToCountMap
                .values().stream()
                .mapToInt(i -> i)
                .toArray();
        System.out.println("Frequencies: " + Arrays.toString(counts));
        return counts;
    }

    private static String toString(Map<String, Integer> wordsToCountMap) {
        return String.format("{%s}",
                wordsToCountMap.entrySet().stream()
                        .map(wordToCount -> String.format("%s=%d",
                                toQuotedString(wordToCount.getKey()),
                                wordToCount.getValue()))
                        .collect(Collectors.joining(", ")));
    }

    private static String toQuotedString(String input) {
        return input != null ? String.format("\"%s\"", input) : null;
    }

    private static Map<String, Integer> getWordsToCountMap(Stream<String> words) {
        Map<String, Integer> wordToCounts = new LinkedHashMap<>();
        words.forEach(word -> {
            if (wordToCounts.containsKey(word)) {
                wordToCounts.put(word, wordToCounts.get(word) + 1);
            } else {
                wordToCounts.put(word, 1);
            }
        });
        return wordToCounts;
    }
}
