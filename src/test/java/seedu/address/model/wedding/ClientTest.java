package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY_WEDDING;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ClientTest {
    private Client aliceClient = new Client(ALICE);
    private Client bensonClient = new Client(BENSON);
    private Client bobClient = new Client(BOB);

    @Test
    public void isValidClientName() {
        // valid client name
        String test = "John Doe";
        assertTrue(Client.isValidClientName(test));

        test = "O'Connor";
        assertTrue(Client.isValidClientName(test));

        test = "John-Doe";
        assertTrue(Client.isValidClientName(test));

        // invalid client name
        test = "John?Doe";
        assertFalse(Client.isValidClientName(test));

        test = "     ";
        assertFalse(Client.isValidClientName(test));

        test = "John Doe $$";
        assertFalse(Client.isValidClientName(test));

        test = "";
        assertFalse(Client.isValidClientName(test));
    }

    @Test
    public void getName() {
        Name aliceName = aliceClient.getName();
        assertEquals(aliceName, new Name("Alice Pauline"));
    }

    @Test
    public void getPerson() {
        Person alicePerson = aliceClient.getPerson();
        assertEquals(alicePerson, ALICE);
    }
    @Test
    public void isValidClientIndex() {
        // valid client index
        String test = "1";
        assertTrue(Client.isValidClientIndex(test));

        test = "123";
        assertTrue(Client.isValidClientIndex(test));

        // invalid client index
        test = "-123";
        assertFalse(Client.isValidClientIndex(test));

        test = "     ";
        assertFalse(Client.isValidClientIndex(test));

        test = "";
        assertFalse(Client.isValidClientIndex(test));

        test = "  123   ";
        assertFalse(Client.isValidClientIndex(test));

        test = "123a";
        assertFalse(Client.isValidClientIndex(test));
    }

    @Test
    public void setOwnWedding() {
        Client editedAlice = new Client(new PersonBuilder(ALICE).build());
        editedAlice.setOwnWedding(AMY_WEDDING);

        Client expected = new Client(new PersonBuilder(ALICE).withOwnWedding(AMY_WEDDING).build());

        assertEquals(expected, editedAlice);
    }
    @Test
    public void equals() {
        // same values -> returns true
        Client aliceCopy = new Client(new PersonBuilder(ALICE).build());
        assertTrue(aliceClient.equals(aliceCopy));

        // same object -> returns true
        assertTrue(aliceClient.equals(aliceClient));

        // null -> returns false
        assertFalse(aliceClient.equals(null));

        // different type -> returns false
        assertFalse(aliceClient.equals(5));

        // different person -> returns false
        assertFalse(aliceClient.equals(bobClient));

        // different name -> returns false
        Client editedAlice = new Client(new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build());
        assertFalse(aliceClient.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new Client(new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build());
        assertFalse(aliceClient.equals(editedAlice));

        // different email -> returns false
        editedAlice = new Client(new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build());
        assertFalse(aliceClient.equals(editedAlice));

        // different address -> returns false
        editedAlice = new Client(new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build());
        assertFalse(aliceClient.equals(editedAlice));

    }

    @Test
    public void toStringMethod() {
        Person alice = aliceClient.getPerson();
        String expected = Person.class.getCanonicalName() + "{name=" + alice.getName() + ", phone=" + alice.getPhone()
                + ", email=" + alice.getEmail() + ", address=" + alice.getAddress() + ", roles=" + alice.getRole()
                + ", wedding=" + alice.getOwnWedding() + ", wedding jobs=" + alice.getWeddingJobs() + "}";

        assertEquals(expected, aliceClient.toString());

    }
}
