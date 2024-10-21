package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.buyer.TypicalBuyers.ALICE;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.buyer.exceptions.DuplicateBuyerException;
import seedu.address.testutil.buyer.BuyerBuilder;

public class BuyerListTest {

    private final BuyerList buyerList = new BuyerList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), buyerList.getBuyerList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> buyerList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyBuyerList_replacesData() {
        BuyerList newData = getTypicalBuyerList();
        buyerList.resetData(newData);
        assertEquals(newData, buyerList);
    }

    @Test
    public void resetData_withDuplicateBuyers_throwsDuplicateBuyerException() {
        // Two buyers with the same identity fields
        Buyer editedAlice = new BuyerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Buyer> newBuyers = Arrays.asList(ALICE, editedAlice);
        BuyerListStub newData = new BuyerListStub(newBuyers);

        assertThrows(DuplicateBuyerException.class, () -> buyerList.resetData(newData));
    }

    @Test
    public void hasBuyer_nullBuyer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> buyerList.hasBuyer(null));
    }

    @Test
    public void hasBuyer_buyerNotInBuyerList_returnsFalse() {
        assertFalse(buyerList.hasBuyer(ALICE));
    }

    @Test
    public void hasBuyer_buyerInBuyerList_returnsTrue() {
        buyerList.addBuyer(ALICE);
        assertTrue(buyerList.hasBuyer(ALICE));
    }

    @Test
    public void hasBuyer_buyerWithSameIdentityFieldsInBuyerList_returnsTrue() {
        buyerList.addBuyer(ALICE);
        Buyer editedAlice = new BuyerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(buyerList.hasBuyer(editedAlice));
    }

    @Test
    public void getBuyerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> buyerList.getBuyerList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = BuyerList.class.getCanonicalName() + "{buyers=" + buyerList.getBuyerList() + "}";
        assertEquals(expected, buyerList.toString());
    }

    /**
     * A stub ReadOnlyBuyerList whose buyers list can violate interface constraints.
     */
    private static class BuyerListStub implements ReadOnlyBuyerList {
        private final ObservableList<Buyer> buyers = FXCollections.observableArrayList();

        BuyerListStub(Collection<Buyer> buyers) {
            this.buyers.setAll(buyers);
        }

        @Override
        public ObservableList<Buyer> getBuyerList() {
            return buyers;
        }
    }

}
