package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_P;
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
        assertFalse(ALICE.isSamePerson((PersonDescriptor) null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB).withStatus(VALID_STATUS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

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
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertEquals(ALICE_P, aliceCopy);

        // same object -> returns true
        assertEquals(ALICE_P, ALICE_P);

        // null -> returns false
        assertNotEquals(null, ALICE_P);

        // different type -> returns false
        assertNotEquals(5, ALICE_P);

        // different person -> returns false
        assertNotEquals(ALICE_P, BOB);

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE_P).withName(VALID_NAME_BOB).build();
        assertNotEquals(ALICE_P, editedAlice);

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE_P).withPhone(VALID_PHONE_BOB).build();
        assertNotEquals(ALICE_P, editedAlice);

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertNotEquals(ALICE_P, editedAlice);

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE_P).withAddress(VALID_ADDRESS_BOB).build();
        assertNotEquals(ALICE_P, editedAlice);

        // different status -> returns false
        editedAlice = new PersonBuilder(ALICE_P).withStatus(VALID_STATUS_BOB).build();
        assertNotEquals(ALICE_P, editedAlice);

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE_P).withTags(VALID_TAG_HUSBAND).build();
        assertNotEquals(ALICE_P, editedAlice);
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{personId=" + ALICE_P.getPersonId()
            + ", name=" + ALICE_P.getName() + ", phone=" + ALICE_P.getPhone()
            + ", email=" + ALICE_P.getEmail() + ", address=" + ALICE_P.getAddress() + ", status="
            + ALICE_P.getStatus() + ", tags=" + ALICE_P.getTags() + "}";
        assertEquals(expected, ALICE_P.toString());
    }
}
