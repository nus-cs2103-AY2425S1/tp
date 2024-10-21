package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_ONE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND).withTutorials(VALID_TUTORIAL_ONE).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same student ID, all other attributes different -> returns true
        Person editedBob = new PersonBuilder(ALICE).withStudentId(VALID_STUDENTID_BOB).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // same email, all other attributes different -> returns true
        editedBob = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // same phone number, all other attributes different -> returns true
        editedBob = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // different name and student ID, all other attributes same -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withStudentId(VALID_STUDENTID_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name and email, all other attributes same -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name and phone, all other attributes same -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different student ID and email, all other attributes same -> returns true
        editedAlice = new PersonBuilder(ALICE).withStudentId(VALID_STUDENTID_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different student ID and phone number, all other attributes same -> returns true
        editedAlice = new PersonBuilder(ALICE).withStudentId(VALID_STUDENTID_BOB).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, student ID, phone number and email, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withStudentId(VALID_STUDENTID_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, different student ID, phone number and email
        // all other attributes same -> returns true
        editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).withStudentId(VALID_STUDENTID_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, different student ID, phone number and email
        // all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).withStudentId(VALID_STUDENTID_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).build();
        assertTrue(BOB.isSamePerson(editedBob));
    }

    @Test
    public void correctlyReturnsCssClass() {
        Person alice = new PersonBuilder(ALICE).build();

        // Alice currently has no tutorials attended, should return NOT_TAKEN_PLACE
        assertEquals("tutorial-not-taken-place", alice.getAttendanceCssClass("1"));

        // Set Alice to have attended tutorial 1, 3.
        alice = new PersonBuilder(ALICE).withTutorials(new String[] {"1", "3"},
                new AttendanceStatus[] {AttendanceStatus.PRESENT, AttendanceStatus.PRESENT}).build();
        assertEquals("tutorial-present", alice.getAttendanceCssClass("1"));
        assertEquals("tutorial-present", alice.getAttendanceCssClass("3"));

        // Set Alice to have not attended tutorial 2 and 4.
        alice = new PersonBuilder(ALICE).withTutorials(new String[] {"2", "4"},
                new AttendanceStatus[] {AttendanceStatus.ABSENT, AttendanceStatus.ABSENT}).build();
        assertEquals("tutorial-absent", alice.getAttendanceCssClass("2"));
        assertEquals("tutorial-absent", alice.getAttendanceCssClass("4"));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different student ID -> returns false
        editedAlice = new PersonBuilder(ALICE).withStudentId(VALID_STUDENTID_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tutorials -> returns false
        editedAlice = new PersonBuilder(ALICE).withTutorials(VALID_TUTORIAL_ONE).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", studentID="
                + ALICE.getStudentId() + ", phone=" + ALICE.getPhone() + ", email=" + ALICE.getEmail()
                + ", tags=" + ALICE.getTags() + ", tutorials=" + ALICE.getTutorials() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
