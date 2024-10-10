package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TutorialTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tutorial(null));
    }

    @Test
    public void constructor_invalidTutorial_throwsIllegalArgumentException() {
        String invalidTagName = "-1";
        assertThrows(IllegalArgumentException.class, () -> new Tutorial(invalidTagName));
    }

    @Test
    public void isValidTutorialName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tutorial.isValidTutorial(null));
    }

    @Test
    public void equals() {
        // is itself
        Tutorial tutorial = new Tutorial("1");

        assertTrue(tutorial.equals(tutorial));

        // is null
        assertFalse(tutorial.equals(null));

        // is some other attribute
        assertFalse(tutorial.equals(new Name("Name name")));

        // different instance, same tutorial
        Tutorial duplicateTutorial = new Tutorial("1");
        assertTrue(tutorial.equals(duplicateTutorial));
    }
}
