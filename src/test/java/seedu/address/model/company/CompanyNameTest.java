package seedu.address.model.company;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
public class CompanyNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CompanyName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new CompanyName(invalidName));
    }

    @Test
    public void isValidName() {
        // null company name
        assertThrows(NullPointerException.class, () -> CompanyName.isValidName(null));

        // invalid name
        assertFalse(CompanyName.isValidName("")); // empty string
        assertFalse(CompanyName.isValidName(" ")); // string with space

        // valid name
        assertTrue(CompanyName.isValidName("*")); // only non-alphanumeric characters
        assertTrue(CompanyName.isValidName("wells fargo")); // only alphabets
        assertTrue(CompanyName.isValidName("12345")); // only numbers
        assertTrue(CompanyName.isValidName("wells 12345")); // alphanumeric characters
        assertTrue(CompanyName.isValidName("Wells Fargo")); // with capital letters
        assertTrue(CompanyName.isValidName("Wells 12345!")); // with ASCII characters
        assertTrue(CompanyName.isValidName("Abc & 123 Alphabet Incorporated")); // long names
    }

    @Test
    public void equals() {
        CompanyName companyName = new CompanyName("NUS");

        // same object -> returns true
        assertTrue(companyName.equals(companyName));

        // same values -> returns true
        assertTrue(companyName.equals(new CompanyName("NUS")));

        // null -> returns false
        assertFalse(companyName.equals(null));

        // different types -> returns false
        assertFalse(companyName.equals(5));

        // different values -> returns false
        assertFalse(companyName.equals(new CompanyName("MUS")));
    }
}
