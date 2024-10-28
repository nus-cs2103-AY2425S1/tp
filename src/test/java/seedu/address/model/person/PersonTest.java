package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.TEACHER_ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TeacherBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(TEACHER_ALICE.isSamePerson(TEACHER_ALICE));

        // null -> returns false
        assertFalse(TEACHER_ALICE.isSamePerson(null));

        // same name and email, all other attributes different -> returns true
        Teacher editedAlice = new TeacherBuilder(TEACHER_ALICE).withPhone(VALID_PHONE_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(TEACHER_ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new TeacherBuilder(TEACHER_ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(TEACHER_ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Teacher aliceCopy = new TeacherBuilder(TEACHER_ALICE).build();
        assertTrue(TEACHER_ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(TEACHER_ALICE.equals(TEACHER_ALICE));

        // null -> returns false
        assertFalse(TEACHER_ALICE.equals(null));

        // different type -> returns false
        assertFalse(TEACHER_ALICE.equals(5));

        // different person -> returns false
        assertFalse(TEACHER_ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(TEACHER_ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(TEACHER_ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(TEACHER_ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(TEACHER_ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(TEACHER_ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(TEACHER_ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(TEACHER_ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(TEACHER_ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(TEACHER_ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(TEACHER_ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Teacher.class.getCanonicalName() + "{name=" + TEACHER_ALICE.getName()
            + ", gender=" + TEACHER_ALICE.getGender() + ", phone=" + TEACHER_ALICE.getPhone()
            + ", email=" + TEACHER_ALICE.getEmail()
            + ", address=" + TEACHER_ALICE.getAddress() + ", tags=" + TEACHER_ALICE.getTags()
            + ", subject=" + TEACHER_ALICE.getSubjects()
            + ", classes=" + TEACHER_ALICE.getClasses() + "}";
        assertEquals(expected, TEACHER_ALICE.toString());
    }
}
