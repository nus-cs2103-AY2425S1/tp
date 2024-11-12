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
     * Verifies that the given index string does not overflow the maximum integer value.
     * The method checks if the string represents a valid integer that is within the range of a 32-bit signed integer
     * and ensures it does not exceed {@code Integer.MAX_VALUE}.
     *
     * <p>If the string starts with a negative or positive sign, it is considered valid as long as the length is not
     * excessive and does not exceed the integer range.</p>
     *
     * @param trimmedIndex The string representing the index value to check.
     * @return {@code true} if the index string does not exceed the maximum integer value;
     *         {@code false} if it overflows {@code Integer.MAX_VALUE}.
     * @throws NullPointerException If the input string is {@code null}.
     */
    public static boolean verifyNotIntOverflow(String trimmedIndex) {
        requireNonNull(trimmedIndex);

        if (trimmedIndex.startsWith("-") || trimmedIndex.startsWith("+")) {
            return true;
        }
        if (trimmedIndex.length() > 10) {
            return false;
        }
        if (trimmedIndex.length() == 10) {
            // lexicographically checks if index is less than max integer
            return trimmedIndex.compareTo(String.valueOf(Integer.MAX_VALUE)) <= 0;
        }
        // checks for valid integer value if length is less than 10, which is checked by isNonZeroUnsignedInteger
        return true;
    }

    /**
     * Verifies that the given index string does not represent a valid number.
     * This method checks if the string contains only digits (0-9). If the string contains any non-digit characters,
     * the method returns {@code true}, indicating that the string is not a valid number.
     *
     * <p>The method returns {@code false} only if the string consists exclusively of digits.</p>
     *
     * @param trimmedIndex The string representing the index value to check.
     * @return {@code true} if the string contains non-digit characters or is not a valid number;
     *         {@code false} if the string is a valid number consisting of digits only.
     * @throws NullPointerException If the input string is {@code null}.
     */
    public static boolean verifyNotNumber(String trimmedIndex) {
        requireNonNull(trimmedIndex);

        // negates the check that input only has digits 0-9
        // AI was used to generate regex
        return !trimmedIndex.matches("\\d+");
    }

    /**
     * Verifies that the given string does not contain excessive leading zeros.
     * A valid string should not have more than 9 leading zeros unless the string represents a value of zero.
     *
     * <p>The method checks the number of leading zeros and returns {@code false} if there are 10 or more.
     * If the string only consists of zeros (e.g., "0000"), the check passes.</p>
     *
     * @param s The string to check for excessive leading zeros.
     * @return {@code true} if the string does not have more than 9 leading zeros or if the string represents zero;
     *         {@code false} if there are 10 or more leading zeros.
     * @throws NullPointerException If the input string is {@code null}.
     */
    public static boolean verifyNotExcessiveLeadingZeros(String s) {
        requireNonNull(s);

        // removes zeros starting from start of string
        // AI was used to generate regex
        String noLeadingZeros = s.replaceFirst("^0+", "");
        int leadingZeroCounter = s.length() - noLeadingZeros.length();

        if (s.matches("0+") && leadingZeroCounter <= 10) {
            return true;
        }
        if (leadingZeroCounter >= 10) {
            return false;
        }

        return true;
    }
}
