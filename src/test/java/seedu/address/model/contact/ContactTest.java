package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_PRESIDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_HANDLE_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BOB;
import static seedu.address.testutil.TypicalContacts.BOBNICK;
import static seedu.address.testutil.TypicalContacts.BOB_HASSAMEEMAIL_ALICE;
import static seedu.address.testutil.TypicalContacts.BOB_HASSAMENICK_BOBNICK;
import static seedu.address.testutil.TypicalContacts.BOB_HASSAMETELE_ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ContactBuilder;

public class ContactTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Contact contact = new ContactBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> contact.getRoles().remove(0));
    }

    @Test
    public void isSameContact() {
        // same object -> returns true
        assertTrue(ALICE.isSameContact(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameContact(null));

        // same name, all other attributes different -> returns true
        Contact editedAlice = new ContactBuilder(ALICE).withTelegramHandle(VALID_TELEGRAM_HANDLE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withStudentStatus(VALID_STUDENT_STATUS_BOB).withRoles(VALID_ROLE_PRESIDENT).build();
        assertTrue(ALICE.isSameContact(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new ContactBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameContact(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Contact editedBob = new ContactBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameContact(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new ContactBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameContact(editedBob));
    }


    @Test
    public void hasSameFields() {
        // Summary of the below tests, testing Contacts where all the traits are different but
        // - same telegram handle
        // - same email
        // - same (nick + name)

        // same object -> returns true
        assertTrue(ALICE.hasSameFields(ALICE));

        // null -> returns false
        assertFalse(ALICE.hasSameFields(null));

        // different fields for (name + nickname), tele handle, email -> false
        assertFalse(ALICE.hasSameFields(BOB));

        // Both of the following Bob's have exactly one conflicting field with Alice
        // same tele -> true ; same email -> true
        assertTrue(BOB_HASSAMETELE_ALICE.hasSameFields(ALICE));
        assertTrue(BOB_HASSAMEEMAIL_ALICE.hasSameFields(ALICE));

        // one is BOB and one is BOBNICK
        // same name + different nick -> false
        assertFalse(BOB.hasSameFields(BOBNICK));

        // same (name + nick) -> true // the bob's below have different email and tele
        assertTrue(BOBNICK.hasSameFields(BOB_HASSAMENICK_BOBNICK));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Contact aliceCopy = new ContactBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different contact -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Contact editedAlice = new ContactBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different telegram -> returns false
        editedAlice = new ContactBuilder(ALICE).withTelegramHandle(VALID_TELEGRAM_HANDLE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ContactBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different student status -> returns false
        editedAlice = new ContactBuilder(ALICE).withStudentStatus(VALID_STUDENT_STATUS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ContactBuilder(ALICE).withRoles(VALID_ROLE_PRESIDENT).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Contact.class.getCanonicalName() + "{name=" + ALICE.getName() + ", telegram handle="
                + ALICE.getTelegramHandle() + ", email=" + ALICE.getEmail()
                + ", studentStatus=" + ALICE.getStudentStatus()
                + ", roles=" + ALICE.getRoles() + ", nickname=" + ALICE.getNickname() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
