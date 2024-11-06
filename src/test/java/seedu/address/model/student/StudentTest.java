package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.JASON;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class StudentTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Student student = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> student.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));
        assertTrue(JASON.isSamePerson(JASON));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns false
        // because email is different
        Student editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
            .withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different student number, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withStudentNumber(VALID_STUDENT_NUMBER_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        // true because StudentNumber is same
        Student editedBob = new PersonBuilder(BOB).withName(VALID_NAME_AMY.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // student name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student aliceCopy = new PersonBuilder(ALICE).build();
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
        Student editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected =
            Student.class.getCanonicalName() + "{name=" + ALICE.getName() + ", email=" + ALICE.getEmail()
                + ", tags=" + ALICE.getTags() + ", student number=" + ALICE.getStudentNumber()
                + ", group=" + ALICE.getGroupName().orElse(null) + "}";
        assertEquals(expected, ALICE.toString());
    }
}
