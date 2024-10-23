package seedu.address.model.owner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IC_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalOwners.ALICE;
import static seedu.address.testutil.TypicalOwners.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.OwnerBuilder;


public class OwnerTest {

    @Test
    public void isSameOwner() {
        // same object -> returns true
        assertTrue(ALICE.isSameOwner(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameOwner(null));

        // same IC number, all other attributes different -> returns true
        Owner editedAlice = new OwnerBuilder(ALICE).withName("Not alice").withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(ALICE.isSameOwner(editedAlice));

        // different IC number, all other attributes same -> returns false
        editedAlice = new OwnerBuilder(ALICE).withIcNumber(VALID_IC_NUMBER_BOB).build();
        assertFalse(ALICE.isSameOwner(editedAlice));

        // IC number has trailing spaces, all other attributes same -> returns true
        Owner editedBob = new OwnerBuilder(BOB).withIcNumber(VALID_IC_NUMBER_BOB + " ").build();
        assertTrue(BOB.isSameOwner(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Owner aliceCopy = new OwnerBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different Owner -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Owner editedAlice = new OwnerBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new OwnerBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new OwnerBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new OwnerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Owner.class.getCanonicalName() + "{identificationNumber=" + ALICE.getIdentificationNumber()
            + ", name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
            + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
