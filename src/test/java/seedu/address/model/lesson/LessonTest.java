package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CLARA;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Subject;

/**
 * Contains tests for the Lesson class.
 */
public class LessonTest {

    /**
     * Tests the constructor of the Lesson class.
     */
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(null, null, null));
    }

    /**
     * Tests the equals method of the Lesson class.
     */
    @Test
    public void equals() {
        // valid lesson
        Lesson testDefault = new Lesson(ALICE, DANIEL, new Subject("Math"));

        // same object -> returns true
        assertTrue(testDefault.equals(testDefault));

        // same lesson
        Lesson copy = new Lesson(ALICE, DANIEL, new Subject("Math"));
        assertTrue(testDefault.equals(copy));

        // different tutor -> returns false
        Lesson differentTutor = new Lesson(BENSON, CLARA, new Subject("English"));
        assertFalse(testDefault.equals(differentTutor));

        // different tutee -> returns false
        Lesson differentTutee = new Lesson(ALICE, CLARA, new Subject("English"));
        assertFalse(testDefault.equals(differentTutee));

        // different subject -> returns false
        Lesson differentSubject = new Lesson(ALICE, DANIEL, new Subject("English"));
        assertFalse(testDefault.equals(differentSubject));

        // different lesson -> returns false
        assertFalse(testDefault.equals(null));

        // different type -> returns false
        assertFalse(testDefault.equals(5));
    }

    /**
     * Tests the toString method of the Lesson class.
     */
    @Test
    public void toStringTest() {
        Lesson testDefault = new Lesson(ALICE, DANIEL, new Subject("Math"));
        String expectedMessage = "Lesson: tutor Alice Pauline is teaching tutee Daniel Meier Math";
        assertEquals(expectedMessage, testDefault.toString());
    }
}
