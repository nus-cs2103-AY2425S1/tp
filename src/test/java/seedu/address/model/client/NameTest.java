package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void isValidRomanNumeral() {
        // Test valid Roman numerals
        assertTrue(Name.isRomanNumeral("I")); // single numeral
        assertTrue(Name.isRomanNumeral("IV")); // subtraction rule
        assertTrue(Name.isRomanNumeral("VI")); // addition rule
        assertTrue(Name.isRomanNumeral("XIV")); // complex number
        assertTrue(Name.isRomanNumeral("XXXIX")); // multiple numerals
        assertTrue(Name.isRomanNumeral("CDXLIV")); // various numerals
        assertTrue(Name.isRomanNumeral("MCMXCIX")); // complex combinations
        assertTrue(Name.isRomanNumeral("MMM")); // big number

        // Test case insensitivity
        assertTrue(Name.isRomanNumeral("ii")); // lowercase
        assertTrue(Name.isRomanNumeral("Xi")); // mixed case
        assertTrue(Name.isRomanNumeral("vIi")); // mixed case

        // Test invalid Roman numerals
        assertFalse(Name.isRomanNumeral("ABC")); // non-Roman letters
        assertFalse(Name.isRomanNumeral("IIII")); // invalid repetition
        assertFalse(Name.isRomanNumeral("VV")); // invalid repetition of V
        assertFalse(Name.isRomanNumeral("LL")); // invalid repetition of L
        assertFalse(Name.isRomanNumeral("DD")); // invalid repetition of D
        assertFalse(Name.isRomanNumeral("IC")); // invalid subtraction
        assertFalse(Name.isRomanNumeral("XM")); // invalid subtraction
        assertFalse(Name.isRomanNumeral("VX")); // invalid ordering
        assertFalse(Name.isRomanNumeral("XIIX")); // invalid combination
        assertFalse(Name.isRomanNumeral("IVIV")); // invalid repetition of subtractive pair
        assertFalse(Name.isRomanNumeral("V II")); // contains space
        assertFalse(Name.isRomanNumeral("XV3")); // contains numbers
        assertFalse(Name.isRomanNumeral("X_V")); // contains special characters
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
