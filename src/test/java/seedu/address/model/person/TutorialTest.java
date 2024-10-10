package seedu.address.model.person;

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
}
