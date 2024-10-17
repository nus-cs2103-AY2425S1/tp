package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.person.Gender.FEMALE_SYMBOL;
import static seedu.address.model.person.Gender.MALE_SYMBOL;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GenderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Gender(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidGender = "mmm";
        assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGender));
    }

    @Test
    public void isValidGender() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Gender.isValidGender(null));

        // invalid genders
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // spaces only
        assertFalse(Gender.isValidGender("91")); // numbers used
        assertFalse(Gender.isValidGender("MMM")); // not male or female
        // valid phone numbers
        assertTrue(Gender.isValidGender("male")); // male gender
        assertTrue(Gender.isValidGender("female")); // female gender
    }

    @Test
    public void getGenderWithSymbol() {
        Gender gender = new Gender("male");
        assertEquals(MALE_SYMBOL, gender.getGenderWithSymbol());
        assertNotEquals(FEMALE_SYMBOL, gender.getGenderWithSymbol());
        assertNotEquals("male", gender.getGenderWithSymbol());
        gender = new Gender("female");
        assertEquals(FEMALE_SYMBOL, gender.getGenderWithSymbol());
        assertNotEquals(MALE_SYMBOL, gender.getGenderWithSymbol());
        assertNotEquals("female", gender.getGenderWithSymbol());
    }

    @Test
    public void equals() {
        Gender gender = new Gender("male");

        // same values -> returns true
        assertTrue(gender.equals(new Gender("male")));

        // same object -> returns true
        assertTrue(gender.equals(gender));

        // null -> returns false
        assertFalse(gender.equals(null));

        // different types -> returns false
        assertFalse(gender.equals(5.0f));

        // different values -> returns false
        assertFalse(gender.equals(new Gender("female")));
    }
}
