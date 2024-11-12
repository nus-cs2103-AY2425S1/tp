package seedu.address.model.tut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TutNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TutName(null));
    }

    @Test
    public void constructor_invalidTutName_throwsIllegalArgumentException() {
        String invalidTutName = " ";
        assertThrows(IllegalArgumentException.class, () -> new TutName(invalidTutName));
    }

    @Test
    public void constructor_validTutName_success() {
        String validTutName = "CS1010";
        TutName tutName = new TutName(validTutName);
        assertEquals(validTutName, tutName.tutName);
    }

    @Test
    public void isValidTutName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> TutName.isValidTutName(null));
    }

    @Test
    public void isValidTutName_invalidTutName_false() {
        // Empty string
        assertFalse(TutName.isValidTutName(""));

        // Spaces only
        assertFalse(TutName.isValidTutName(" "));

        // Non-alphanumeric characters
        assertFalse(TutName.isValidTutName("CS@123"));

        // Leading space
        assertFalse(TutName.isValidTutName(" CS1010"));

        // Trailing space
        assertFalse(TutName.isValidTutName("CS1010 "));
    }

    @Test
    public void isValidTutName_validTutName_true() {
        // Alphanumeric characters only
        assertTrue(TutName.isValidTutName("CS1010"));

        // Alphanumeric characters with spaces
        assertTrue(TutName.isValidTutName("CS1010 Introduction"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        TutName tutName = new TutName("CS1010");
        assertEquals(tutName, tutName);
    }

    @Test
    public void equals_differentObjectWithSameName_returnsTrue() {
        TutName tutName1 = new TutName("CS1010");
        TutName tutName2 = new TutName("CS1010");
        assertEquals(tutName1, tutName2);
    }

    @Test
    public void equals_differentObjectWithDifferentName_returnsFalse() {
        TutName tutName1 = new TutName("CS1010");
        TutName tutName2 = new TutName("CS2020");
        assertNotEquals(tutName1, tutName2);
    }

    @Test
    public void hashCode_sameObject_returnsSameHashCode() {
        TutName tutName = new TutName("CS1010");
        assertEquals(tutName.hashCode(), tutName.hashCode());
    }

    @Test
    public void hashCode_differentObjectWithSameName_returnsSameHashCode() {
        TutName tutName1 = new TutName("CS1010");
        TutName tutName2 = new TutName("CS1010");
        assertEquals(tutName1.hashCode(), tutName2.hashCode());
    }
}

