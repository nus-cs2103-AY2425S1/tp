package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    private static final int TRUNCATED_LINE_NUMBER = 3;
    private static final int TRUNCATED_STRING_LENGTH = 80;

    /**
     * Returns true if the {@code sentence} contains the {@code substring}.
     *   Ignores case, does not require full word match.
     *   <br>examples:<pre>
     *       containsSubstringIgnoreCase("ABc def", "abc") == true
     *       containsSubstringIgnoreCase("ABc def", "DEF") == true
     *       containsSubstringIgnoreCase("ABc def", "AB") == true
     *       containsSubstringIgnoreCase("ABc def", "cde") == false // Sentence does not contain substring.
     *       containsSubstringIgnoreCase("ABc def", "c de") == true
     *       </pre>
     * @param sentence cannot be null
     * @param substring cannot be null, cannot be empty
     * @return true if the {@code sentence} contains the {@code substring}, ignoring case
     */
    public static boolean containsSubstringIgnoreCase(String sentence, String substring) {
        requireNonNull(sentence);
        requireNonNull(substring);

        String preppedSubstring = substring.trim();
        checkArgument(!preppedSubstring.isEmpty(), "Substring parameter cannot be empty");

        // Convert both sentence and substring to upper case to ignore case
        String upperCaseSentence = sentence.toUpperCase();
        String upperCaseSubstring = preppedSubstring.toUpperCase();

        return upperCaseSentence.contains(upperCaseSubstring);
    }

    /**
     * Returns true if the {@code sentence} starts with {@code substring}
     *   Ignores case, does not require full word match.
     *   <br>examples:<pre>
     *       startsWithSubstringIgnoreCase("ABc def", "abc") == true
     *       startsWithSubstringIgnoreCase("ABc def", "DEF") == false // "DEF" is not at the start.
     *       startsWithSubstringIgnoreCase("ABc def", "AB") == true
     *       startsWithSubstringIgnoreCase("ABc def", "cde") == false // "cde" is not at the start.
     *       startsWithSubstringIgnoreCase("ABc def", "ABc d") == true
     *       startsWithSubstringIgnoreCase("ABc def", "bc") == false // "bc" is not at the start.
     *       </pre>
     * @param sentence cannot be null
     * @param substring cannot be null, cannot be empty
     * @return true if the {@code sentence} starts with the {@code substring}, ignoring case
     */
    public static boolean startsWithSubstringIgnoreCase(String sentence, String substring) {
        requireNonNull(sentence);
        requireNonNull(substring);

        String preppedSubstring = substring.trim();
        checkArgument(!preppedSubstring.isEmpty(), "Substring parameter cannot be empty");

        // Convert both sentence and substring to upper case to ignore case
        String upperCaseSentence = sentence.toUpperCase();
        String upperCaseSubstring = preppedSubstring.toUpperCase();

        return upperCaseSentence.startsWith(upperCaseSubstring);
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
     * Returns a truncated string if {@code s} exceeds the TRUNCATED_STRING_LENGTH
     * @param s cannot be null
     * @return the truncated string if {@code s} exceeds TRUNCATED_STRING_LENGTH
     */
    public static String truncateText(String s) {
        assert s != null;
        // If text does not need to be truncated
        if (s.length() < TRUNCATED_STRING_LENGTH && !s.contains("\n")) {
            return s;
        } else if (s.contains("\n")) {
            StringBuilder ans = new StringBuilder();
            int lineCount = 1;

            // Text contains newlines
            String[] lines = s.split("\n");
            for (String line : lines) {
                if (lineCount > TRUNCATED_LINE_NUMBER) {
                    ans.append("...");
                    break;
                }
                if (line.length() > TRUNCATED_STRING_LENGTH) {
                    line = line.substring(0, TRUNCATED_STRING_LENGTH) + "...";
                }
                ans.append(line).append("\n");
                lineCount++;
            }
            return ans.toString();
        } else {
            return s.substring(0, TRUNCATED_STRING_LENGTH) + "...";
        }
    }
}
