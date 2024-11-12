package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISSUE_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getIssues().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withIssues(VALID_ISSUE_HUSBAND).build();
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

        // same person, but 1 has car and the other does not -> returns true
        editedAlice = new PersonBuilder(ALICE).withCar("SH8942L", "11111111111111111", "Toyota", "Corolla").build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same person, but have different cars -> returns true
        Person editedAliceDifferentCar = new PersonBuilder(ALICE).withCar("SH8942L", "22222222222222222",
                                                                            "Honda", "Civic").build();
        assertTrue(editedAlice.isSamePerson(editedAliceDifferentCar));
    }

    @SuppressWarnings("unlikely-arg-type")
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

        // different issues -> returns false
        editedAlice = new PersonBuilder(ALICE).withIssues(VALID_ISSUE_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different car -> returns false
        editedAlice = new PersonBuilder(ALICE).withCar("SH8942L",
                "11111111111111111", "Toyota", "Corolla").build();
        Person editedAliceDifferentCar = new PersonBuilder(ALICE).withCar("SH8942L",
                "22222222222222222", "Honda", "Civic").build();
        assertFalse(editedAliceDifferentCar.equals(editedAlice));

        // same person, but 1 has car and the other does not -> returns false
        editedAlice = new PersonBuilder(ALICE).withCar("SH8942L", "11111111111111111", "Toyota", "Corolla").build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone="
            + ALICE.getPhone()
            + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
            + ", issues=" + ALICE.getIssues() + "}";
        assertEquals(expected, ALICE.toString());

        // Test with car
        Person aliceWithCar = new PersonBuilder(ALICE).withCar("SH8942L", "11111111111111111",
                                                                "Toyota", "Corolla").build();
        expected = Person.class.getCanonicalName() + "{name=" + aliceWithCar.getName() + ", phone="
                + aliceWithCar.getPhone() + ", email=" + aliceWithCar.getEmail()
                + ", address=" + aliceWithCar.getAddress() + ", issues=" + aliceWithCar.getIssues()
                + ", car=" + aliceWithCar.getCar() + "}";
        assertEquals(expected, aliceWithCar.toString());
    }

    @Test
    public void checkInOutTest() {
        // New clients are not automatically Checked in.
        Person alice = new PersonBuilder(ALICE).build();
        Person bob = new PersonBuilder(BOB).build();
        assertFalse(alice.isServicing());
        assertFalse(bob.isServicing());

        // Clients with no Car cannot be Checked in.
        alice.setServicing();
        bob.setServicing();
        assertFalse(alice.isServicing());
        assertFalse(bob.isServicing());

        alice = new PersonBuilder(alice).withCar("SJH9514P", "KMHGH4JH3EU073801", "Hyundai", "Kona 1.6T").build();
        bob = new PersonBuilder(bob).withCar("S6780S", "ABCDE12345ABCDE12", "BMW", "520i").build();

        // Clients with Car can be Checked in/ out indefinitely.
        for (int i = 0; i < 10; i++) {
            // Clients with Car whom are Checked out can be Checked in.
            alice.setServicing();
            bob.setServicing();
            assertTrue(alice.isServicing());
            assertTrue(bob.isServicing());
            // Clients with Car whom are Checked in can be Checked out.
            alice.setServicing();
            bob.setServicing();
            assertFalse(alice.isServicing());
            assertFalse(bob.isServicing());
        }
    }
}
