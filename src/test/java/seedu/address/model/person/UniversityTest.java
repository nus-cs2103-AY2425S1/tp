package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UniversityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new University(null));
    }

    @Test
    public void constructor_invalidUniversity_throwsIllegalArgumentException() {
        String invalidUniversity = "";
        assertThrows(IllegalArgumentException.class, () -> new University(invalidUniversity));
    }

    @Test
    public void isValidUniversity() {
        // null university
        assertThrows(NullPointerException.class, () -> University.isValidUniversity(null));

        // invalid universities
        assertFalse(University.isValidUniversity("")); // empty string
        assertFalse(University.isValidUniversity(" ")); // spaces only
        assertFalse(University.isValidUniversity("^")); // only non-alphanumeric characters
        assertFalse(University.isValidUniversity("NUS*")); // contains non-alphanumeric characters

        // valid universities
        assertTrue(University.isValidUniversity("NUS")); // alphabets only
        assertTrue(University.isValidUniversity("National University of New-York")); // alphabets and spaces
        assertTrue(University.isValidUniversity("NTU123")); // alphanumeric characters
    }

    @Test
    public void equals() {
        University university = new University("NUS");

        // same values -> returns true
        assertTrue(university.equals(new University("NUS")));

        // same object -> returns true
        assertTrue(university.equals(university));

        // null -> returns false
        assertFalse(university.equals(null));

        // different types -> returns false
        assertFalse(university.equals(5.0f));

        // different values -> returns false
        assertFalse(university.equals(new University("NTU")));
    }
}
