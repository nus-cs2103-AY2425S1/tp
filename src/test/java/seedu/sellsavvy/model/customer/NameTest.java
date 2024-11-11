package seedu.sellsavvy.model.customer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.testutil.Assert.assertThrows;

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

        // invalid names
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("-")); // no alphanumeric characters before and after symbol
        assertFalse(Name.isValidName("peter*")); // invalid symbol
        assertFalse(Name.isValidName("Peter S/O ")); // no alphanumeric characters after symbol
        assertFalse(Name.isValidName("Daniel, Peter-Tan")); // multiple symbols

        // valid names
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Name.isValidName("Swee Beng-Peter Tan")); // valid symbol
        assertTrue(Name.isValidName("Swee Beng, Peter Tan")); // valid comma with space
        assertTrue(Name.isValidName("D'Costa D/O Rajarat-nam")); // valid use of multiple symbols
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

    @Test
    public void isSimilarTo() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.isSimilarTo(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.isSimilarTo(name));

        // null -> returns false
        assertThrows(NullPointerException.class, () -> name.isSimilarTo(null));

        // different values -> returns false
        assertFalse(name.isSimilarTo(new Name("Other Valid Name")));

        // Only different in casing -> returns true
        assertTrue(name.isSimilarTo(new Name("valid name")));

        // Only different in space -> returns true
        assertTrue(name.isSimilarTo(new Name("ValidName")));

        // different in both casing and space
        assertTrue(name.isSimilarTo(new Name("validName")));
    }
}
