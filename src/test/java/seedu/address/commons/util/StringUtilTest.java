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
    public void containsWordIgnoreCase_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsIgnoreCase("typical sentence", null));
    }

    @Test
    public void containsWordIgnoreCase_empty_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "String parameter cannot be empty", ()
            -> StringUtil.containsIgnoreCase("typical sentence", "  "));
    }

    @Test
    public void containsIgnoreCase_nullSentence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsIgnoreCase(null, "abc"));
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
     *   - query matches multiple words or parts of multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of the sentence word
     *   - no partial match of query with any sentence word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertTrue(StringUtil.containsIgnoreCase("aaa bbb ccc", "bb")); // Sentence word bigger than query word
        assertFalse(StringUtil.containsIgnoreCase("aaa bbb ccc", "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsIgnoreCase("aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertTrue(StringUtil.containsIgnoreCase("aaa bBb ccc@1", "CCc@1")); // Last word (boundary case)
        assertTrue(StringUtil.containsIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsIgnoreCase("Aaa", "aaa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces
        assertTrue(StringUtil.containsIgnoreCase("aaa123 bbb234 ccc345", "123")); // Partial match with numbers

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsIgnoreCase("AAA bBb ccc  bbb", "bbB"));

        // No matches in any part of the sentence
        assertFalse(StringUtil.containsIgnoreCase("aaa bbb ccc", "ddd")); // Query has no match in sentence
        assertFalse(StringUtil.containsIgnoreCase("aaa bbb ccc", "ee")); // Query word is smaller but not found
        assertFalse(StringUtil.containsIgnoreCase("aaa bbb ccc", "eeee")); // Query word is larger but not found
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

}
