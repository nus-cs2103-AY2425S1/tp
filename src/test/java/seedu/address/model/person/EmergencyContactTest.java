package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECRS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

public class EmergencyContactTest {
    private static final EmergencyContact ALICE_EMERGENCY_CONTACT = ALICE.getFirstEmergencyContact();
    private static final EmergencyContact BOB_EMERGENCY_CONTACT = BOB.getFirstEmergencyContact();
    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE_EMERGENCY_CONTACT.isSamePerson(ALICE_EMERGENCY_CONTACT));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        EmergencyContact editedAlice = new EmergencyContact(ALICE_EMERGENCY_CONTACT.getName(),
                new Phone(VALID_PHONE_BOB),
                new Relationship(VALID_ECRS_BOB));
        assertTrue(ALICE_EMERGENCY_CONTACT.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new EmergencyContact(BOB.getName(), ALICE_EMERGENCY_CONTACT.getPhone(),
                ALICE_EMERGENCY_CONTACT.getRelationship());
        assertFalse(ALICE_EMERGENCY_CONTACT.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        EmergencyContact editedBob = new EmergencyContact(
                new Name(BOB_EMERGENCY_CONTACT.getName().fullName.toLowerCase()),
                BOB_EMERGENCY_CONTACT.getPhone(),
                BOB_EMERGENCY_CONTACT.getRelationship());
        assertFalse(BOB_EMERGENCY_CONTACT.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_ECNAME_BOB + " ";
        editedBob = new EmergencyContact(new Name(nameWithTrailingSpaces),
                BOB_EMERGENCY_CONTACT.getPhone(),
                BOB_EMERGENCY_CONTACT.getRelationship());
        assertFalse(BOB_EMERGENCY_CONTACT.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        EmergencyContact aliceEmergencyContactCopy = new EmergencyContact(
                ALICE_EMERGENCY_CONTACT.getName(), ALICE_EMERGENCY_CONTACT.getPhone(),
                ALICE_EMERGENCY_CONTACT.getRelationship());
        assertTrue(ALICE_EMERGENCY_CONTACT.equals(aliceEmergencyContactCopy));

        // same object -> returns true
        assertTrue(ALICE_EMERGENCY_CONTACT.equals(ALICE_EMERGENCY_CONTACT));

        // null -> returns false
        assertFalse(ALICE_EMERGENCY_CONTACT.equals(null));

        // different type -> returns false
        assertFalse(ALICE_EMERGENCY_CONTACT.equals(5));

        // different person -> returns false
        assertFalse(ALICE_EMERGENCY_CONTACT.equals(BOB_EMERGENCY_CONTACT));

        // different name -> returns false
        EmergencyContact editedAliceEmergencyContact = new EmergencyContact(
                BOB_EMERGENCY_CONTACT.getName(),
                ALICE_EMERGENCY_CONTACT.getPhone(),
                ALICE_EMERGENCY_CONTACT.getRelationship()
        );
        assertFalse(ALICE.equals(editedAliceEmergencyContact));

        // different phone -> returns false
        editedAliceEmergencyContact = new EmergencyContact(
                ALICE_EMERGENCY_CONTACT.getName(),
                BOB_EMERGENCY_CONTACT.getPhone(),
                ALICE_EMERGENCY_CONTACT.getRelationship()
        );
        assertFalse(ALICE.equals(editedAliceEmergencyContact));

        // different relationship -> returns false
        editedAliceEmergencyContact = new EmergencyContact(
                ALICE_EMERGENCY_CONTACT.getName(),
                ALICE_EMERGENCY_CONTACT.getPhone(),
                BOB_EMERGENCY_CONTACT.getRelationship()
        );
        assertFalse(ALICE.equals(editedAliceEmergencyContact));

    }
}
