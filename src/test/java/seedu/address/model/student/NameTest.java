package seedu.address.model.student;

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
    public void start_case_test() {
        assertTrue((new Name("valid name")).toString().equals("Valid Name"));
        assertTrue((new Name("VALID NAME")).toString().equals("Valid Name"));
        assertTrue((new Name("vAlId nAme")).toString().equals("Valid Name"));
    }

    @Test
    public void different_spacing_test() {
        assertTrue((new Name("Valid      Name")).toString().equals("Valid Name"));
        assertTrue((new Name("Valid Name       ")).toString().equals("Valid Name"));
        assertTrue((new Name("Valid       Name     ")).toString().equals("Valid Name"));
    }

    @Test
    public void differentCasingAndSpacingTest() {
        assertTrue((new Name("VaLId    nAme")).toString().equals("Valid Name"));
        assertTrue((new Name("ValiD NaME       ")).toString().equals("Valid Name"));
        assertTrue((new Name("VALID       NamE     ")).toString().equals("Valid Name"));
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // Name is case-insensitive
        assertTrue(name.equals(new Name("valid name")));

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
