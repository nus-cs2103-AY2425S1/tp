package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ANDY;
import static seedu.address.testutil.TypicalPersons.BETTY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void testPersonConstructorWithModuleRoleMap() {
        Person andy = new PersonBuilder(ANDY).build();

        Person person = new Person(ANDY.getName(), ANDY.getPhone(), ANDY.getEmail(), ANDY.getAddress(),
                ANDY.getTags(), ANDY.getModuleRoleMap());
        assertNotNull(person, "The person object should not be null");
        assertEquals(andy, person);
    }

    @Test
    public void testPersonConstructorWithoutModuleRoleMap() {
        Person betty = new PersonBuilder(BETTY).build();

        Person person = new Person(BETTY.getName(), BETTY.getPhone(), BETTY.getEmail(),
                Optional.empty(), BETTY.getTags(), BETTY.getModuleRoleMap());

        assertNotNull(person, "The person object should not be null");
        assertEquals(betty, person);
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void testPersonConstructorWithoutAddress() {
        Person betty = new PersonBuilder(BETTY).withEmptyAddress().build();

        Person person = new Person(BETTY.getName(), BETTY.getPhone(), BETTY.getEmail(),
                Optional.empty(), BETTY.getTags(), BETTY.getModuleRoleMap());

        assertNotNull(person, "The person object should not be null");
        assertEquals(betty, person);
    }

    @Test
    public void testHasPhoneWithoutPhone() {
        Person betty = new PersonBuilder(BETTY).withEmptyPhone().build();

        assertFalse(betty.hasPhone());
    }

    @Test
    public void testHasPhoneWithPhone() {
        Person carl = new PersonBuilder(CARL).build();

        assertTrue(carl.hasPhone());
    }

    @Test
    public void testHasEmailWithoutEmail() {
        Person betty = new PersonBuilder(BETTY).withEmptyEmail().build();

        assertFalse(betty.hasEmail());
    }

    @Test
    public void testHasEmailWithEmail() {
        Person carl = new PersonBuilder(CARL).build();

        assertTrue(carl.hasEmail());
    }

    @Test
    public void testHasAddressWithoutAddress() {
        Person betty = new PersonBuilder(BETTY).withEmptyAddress().build();

        assertFalse(betty.hasAddress());
    }

    @Test
    public void testHasAddressWithAddress() {
        Person carl = new PersonBuilder(CARL).build();

        assertTrue(carl.hasAddress());
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same email, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(BOB).withEmail(VALID_EMAIL_ALICE).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same phone, all other attributes different -> returns true
        editedAlice = new PersonBuilder(BOB).withPhone(VALID_PHONE_ALICE).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different email, all other attributes same including phone -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different phone, all other attributes same including email -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different phone, different email, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));
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

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName()
                + "{name=" + ALICE.getName()
                + ", phone=" + ALICE.getPhone().map(Objects ::toString).orElse(null)
                + ", email=" + ALICE.getEmail().map(Objects ::toString).orElse(null)
                + ", address=" + ALICE.getAddress().map(Objects ::toString).orElse(null)
                + ", tags=" + ALICE.getTags()
                + ", roles=" + ALICE.getModuleRoleMap() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
