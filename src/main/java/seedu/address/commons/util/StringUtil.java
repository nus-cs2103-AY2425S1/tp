package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Represents a pair of {@code sentence} and {@code word} for searching.
     */
    private record SearchPair(String sentence, String word) {
    }

    /**
     * Prepares the {@code sentence} and {@code word} for searching.
     *   Trims the {@code word} and checks if it is empty or contains multiple words.
     *   <br>examples:<pre>
     *       prepareSearch("ABc def  ", "abc") == SearchPair("ABc def", "abc")
     *       prepareSearch("ABc def", "DEF") == SearchPair("ABc def", "DEF")
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    private static SearchPair prepareSearch(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;

        return new SearchPair(preppedSentence, preppedWord);
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        SearchPair searchPair = prepareSearch(sentence, word);
        String preppedSentence = searchPair.sentence;
        String preppedWord = searchPair.word;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        Predicate<String> containsKeywordIgnoringCase =
                stringPart -> stringPart.toUpperCase().contains(preppedWord.toUpperCase());

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(containsKeywordIgnoringCase);
    }

    /**
     * Returns true if the {@code sentence} contains all characters of the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsCharactersInWordIgnoreCase("ABc def", "abc") == true
     *       containsCharactersInWordIgnoreCase("ABc def", "ac") == true
     *       containsCharactersInWordIgnoreCase("ABc def", "af") == false
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsCharactersInWordIgnoreCase(String sentence, String word) {
        SearchPair searchPair = prepareSearch(sentence, word);
        String preppedSentence = searchPair.sentence;
        String preppedWord = searchPair.word;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        int preppedWordLength = preppedWord.length();
        Predicate<String> containsCharactersInKeywordIgnoringCase =
                seq -> {
                    int matchCnt = 0;
                    int cIdx = 0;
                    seq = seq.toUpperCase();
                    String wrd = preppedWord.toUpperCase();
                    for (int i = 0; i < seq.length(); i++) {
                        char s = seq.charAt(i);
                        if (cIdx >= preppedWordLength) {
                            break;
                        }
                        char c = wrd.charAt(cIdx);
                        if (s == c) {
                            matchCnt++;
                            cIdx++;
                        }
                    }
                    return matchCnt == preppedWordLength;
                };

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(containsCharactersInKeywordIgnoringCase);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
