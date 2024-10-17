package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECRS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class EmergencyContactTest {
    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.getEmergencyContact().isSamePerson(ALICE.getEmergencyContact()));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        EmergencyContact editedAlice = new EmergencyContact(ALICE.getEmergencyContact().getName(),
                new Phone(VALID_PHONE_BOB),
                new Relationship(VALID_ECRS_BOB));
        assertTrue(ALICE.getEmergencyContact().isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new EmergencyContact(BOB.getName(), ALICE.getEmergencyContact().getPhone(),
                ALICE.getEmergencyContact().getRelationship());
        assertFalse(ALICE.getEmergencyContact().isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        EmergencyContact editedBob = new EmergencyContact(
                new Name(BOB.getEmergencyContact().getName().fullName.toLowerCase()),
                BOB.getEmergencyContact().getPhone(),
                BOB.getEmergencyContact().getRelationship());
        assertFalse(BOB.getEmergencyContact().isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_ECNAME_BOB + " ";
        editedBob = new EmergencyContact(new Name(nameWithTrailingSpaces),
                BOB.getEmergencyContact().getPhone(),
                BOB.getEmergencyContact().getRelationship());
        assertFalse(BOB.getEmergencyContact().isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        EmergencyContact aliceEmergencyContactCopy = new EmergencyContact(
                ALICE.getEmergencyContact().getName(), ALICE.getEmergencyContact().getPhone(),
                ALICE.getEmergencyContact().getRelationship());
        assertTrue(ALICE.getEmergencyContact().equals(aliceEmergencyContactCopy));

        // same object -> returns true
        assertTrue(ALICE.getEmergencyContact().equals(ALICE.getEmergencyContact()));

        // null -> returns false
        assertFalse(ALICE.getEmergencyContact().equals(null));

        // different type -> returns false
        assertFalse(ALICE.getEmergencyContact().equals(5));

        // different person -> returns false
        assertFalse(ALICE.getEmergencyContact().equals(BOB.getEmergencyContact()));

        // different name -> returns false
        EmergencyContact editedAliceEmergencyContact = new EmergencyContact(
                BOB.getEmergencyContact().getName(),
                ALICE.getEmergencyContact().getPhone(),
                ALICE.getEmergencyContact().getRelationship()
        );
        assertFalse(ALICE.equals(editedAliceEmergencyContact));

        // different phone -> returns false
        editedAliceEmergencyContact = new EmergencyContact(
                ALICE.getEmergencyContact().getName(),
                BOB.getEmergencyContact().getPhone(),
                ALICE.getEmergencyContact().getRelationship()
        );
        assertFalse(ALICE.equals(editedAliceEmergencyContact));

        // different relationship -> returns false
        editedAliceEmergencyContact = new EmergencyContact(
                ALICE.getEmergencyContact().getName(),
                ALICE.getEmergencyContact().getPhone(),
                BOB.getEmergencyContact().getRelationship()
        );
        assertFalse(ALICE.equals(editedAliceEmergencyContact));

    }
}
