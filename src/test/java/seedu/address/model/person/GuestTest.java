package seedu.address.model.person;


import org.junit.jupiter.api.Test;
import seedu.address.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalPersons.AVA;
import static seedu.address.testutil.TypicalPersons.BRIAN;


public class GuestTest {
    private final Guest GUEST_AVA = (Guest) AVA;
    private final Guest GUEST_BRIAN = (Guest) BRIAN;

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(AVA.isSamePerson(AVA));

        // null -> returns false
        assertFalse(AVA.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(AVA).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(AVA.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(AVA).withName(VALID_NAME_BOB).build();
        assertFalse(AVA.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBrian = new PersonBuilder(BRIAN).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BRIAN.isSamePerson(editedBrian));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBrian = new PersonBuilder(BRIAN).withName(nameWithTrailingSpaces).build();
        assertFalse(BRIAN.isSamePerson(editedBrian));

        // name has trailing spaces, all other attributes same -> returns false
        editedBrian = new PersonBuilder(BRIAN).withRsvp(VALID_RSVP_ACCEPTED).build();
        assertFalse(BRIAN.isSamePerson(editedBrian));
        
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(AVA).build();
        assertTrue(AVA.equals(aliceCopy));

        // same object -> returns true
        assertTrue(AVA.equals(AVA));

        // null -> returns false
        assertFalse(AVA.equals(null));

        // different type -> returns false
        assertFalse(AVA.equals(5));

        // different person -> returns false
        assertFalse(AVA.equals(BRIAN));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(AVA).withName(VALID_NAME_BOB).build();
        assertFalse(AVA.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(AVA).withPhone(VALID_NAME_BOB).build();
        assertFalse(AVA.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(AVA).withEmail(VALID_NAME_BOB).build();
        assertFalse(AVA.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(AVA).withAddress(VALID_NAME_BOB).build();
        assertFalse(AVA.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(AVA).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(AVA.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(AVA).withTags(VALID_RSVP_PENDING).build();
        assertFalse(AVA.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + AVA.getName() + ", phone=" + AVA.getPhone()
                + ", email=" + AVA.getEmail() + ", address=" + AVA.getAddress() + ", tags=" + AVA.getTags() + "}";
        assertEquals(expected, AVA.toString());
    }

}
