package seedu.address.model.student;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class
TutorialGroupTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TutorialGroup(null));
    }

    @Test
    public void constructor_invalidTutorialGroup_throwsIllegalArgumentException() {
        String invalidTutorialGroup = "";
        assertThrows(IllegalArgumentException.class, () -> new TutorialGroup(invalidTutorialGroup));
    }

    @Test
    public void isValidTutorialGroup() {
        // null tutorial group
        assertThrows(NullPointerException.class, () -> TutorialGroup.isValidTutorialGroup(null));

        // invalid tutorial group
        assertFalse(TutorialGroup.isValidTutorialGroup("")); // empty string
        assertFalse(TutorialGroup.isValidTutorialGroup(" ")); // spaces only
        assertFalse(TutorialGroup.isValidTutorialGroup("Ggg")); // only alphabets
        assertFalse(TutorialGroup.isValidTutorialGroup("123")); // only numbers
        assertFalse(TutorialGroup.isValidTutorialGroup("G123")); // more than 2 numbers

        // valid tutorial group
        assertTrue(TutorialGroup.isValidTutorialGroup("G01")); // starts with capital letter
        assertTrue(TutorialGroup.isValidTutorialGroup("g01")); // starts with non-capital letter
    }

    @Test
    public void equals() {
        TutorialGroup tutorialGroup = new TutorialGroup("G01");

        // same values -> returns true
        assertTrue(tutorialGroup.equals(new TutorialGroup("G01")));

        // same object -> returns true
        assertTrue(tutorialGroup.equals(tutorialGroup));

        // null -> returns false
        assertFalse(tutorialGroup.equals(null));

        // different types -> returns false
        assertFalse(tutorialGroup.equals(5.0f));

        // different values -> returns false
        assertFalse(tutorialGroup.equals(new TutorialGroup("G02")));
    }
}
