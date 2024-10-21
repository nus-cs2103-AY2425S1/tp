package seedu.academyassist.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_IC_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_YEAR_GROUP_BOB;
import static seedu.academyassist.testutil.Assert.assertThrows;
import static seedu.academyassist.testutil.TypicalPersons.ALICE;
import static seedu.academyassist.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.academyassist.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getSubjects().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // todo change the following test cases
        // same student id, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withIc(VALID_IC_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different student id, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withStudentId(VALID_STUDENT_ID_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));
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

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different subjects -> returns false
        editedAlice = new PersonBuilder(ALICE).withSubjects(VALID_SUBJECT_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different year group -> returns false
        editedAlice = new PersonBuilder(ALICE).withSubjects(VALID_YEAR_GROUP_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different ic -> returns false
        editedAlice = new PersonBuilder(ALICE).withSubjects(VALID_IC_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", ic=" + ALICE.getIc()
                + ", year group=" + ALICE.getYearGroup() + ", studentId=" + ALICE.getStudentId() + ", subjects="
                + ALICE.getSubjects() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
