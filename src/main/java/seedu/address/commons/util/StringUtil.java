package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}, ignoring case.
     * A full word match is not required; any part of the {@code sentence} that matches
     * the {@code word} (ignoring case) will return true.
     * <br>examples:<pre>
     *     containsIgnoreCase("ABc def", "abc") == true
     *     containsIgnoreCase("ABc def", "DEF") == true
     *     containsIgnoreCase("ABc def", "AB") == true // partial match is allowed
     *     containsIgnoreCase("ABc def", "ghi") == false // no match
     * </pre>
     * @param sentence The string in which to search for the word; cannot be null.
     * @param word The string to search for in the sentence; cannot be null, cannot be empty,
     *             must be a single word.
     */
    public static boolean containsIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "String parameter cannot be empty");
        //checkArgument(preppedWord.split("\\s+").length == 1, "String parameter should be a single word");

        String preppedSentence = sentence.toLowerCase();
        return preppedSentence.contains(preppedWord.toLowerCase());
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
