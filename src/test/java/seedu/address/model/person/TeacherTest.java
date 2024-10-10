package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTeachers.ALICE;
import static seedu.address.testutil.TypicalTeachers.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TeacherBuilder;

public class TeacherTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Teacher teacher = new TeacherBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> teacher.getTags().remove(0));
    }

    @Test
    public void isSameTeacher() {
        // same object -> returns true
        assertTrue(ALICE.isSameTeacher(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameTeacher(null));

        // different phone and email -> returns true
        Teacher editedAlice = new TeacherBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(ALICE.isSameTeacher(editedAlice));

        // different name -> returns false
        editedAlice = new TeacherBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameTeacher(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new TeacherBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withGender(VALID_GENDER_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameTeacher(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new TeacherBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
            .withGender(VALID_GENDER_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameTeacher(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Teacher aliceCopy = new TeacherBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different teacher -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Teacher editedAlice = new TeacherBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new TeacherBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new TeacherBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new TeacherBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different gender -> returns false
        editedAlice = new TeacherBuilder(ALICE).withGender(VALID_GENDER_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new TeacherBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
