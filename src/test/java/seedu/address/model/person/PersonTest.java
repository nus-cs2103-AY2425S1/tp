package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALL_FREQUENCY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.contactrecord.ContactRecord;
import seedu.address.testutil.ContactRecordBuilder;
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

        // same nric, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .withCallFrequency(VALID_CALL_FREQUENCY_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different nric, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withNric(VALID_NRIC_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // nric differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NRIC_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // nric has trailing spaces, all other attributes same -> returns true
        String nricWithTrailingSpaces = VALID_NRIC_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nricWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));
    }

    @Test
    public void isSimilarPerson() {
        // same object -> returns true
        assertTrue(ALICE.isSimilarPerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSimilarPerson(null));

        // same nric, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .withCallFrequency(VALID_CALL_FREQUENCY_BOB).build();
        assertTrue(ALICE.isSimilarPerson(editedAlice));

        // same name, all other attributes different -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(BOB.isSimilarPerson(editedAlice));

        // same name lowercase, all other attributes different -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSimilarPerson(editedAlice));

        // same phone, all other attributes different -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(BOB.isSimilarPerson(editedAlice));

        // same email, all other attributes different -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(BOB.isSimilarPerson(editedAlice));

        // same email lowercase, all other attributes different -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB.toLowerCase()).build();
        assertTrue(BOB.isSimilarPerson(editedAlice));

        // same name, phone, email, all other attributes different -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).build();
        assertTrue(BOB.isSimilarPerson(editedAlice));

        // different nric, name, phone, email, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withNric(VALID_NRIC_BOB).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSimilarPerson(editedAlice));

        // all different attributes -> returns false
        editedAlice = new PersonBuilder(ALICE).withNric(VALID_NRIC_BOB).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withCallFrequency(VALID_CALL_FREQUENCY_BOB).build();
        assertFalse(ALICE.isSimilarPerson(editedAlice));
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

        // different nric -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withNric(VALID_NRIC_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
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

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void markAsContacted() {
        Person person = new PersonBuilder().build();
        ContactRecord contactRecord = new ContactRecordBuilder().build();
        person.markAsContacted(contactRecord);
        // 1 contact record added on initialisation and another added by markAsContacted
        assertEquals(2, person.getContactRecords().size());
    }

    @Test
    public void getLastContacted() {
        Person person = new PersonBuilder().build();
        assertEquals(person.getContactRecords().getLastContacted(), person.getLastContacted());
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{nric=" + ALICE.getNric()
                + ", name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", tags=" + ALICE.getTags() + ", call frequency=" + ALICE.getCallFrequency() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
