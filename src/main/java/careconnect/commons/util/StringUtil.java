package careconnect.commons.util;

import static careconnect.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

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
     * Returns true if the {@code sentence} contains the {@code partialWord}.
     *   Ignores case, but a full word match is NOT required.
     *   <br>examples:<pre>
     *       containsPartialWordIgnoreCase("ABc def", "abc") == true
     *       containsPartialWordIgnoreCase("ABc def", "DEF") == true
     *       containsPartialWordIgnoreCase("ABc def", "AB") == true // full word match not required
     *       containsPartialWordIgnoreCase("ABc def", "fg") == false
     *       </pre>
     * @param sentence cannot be null
     * @param partialWord cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsPartialWordIgnoreCase(String sentence, String partialWord) {
        requireNonNull(sentence);
        requireNonNull(partialWord);

        // prepare partial word (using lower case for case insensitive search)
        String preppedPartialWord = partialWord.trim().toLowerCase();
        checkArgument(!preppedPartialWord.isEmpty(), "Partial Word parameter cannot be empty");
        checkArgument(preppedPartialWord.split("\\s+").length == 1,
                "Partial Word parameter should be a single word");

        // prepare sentence (using lower case for case insensitive search)
        String preppedSentence = sentence.toLowerCase();

        // There is no need to check each individual word in person name for contains preppedWord because
        // 1) if a word in person name contains prepped word -> preppedSentence contains preppedWord
        // 2) if preppedSentence contains prepped word -> a word in person name contains prepped word
        // 2 is because preppedWord doesn't have any spaces
        return preppedSentence.contains(preppedPartialWord);
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
