package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.HashSet;

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
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
                .withTelegram(VALID_TELEGRAM_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertEquals(ALICE, aliceCopy);

        // same object -> returns true
        assertEquals(ALICE, ALICE);

        // null -> returns false
        assertNotEquals(null, ALICE);

        // different type -> returns false
        assertNotEquals(5, ALICE);

        // different person -> returns false
        assertNotEquals(ALICE, BOB);

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertNotEquals(ALICE, editedAlice);

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertNotEquals(ALICE, editedAlice);

        // different telegram -> returns false
        editedAlice = new PersonBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB).build();
        assertNotEquals(ALICE, editedAlice);

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertNotEquals(ALICE, editedAlice);

        // different GitHub account -> returns false
        editedAlice = new PersonBuilder(ALICE).withGithub(VALID_GITHUB_BOB).build();
        assertNotEquals(ALICE, editedAlice);
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName()
                + ", email=" + ALICE.getEmail()
                + ", telegram=" + ALICE.getTelegram() + ", tags=" + ALICE.getTags()
                + ", github=" + ALICE.getGithub()
                + ", assignment=" + ALICE.getAssignment()
                + ", weeks present=" + ALICE.getWeeksPresent() + "}";

        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void compareName() {
        Person firstPerson = new PersonBuilder(ALICE).build();
        Person secondPerson = new PersonBuilder(BOB).build();

        // null input
        assertThrows(NullPointerException.class, () -> firstPerson.compareName(null));

        // valid input
        assertTrue(firstPerson.compareName(secondPerson) < 0);
        assertTrue(secondPerson.compareName(firstPerson) > 0);

        // same value
        assertEquals(0, firstPerson.compareName(firstPerson));
    }

    @Test
    public void compareGithub() {
        Person firstPerson = new PersonBuilder(ALICE).build();
        Person secondPerson = new PersonBuilder(BOB).build();

        // null input
        assertThrows(NullPointerException.class, () -> firstPerson.compareGithub(null));

        // valid input
        assertTrue(firstPerson.compareGithub(secondPerson) < 0);
        assertTrue(secondPerson.compareGithub(firstPerson) > 0);

        // same value
        assertEquals(0, firstPerson.compareGithub(firstPerson));
    }

    @Test
    public void compareTelegram() {
        Person firstPerson = new PersonBuilder(ALICE).build();
        Person secondPerson = new PersonBuilder(BOB).build();

        // null input
        assertThrows(NullPointerException.class, () -> firstPerson.compareTelegram(null));

        // valid input
        assertTrue(firstPerson.compareTelegram(secondPerson) < 0);
        assertTrue(secondPerson.compareTelegram(firstPerson) > 0);

        // same value
        assertEquals(0, firstPerson.compareTelegram(firstPerson));
    }

    @Test
    public void testEquals_differentType() {
        Person person = new Person(new Name("John Doe"),
                new Email("john@example.com"), new Telegram("@johndoe"),
                new HashSet<>(), new Github("johndoe123"));

        assertNotEquals(person, new Object());
    }
}
