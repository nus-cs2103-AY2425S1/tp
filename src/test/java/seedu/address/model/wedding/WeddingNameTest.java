package seedu.address.model.wedding;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.Name;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class WeddingNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WeddingName(null));
    }

    @Test
    public void constructor_invalidWeddingName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new WeddingName(invalidName));
    }

    @Test
    public void isValidWeddingName() {
        // null name
        assertThrows(NullPointerException.class, () -> WeddingName.isValidWeddingName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("peter and jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("John Junior and Jane Seraphim")); // long names
    }

    @Test
    public void isWithinCharLimit() {
        assertTrue(Name.isWithinCharLimit("validWeddingName"));
        assertFalse(Name.isWithinCharLimit("longlonglonglonglonglonglonglonglonglonglong"));
    }

    @Test
    public void equals() {
        WeddingName name = new WeddingName("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new WeddingName("Valid Name")));

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
