package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_S4_NT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StudentBuilder;

public class StudentTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Student student = new StudentBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> student.getSubjects().remove(0));
    }

    @Test
    public void isSameStudent() {
        // same object -> returns true
        assertTrue(ALICE.isSameStudent(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameStudent(null));

        // same name, all other attributes different -> returns true
        Student updatedAlice = new StudentBuilder(ALICE).withPhone(VALID_PHONE_BOB)
                .withAddress(VALID_ADDRESS_BOB).withSubjects(VALID_SUBJECT_MATH).build();
        assertTrue(ALICE.isSameStudent(updatedAlice));

        // different name, all other attributes same -> returns false
        updatedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameStudent(updatedAlice));

        // name differs in case, all other attributes same -> returns true
        Student updatedBob = new StudentBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSameStudent(updatedBob));

        // name differs in multi-space and single space, all other attributes same -> returns true
        updatedBob = new StudentBuilder(BOB).withName("Bob  Choo").build();
        assertTrue(BOB.isSameStudent(updatedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        updatedBob = new StudentBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSameStudent(updatedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student aliceCopy = new StudentBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different student -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Student updatedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(updatedAlice));

        // different phone -> returns false
        updatedAlice = new StudentBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(updatedAlice));

        // different address -> returns false
        updatedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(updatedAlice));

        // different emergency contact -> returns false
        updatedAlice = new StudentBuilder(ALICE).withEmergencyContact("98887777").build();
        assertFalse(ALICE.equals(updatedAlice));

        // different note -> returns false
        updatedAlice = new StudentBuilder(ALICE).withNote("on skibidi").build();
        assertFalse(ALICE.equals(updatedAlice));

        // different level -> returns false
        updatedAlice = new StudentBuilder(ALICE).withLevel(VALID_LEVEL_S4_NT).build();
        assertFalse(ALICE.equals(updatedAlice));

        // different lesson time -> returns false
        updatedAlice = new StudentBuilder(ALICE).withLessonTimes(VALID_LESSON_TIME).build();
        assertFalse(ALICE.equals(updatedAlice));

        // different subjects -> returns false
        updatedAlice = new StudentBuilder(ALICE).withSubjects(VALID_SUBJECT_MATH).build();
        assertFalse(ALICE.equals(updatedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Student.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", emergency contact=" + ALICE.getEmergencyContact()
                + ", address=" + ALICE.getAddress() + ", note=" + ALICE.getNote()
                + ", subjects=" + ALICE.getSubjects() + ", level=" + ALICE.getLevel()
                + ", task list=" + ALICE.getTaskList()
                + ", lesson times=" + ALICE.getLessonTimes() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
