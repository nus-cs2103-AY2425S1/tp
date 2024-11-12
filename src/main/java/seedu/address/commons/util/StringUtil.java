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
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns true if the {@code sentence} contains the {@code name}.
     *  Ignores case, as long as sentence contains name.
     *  <br>examples:<pre>
     *      containsNameIgnoreCase("ABc def", "abc") == true //contains abc
     *      containsNameIgnoreCase("ABc def", "DEF") == true //contains DEF
     *      containsNameIgnoreCase("ABc def", "ABde") == false //does not contain ABde
     *      </pre>
     * @param sentence cannot be null
     * @param name cannot be null, cannot be empty
     */
    public static boolean containsNameIgnoreCase(String sentence, String name) {
        requireNonNull(sentence);
        requireNonNull(name);

        String preppedName = name.trim();
        checkArgument(!preppedName.isEmpty(), "Name parameter cannot be empty");

        return sentence.toLowerCase().contains(preppedName.toLowerCase());
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
     * Returns true if {@code s} represents a valid signed integer.
     * A valid signed integer can be positive, negative, or zero.
     * e.g. "1", "-1", "0", {@code Integer.MAX_VALUE}, {@code Integer.MIN_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "1.5" (contains decimal), "abc" (contains letters), "1a" (contains both digits and letters),
     * @param s The string to check for valid integer format.
     * @return true if {@code s} represents a valid signed integer, otherwise false.
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isInteger(String s) {
        requireNonNull(s);

        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
