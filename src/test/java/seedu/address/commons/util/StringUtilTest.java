package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

public class StringUtilTest {

    //---------------- Tests for isNonZeroUnsignedInteger --------------------------------------

    @Test
    public void isNonZeroUnsignedInteger() {

        // EP: empty strings
        assertFalse(StringUtil.isNonZeroUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isNonZeroUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isNonZeroUnsignedInteger("a"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isNonZeroUnsignedInteger("0"));

        // EP: zero as prefix
        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));

        // EP: signed numbers
        assertFalse(StringUtil.isNonZeroUnsignedInteger("-1"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isNonZeroUnsignedInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isNonZeroUnsignedInteger("1 0")); // Spaces in the middle

        // EP: number larger than Integer.MAX_VALUE
        assertFalse(StringUtil.isNonZeroUnsignedInteger(Long.toString(Integer.MAX_VALUE + 1)));

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isNonZeroUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isNonZeroUnsignedInteger("10"));
    }


    //---------------- Tests for containsWordIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsSubstringIgnoreCase_nullWord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil
                .containsSubstringIgnoreCase("typical sentence", null));
    }

    @Test
    public void containsSubstringIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Substring parameter cannot be empty", ()
            -> StringUtil.containsSubstringIgnoreCase("typical sentence", "  "));
    }

    @Test
    public void containsSubstringIgnoreCase_nullSentence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsSubstringIgnoreCase(null, "abc"));
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsSubstringIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsSubstringIgnoreCase("    ", "123"));

        // Sentence does not contain substring
        assertFalse(StringUtil.containsSubstringIgnoreCase("aaa bbb ccc", "bbbb"));

        // Matches substring in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsSubstringIgnoreCase("aaa bbb ccc", "bb")); // Partial word match
        assertTrue(StringUtil.containsSubstringIgnoreCase("aaa bBb ccc", "Bbb")); // Entire word match
        assertTrue(StringUtil.containsSubstringIgnoreCase("aaa bBb ccc@1", "CCc@1")); // End of sentence (boundary case)
        assertTrue(StringUtil.containsSubstringIgnoreCase("aaa bBb ccc@1", "AaA")); // Start of sentence (boundary case)
        assertTrue(StringUtil.containsSubstringIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsSubstringIgnoreCase("Aaa", "aaa")); // Only one word in sentence
        assertTrue(StringUtil.containsSubstringIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsSubstringIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }

    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertTrue(StringUtil.getDetails(new FileNotFoundException("file not found"))
            .contains("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.getDetails(null));
    }

    @Test
    public void truncateText() {
        // 80 character testString
        String longTestString = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String truncatedLongTest = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa...";
        // 5 character testString
        String shortTestString = "aaaaa";
        // newline character testStrings
        String threeNewLineTestString = "aa\naa\naa\n";
        String fourNewLineTestString = "aa\naa\naa\naa\n";

        // Text is not long enough to be truncated
        assertTrue(StringUtil.truncateText(shortTestString).equals("aaaaa"));
        assertTrue(StringUtil.truncateText(shortTestString).equals(shortTestString));

        // Text is long enough to be truncated
        assertTrue(StringUtil.truncateText(longTestString).equals(truncatedLongTest));

        // Truncated text is not the same as the original text
        assertFalse(StringUtil.truncateText(longTestString).equals(longTestString));

        // Truncated text will truncate if there are more than 3 newline characters
        assertTrue(StringUtil.truncateText(fourNewLineTestString).equals(threeNewLineTestString + "..."));
    }

}
