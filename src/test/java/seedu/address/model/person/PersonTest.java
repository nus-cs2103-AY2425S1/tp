package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_ATTENDEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_VENDOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.exceptions.InvalidRoleException;
import seedu.address.testutil.PersonBuilder;
import seedu.address.ui.Observer;

public class PersonTest {
    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTelegramUsername(VALID_TELEGRAM_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // same phone, all other attributes different -> returns true
        Person editedAlice2 = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTelegramUsername(VALID_TELEGRAM_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice2));

        // same email, all other attributes different -> return true
        Person editedAlice3 = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTelegramUsername(VALID_TELEGRAM_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice3));

        // same telegram username, all other attributes different -> return true
        Person editedAlice4 = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice4));

        // telegram username differs in case, all other attributes different -> return true
        Person editedAlice5 = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB)
                .withTelegramUsername(ALICE.getTelegramUsername().toString().toUpperCase()).build();
        assertTrue(ALICE.isSamePerson(editedAlice5));

        // no telegram username, all attributes different -> return false
        Person editedAlice6 = new PersonBuilder(ALICE).withTelegramUsername(null).build();
        Person editedBob2 = new PersonBuilder(BOB).withTelegramUsername(null).build();
        assertFalse(editedBob2.isSamePerson(editedAlice6));

        // no telegram username, all attributes same -> return true
        Person editedBob3 = new PersonBuilder(BOB).withTelegramUsername(null).build();
        assertTrue(editedBob2.isSamePerson(editedBob3));

    }

    @Test
    public void isSamePerson_differentConstructor() {
        Person person = new PersonBuilder().build();
        Person personCopy = new PersonBuilder(person).build();
        assertTrue(person.isSamePerson(personCopy));
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



        editedAlice = new PersonBuilder(ALICE).withRoles(VALID_ROLE_VENDOR).build();
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new PersonBuilder(ALICE).withTelegramUsername("al1ice").build();
        assertFalse(ALICE.equals(editedAlice));

    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", telegram=" + ALICE.getTelegramUsername()
                + ", roles=" + ALICE.getRoles() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void compareTo_lowerLexicographicalOrder() {
        Person aliceCopy = new PersonBuilder(ALICE).build();
        Person bobCopy = new PersonBuilder(BOB).build();
        assertTrue(ALICE.compareTo(BOB) < 0);
    }

    @Test
    public void compareTo_higherLexicographicalOrder() {
        Person aliceCopy = new PersonBuilder(ALICE).build();
        Person bobCopy = new PersonBuilder(BOB).build();
        assertTrue(BOB.compareTo(ALICE) > 0);
    }

    @Test
    public void getRoles_oneRole() {
        Person person = new PersonBuilder().withRoles(VALID_ROLE_ATTENDEE).build();
        try {
            assertTrue(person.getRoles().contains(RoleHandler.getRole(VALID_ROLE_ATTENDEE)));

        } catch (InvalidRoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void hasRole_oneRole() {
        Person person = new PersonBuilder().withRoles(VALID_ROLE_ATTENDEE).build();
        try {
            assertTrue(person.hasRole(RoleHandler.getRole(VALID_ROLE_ATTENDEE)));

        } catch (InvalidRoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void hasRole_twoRole() {
        Person person = new PersonBuilder().withRoles(VALID_ROLE_ATTENDEE, INVALID_ROLE).build();
        try {
            assertTrue(person.hasRole(RoleHandler.getRole(VALID_ROLE_ATTENDEE)));
            assertTrue(person.hasRole(RoleHandler.getRole(INVALID_ROLE)));

        } catch (InvalidRoleException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void equals_sameRole() {
        Person person = new PersonBuilder().withRoles(VALID_ROLE_ATTENDEE).build();

        Person personCopy = new PersonBuilder().withRoles(VALID_ROLE_ATTENDEE).build();
        assertTrue(person.equals(personCopy));

    }

    @Test
    public void isSameTelegram() {
        // same object -> returns true
        assertTrue(ALICE.isSameTelegram(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameTelegram(null));

        // same telegram, all other attributes different -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(ALICE.isSameTelegram(editedAlice));

        // different telegram, all other attributes same -> returns true
        editedAlice = new PersonBuilder(ALICE).withTelegramUsername(VALID_TELEGRAM_BOB).build();
        assertFalse(ALICE.isSameTelegram(editedAlice));

        // telegram differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withTelegramUsername(VALID_TELEGRAM_BOB.toLowerCase()).build();
        assertTrue(BOB.isSameTelegram(editedBob));

        // telegram has trailing spaces, all other attributes same -> returns true
        String editedTelegram = VALID_TELEGRAM_BOB + "xDD";
        editedBob = new PersonBuilder(BOB).withTelegramUsername(editedTelegram).build();
        assertFalse(BOB.isSameTelegram(editedBob));
    }

    private class DummyObserver implements Observer {
        public void update() {

        }

        public void update(Person person) {

        }
    }
    @Test
    public void testAddObserver() {
        Observer dummyObserver = new PersonTest.DummyObserver();
        Person person = ALICE;
        person.addObserver(dummyObserver);
        assertEquals(person.getObserver(), dummyObserver);
    }
}
