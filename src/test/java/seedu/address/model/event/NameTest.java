import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import seedu.address.model.event.Name;

public class NameTest {

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // invalid names
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("@Meeting")); // special characters

        // valid names
        assertTrue(Name.isValidName("Meeting"));
        assertTrue(Name.isValidName("Project Planning"));
        assertTrue(Name.isValidName("Event123"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Name name = new Name("Conference");
        assertTrue(name.equals(name));
    }

    @Test
    public void equals_differentObjectsSameValue_returnsTrue() {
        Name name1 = new Name("Conference");
        Name name2 = new Name("Conference");
        assertTrue(name1.equals(name2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Name name1 = new Name("Conference");
        Name name2 = new Name("Workshop");
        assertFalse(name1.equals(name2));
    }
}

