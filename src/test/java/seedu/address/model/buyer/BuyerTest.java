package seedu.address.model.buyer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.buyer.TypicalBuyers.ALICE;
import static seedu.address.testutil.buyer.TypicalBuyers.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.buyer.BuyerBuilder;

public class BuyerTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Buyer buyer = new BuyerBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> buyer.getTags().remove(0));
    }

    @Test
    public void isSameBuyer() {
        // same object -> returns true
        assertTrue(ALICE.isSameBuyer(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameBuyer(null));

        // same name, all other attributes different -> returns true
        Buyer editedAlice = new BuyerBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withBudget(VALID_BUDGET_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameBuyer(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new BuyerBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameBuyer(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Buyer editedBob = new BuyerBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameBuyer(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new BuyerBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameBuyer(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Buyer aliceCopy = new BuyerBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different buyer -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Buyer editedAlice = new BuyerBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new BuyerBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new BuyerBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different budget -> returns false
        editedAlice = new BuyerBuilder(ALICE).withBudget(VALID_BUDGET_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new BuyerBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Buyer.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", budget=" + ALICE.getBudget()
                + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
