package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OWED_AMOUNT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    /* seems like it is for tag
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }
    */

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name and phone, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withSchedule(VALID_SCHEDULE_BOB)
                .withSubject(VALID_SUBJECT_BOB).withRate(VALID_RATE_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, all other attributes different -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withSchedule(VALID_SCHEDULE_BOB)
                .withSubject(VALID_SUBJECT_BOB).withRate(VALID_RATE_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));
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

        // different schedule -> returns false
        editedAlice = new PersonBuilder(ALICE).withSchedule(VALID_SCHEDULE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different subject -> returns false
        editedAlice = new PersonBuilder(ALICE).withSubject(VALID_SUBJECT_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different rate -> returns false
        editedAlice = new PersonBuilder(ALICE).withRate(VALID_RATE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different owedAmount -> returns false
        editedAlice = new PersonBuilder(ALICE).withOwedAmount(VALID_OWED_AMOUNT_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

    }

    @Test
    public void isClash_samePerson_returnsFalse() {
        Person person = new PersonBuilder().build();
        assertFalse(person.isClash(person), "A person should not clash with themselves.");
    }

    @Test
    public void isClash_nullPerson_returnsFalse() {
        Person person = new PersonBuilder().build();
        assertFalse(person.isClash(null), "Clashing with a null person should return false.");
    }

    @Test
    public void isClash_noClash_returnsFalse() {
        Person personA = new PersonBuilder().withSchedule("Monday-0900-1100").build();
        Person personB = new PersonBuilder().withSchedule("Monday-1200-1300").build();
        assertFalse(personA.isClash(personB), "Persons should not clash when their schedules do not overlap.");
    }

    @Test
    public void isClash_clash_returnsTrue() {
        Person personA = new PersonBuilder().withSchedule("Monday-0900-1100").build();
        Person personB = new PersonBuilder().withSchedule("Monday-1000-1200").build();
        assertTrue(personA.isClash(personB), "Persons should clash when their schedules overlap.");
    }

    @Test
    public void isClash_partialOverlap_returnsTrue() {
        Person personA = new PersonBuilder().withSchedule("Tuesday-1000-1200").build();
        Person personB = new PersonBuilder().withSchedule("Tuesday-1100-1300").build();
        assertTrue(personA.isClash(personB), "Persons should clash with partially overlapping schedules.");
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", schedule=" + ALICE.getSchedule() + ", subject=" + ALICE.getSubject()
                + ", rate=" + ALICE.getRate() + ", paid=" + ALICE.getPaid()
                + ", owedAmount=" + ALICE.getOwedAmount() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void hashCodeTest() {
        // same attributes -> returns true
        Person sameAlice = new PersonBuilder(ALICE).build();

        assertEquals(ALICE.hashCode(), sameAlice.hashCode());

        // different attributes -> returns false
        Person differentPhone = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        Person differentEmail = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        Person differentAddress = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        Person differentName = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        Person differentPerson = new PersonBuilder(BOB).build();

        assertNotEquals(ALICE.hashCode(), differentPhone.hashCode());
        assertNotEquals(ALICE.hashCode(), differentEmail.hashCode());
        assertNotEquals(ALICE.hashCode(), differentAddress.hashCode());
        assertNotEquals(ALICE.hashCode(), differentName.hashCode());
        assertNotEquals(ALICE.hashCode(), differentPerson.hashCode());
    }
}
