package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.regex.Pattern;

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
     * Returns true if the {@code phoneNumber} contains the {@code number}.
     *   It requires at least one number to match the phoneNumber.
     *   <br>examples:<pre>
     *       containsNumber("123456", "123") == true
     *       containsNumber("123456", "789") == false
     *       containsNumber("123456", "124") == false
     *       </pre>
     * @param phoneNumber cannot be null, must be a single number
     * @param inputNumber cannot be null, cannot be empty, must be a single number
     */
    public static boolean containsNumber(String phoneNumber, String inputNumber) {
        requireNonNull(phoneNumber);
        requireNonNull(inputNumber);

        String preppedNumber = inputNumber.trim();
        checkArgument(!preppedNumber.isEmpty(), "Number parameter cannot be empty");

        String preppedPhoneNumber = phoneNumber.trim();
        if (preppedPhoneNumber.isEmpty()) {
            return false;
        }

        //phone number should not have any space
        checkArgument(preppedNumber.split("\\s+").length == 1, "Number parameter should be a single word");
        checkArgument(phoneNumber.split("\\s+").length == 1, "phoneNumber parameter should be a single word");

        Pattern pattern = Pattern.compile("^\\d+$");

        return pattern.matcher(phoneNumber).matches() && phoneNumber.contains(inputNumber);
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
