package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDCON_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getAllergies().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same nric, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withName(VALID_NAME_BOB).withDateOfBirth(VALID_DOB_BOB).withGender(VALID_GENDER_BOB)
                .withAddress(VALID_ADDRESS_BOB).withAllergies(VALID_ALLERGY_BOB).withPriority(VALID_PRIORITY_BOB)
                .withMedCons(VALID_MEDCON_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different nric, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withNric(VALID_NRIC_BOB).build();
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

        // different nric -> returns false
        editedAlice = new PersonBuilder(ALICE).withNric(VALID_NRIC_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        //different date of birth -> returns false
        editedAlice = new PersonBuilder(ALICE).withDateOfBirth(VALID_DOB_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        //different gender -> returns false
        editedAlice = new PersonBuilder(ALICE).withGender(VALID_GENDER_BOB).build();
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

        // different allergies -> returns false
        editedAlice = new PersonBuilder(ALICE).withAllergies(VALID_ALLERGY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different medical condition -> returns false
        editedAlice = new PersonBuilder(ALICE).withMedCons(VALID_MEDCON_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different priority -> returns false
        editedAlice = new PersonBuilder(ALICE).withPriority(VALID_PRIORITY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", nric=" + ALICE.getNric()
                + ", gender=" + ALICE.getGender() + ", dateOfBirth=" + ALICE.getDateOfBirth() + ", phone="
                + ALICE.getPhone() + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", allergies=" + ALICE.getAllergies() + ", priority=" + ALICE.getPriority() + ", appointments="
                + ALICE.getAppointments() + ", medical conditions=" + ALICE.getMedCons() + "}";

        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void hashCode_sameObject_sameHashCode() {
        // Arrange
        Person person = new PersonBuilder().withName("John Doe")
                .withPhone("12345678")
                .withEmail("johndoe@example.com")
                .withNric("S1234567A")
                .withDateOfBirth("2001-01-01")
                .withGender("M")
                .withAddress("123 Main St")
                .withPriority("LOW")
                .build();

        assertEquals(person.hashCode(), person.hashCode(), "Hashcode should be consistent");
    }
    @Test
    public void hashCode_equalObjects_sameHashCode() {
        // Arrange
        Person person1 = new PersonBuilder().withName("John Doe")
                .withPhone("12345678")
                .withEmail("johndoe@example.com")
                .withNric("S1234567A")
                .withDateOfBirth("2001-01-01")
                .withGender("M")
                .withAddress("123 Main St")
                .withPriority("LOW")
                .build();

        Person person2 = new PersonBuilder().withName("John Doe")
                .withPhone("12345678")
                .withEmail("johndoe@example.com")
                .withNric("S1234567A")
                .withDateOfBirth("2001-01-01")
                .withGender("M")
                .withAddress("123 Main St")
                .withPriority("LOW")
                .build();

        // Act & Assert
        assertEquals(person1.hashCode(), person2.hashCode(),
                "Hashcodes of equal objects should be the same");
    }

    @Test
    public void hashCode_differentObjects_differentHashCodes() {
        // Arrange
        Person person1 = new PersonBuilder().withName("John Doe")
                .withPhone("12345678")
                .withEmail("johndoe@example.com")
                .withNric("S1234567A")
                .withDateOfBirth("2001-01-01")
                .withGender("M")
                .withAddress("123 Main St")
                .withPriority("LOW")
                .build();

        Person person2 = new PersonBuilder().withName("Jane Doe")
                .withPhone("87654321")
                .withEmail("janedoe@example.com")
                .withNric("S7654321B")
                .withDateOfBirth("2001-01-02")
                .withGender("F")
                .withAddress("456 Another St")
                .withPriority("HIGH")
                .build();

        // Act & Assert
        assertNotEquals(person1.hashCode(), person2.hashCode(),
                "Hashcodes of different objects should be different");
    }
}
