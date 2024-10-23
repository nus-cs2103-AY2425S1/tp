package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription1 = "Event@123"; // Invalid case: special character "@"
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription1));
        String invalidDescription3 = "Event123."; // Invalid case: special character "."
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription3));
        String invalidDescription4 = "Happening soon, see you there"; // Invalid case: special character ","
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription4));
        String invalidDescription2 = "Collecting unsold food from NTUC for distribution Collecting unsold food from "
                                     + "NTUC for distribution a"; // Invalid case: Exceeds 100 char (101)
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription2));

    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid description
        assertFalse(Description.isValidDescription("@TestEvent")); // special characters
        assertFalse(Description.isValidDescription("Event@123")); // contains special character
        assertFalse(Description.isValidDescription("123@456!")); // multiple special characters

        // valid description
        assertTrue(Description.isValidDescription("")); // empty string
        assertTrue(Description.isValidDescription(" ")); // spaces only
        assertTrue(Description.isValidDescription("Birthday Party")); // alphanumeric with spaces
        assertTrue(Description.isValidDescription("Event 2024")); // alphanumeric with numbers
        assertTrue(Description.isValidDescription("SimpleEvent")); // alphanumeric, no spaces
        assertTrue(Description.isValidDescription("Collecting unsold food from NTUC for distribution "
                                                  + "Collecting unsold food from NTUC for distribution")); // 100 char
    }

    @Test
    public void equals() {
        Description desc1 = new Description("Birthday Party");
        Description desc2 = new Description("Birthday Party");
        Description desc3 = new Description("Conference Event");

        // same values -> returns true
        assertTrue(desc1.equals(desc2));

        // same object -> returns true
        assertTrue(desc1.equals(desc1));

        // null -> returns false
        assertFalse(desc1.equals(null));

        // different types -> returns false
        assertFalse(desc1.equals(5.0f));

        // different values -> returns false
        assertFalse(desc1.equals(desc3));
    }

    @Test
    public void toString_returnsFormattedDescription() {
        Description description = new Description("Workshop 2024 Join us there");
        assertTrue(description.toString().equals("Workshop 2024 Join us there"));
    }

    @Test
    public void hashCode_sameDescription_sameHashCode() {
        Description desc1 = new Description("Hackathon Event hosted by Amazon");
        Description desc2 = new Description("Hackathon Event hosted by Amazon");

        assertTrue(desc1.hashCode() == desc2.hashCode());
    }
}
