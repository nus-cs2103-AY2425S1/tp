package seedu.address.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TutorialTest {

    private Tutorial tutorial1 = new Tutorial("Mathematics");
    private Tutorial tutorial2 = new Tutorial("Mathematics");
    private Tutorial tutorial3 = new Tutorial("Science");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tutorial(null));
    }

    @Test
    public void getSubject_validTutorial_success() {
        assertEquals("Mathematics", tutorial1.getSubject());
    }

    @Test
    public void isSameTutorial_sameSubject_success() {
        assertTrue(tutorial1.isSameTutorial(tutorial2));
    }

    @Test
    public void isSameTutorial_differentSubject_failure() {
        assertFalse(tutorial1.isSameTutorial(tutorial3));
    }

    @Test
    public void isSameTutorial_nullObject_failure() {
        assertFalse(tutorial1.isSameTutorial(null));
    }

    @Test
    public void equals_sameObject_success() {
        assertTrue(tutorial1.equals(tutorial1));
    }

    @Test
    public void equals_differentType_failure() {
        assertFalse(tutorial1.equals("string"));
    }

    @Test
    public void equals_sameSubject_success() {
        assertTrue(tutorial1.equals(tutorial2));
    }

    @Test
    public void equals_differentSubject_failure() {
        assertFalse(tutorial1.equals(tutorial3));
    }

    @Test
    public void hashCode_sameSubject_success() {
        assertEquals(tutorial1.hashCode(), tutorial2.hashCode());
        assertNotEquals(tutorial1.hashCode(), tutorial3.hashCode());
    }

    @Test
    public void toString_validTutorial_success() {
        String expectedString = Tutorial.class.getCanonicalName() + "{subject=Mathematics}";
        assertEquals(expectedString, tutorial1.toString());
    }

}
