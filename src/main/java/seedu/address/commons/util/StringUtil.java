package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Locale;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    /**
     * Checks if a given word is present in a sentence, ignoring case differences.
     * A match occurs if any word in the sentence contains the specified word as a substring.
     *
     *  <br>examples:<pre>
     *       isWordInSentenceIgnoreCase("ABc def", "abc") == true
     *       isWordInSentenceIgnoreCase("ABc def", "DEF") == true
     *       isWordInSentenceIgnoreCase("ABc def", "AB") == true
     *       isWordInSentenceIgnoreCase("ABc def", "ABcd") == false //not a full word match
     *       isWordInSentenceIgnoreCase("ABc def", "d") == true
     *       isWordInSentenceIgnoreCase("ABc def", "df") == false
     *     </pre>
     *
     * @param sentence The sentence to search within. Must not be null.
     * @param word The word to search for in the sentence. Must not be null, empty, or contain spaces.
     * @return {@code true} if the word is found within any word in the sentence, ignoring case;
     *         {@code false} otherwise.
     **/
    public static boolean isWordInSentenceIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String trimmedWord = word.trim();
        checkArgument(!trimmedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(trimmedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String trimmedSentence = sentence.trim();
        String[] wordsInTrimmedSentence = trimmedSentence.split("\\s+");

        return Arrays.stream(wordsInTrimmedSentence)
                .anyMatch(eachWord -> eachWord.toLowerCase(Locale.ROOT).contains(trimmedWord.toLowerCase(Locale.ROOT)));
    }

    /**
     * Checks if a given word is present in another word, ignoring case differences.
     * Returns true if the {@code wordFromPerson} contains the {@code wordToCheck}.
     *   Ignores case, and a string matching between the two inputs is required.
     *   <br>examples:<pre>
     *       isWordPresentIgnoreCase("helloworld@example.com", "hello") == true
     *       isWordPresentIgnoreCase("1236", "1236") == true
     *       isWordPresentIgnoreCase("1236", "13") == false //not an exact string match
     *       </pre>
     * @param wordFromPerson cannot be null, must be a single word
     * @param wordToCheck cannot be null, cannot be empty, must be a single word
     * @return {@code true} if the word is found within another word, ignoring case;
     *         {@code false} otherwise.
     */
    public static boolean isWordPresentIgnoreCase(String wordFromPerson, String wordToCheck) {
        requireNonNull(wordFromPerson);
        requireNonNull(wordToCheck);

        String trimmedWordToCheck = wordToCheck.trim();
        checkArgument(!trimmedWordToCheck.isEmpty(), "Word parameter cannot be empty");
        checkArgument(
                trimmedWordToCheck.split("\\s+").length == 1,
                "wordToCheck parameter should be a single word");

        String trimmedWordFromPerson = wordFromPerson.trim();
        checkArgument(
                trimmedWordFromPerson.split("\\s+").length == 1,
                "wordFromPerson parameter should be a single word");

        return wordFromPerson.toLowerCase(Locale.ROOT).contains(wordToCheck.toLowerCase(Locale.ROOT));
    }

    /**
     * Compares two strings for equality, ignoring case and leading/trailing whitespaces.
     *
     * @param firstString The first string to compare. Must be non-null and non-empty.
     * @param secondString The second string to compare. Must be non-null and non-empty.
     * @return {@code true} if the trimmed strings are equal ignoring case, otherwise {@code false}.
     * @throws NullPointerException If either string is null.
     * @throws IllegalArgumentException If either string is empty after trimming.
     */
    public static boolean areMatchingStringsIgnoreCase(String firstString, String secondString) {
        requireNonNull(firstString);
        requireNonNull(secondString);

        String trimmedFirstString = firstString.trim();
        checkArgument(!trimmedFirstString.isEmpty(), "First string parameter cannot be empty");

        String trimmedSecondString = secondString.trim();
        checkArgument(!trimmedSecondString.isEmpty(), "Second string parameter cannot be empty");

        return trimmedFirstString.equalsIgnoreCase(trimmedSecondString);
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
     * Checks if a given string represents a boolean value ("true" or "false"), ignoring case.
     *
     * @param s The string to check. Must not be null.
     * @return {@code true} if the string equals "true" or "false" (case-insensitive);
     *         {@code false} otherwise.
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isBooleanValue(String s) {
        requireNonNull(s);
        return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
    }
}
