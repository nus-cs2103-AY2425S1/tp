package seedu.hireme.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.hireme.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1,
                "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param prefix cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsPrefixIgnoreCase(String sentence, String prefix) {
        requireNonNull(sentence);
        requireNonNull(prefix);

        String preppedPrefix = prefix.trim().toLowerCase();
        checkArgument(!preppedPrefix.isEmpty(), "Prefix parameter cannot be empty");
        checkArgument(preppedPrefix.split("\\s+").length == 1,
                "Prefix parameter should be a single word");

        String preppedSentence = sentence.toLowerCase();
        String[] prefixesInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(prefixesInPreppedSentence)
                .anyMatch(preppedSentenceWord -> preppedSentenceWord.contains(preppedPrefix));
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

    /**
     * @param value The value of the data.
     * @param total The total sum.
     * @return String The proportion of this data value in the overall sum as a 2 decimal place percentage string
     *     if the data provided if valid. Otherwise, an empty string is returned.
     */
    public static String getPercentageString(double value, double total) {
        if (value < 0 || total <= 0) {
            return "";
        }

        double proportion = (value / total) * 100;
        if (proportion > 100) {
            return "";
        }

        return String.format("%.2f%%", proportion);
    }
}
