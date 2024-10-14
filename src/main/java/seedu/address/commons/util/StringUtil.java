package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code subword} is a substring of any word in {@code sentence}.
     *   Ignores case
     *   <br>examples:<pre>
     *       containsIgnoreCase("ABc def", "abc") == true
     *       containsIgnoreCase("ABc def", "DEF") == true
     *       containsIgnoreCase("ABc def", "AB") == true
     *       containsIgnoreCase("ABc def", "cde") == false
     *       </pre>
     * @param sentence cannot be null
     * @param subword cannot be null, cannot be empty, must not contain spaces
     */
    public static boolean containsIgnoreCase(String sentence, String subword) {
        requireNonNull(sentence);
        requireNonNull(subword);

        String preppedSubword = subword.trim().toLowerCase();
        checkArgument(!preppedSubword.isEmpty(), "Subword parameter cannot be empty");
        checkArgument(preppedSubword.split("\\s+").length == 1, "Subword parameter should not contain spaces");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.toLowerCase().split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(word -> word.contains(preppedSubword));
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
