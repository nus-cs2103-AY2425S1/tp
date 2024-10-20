package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBuyers.ALICE;
import static seedu.address.testutil.TypicalBuyers.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.buyer.exceptions.DuplicatePersonException;
import seedu.address.testutil.BuyerBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getBuyerList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two buyers with the same identity fields
        Buyer editedAlice = new BuyerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Buyer> newBuyers = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newBuyers);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasBuyer_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasBuyer(null));
    }

    @Test
    public void hasBuyer_buyerNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasBuyer(ALICE));
    }

    @Test
    public void hasBuyer_personInAddressBook_returnsTrue() {
        addressBook.addBuyer(ALICE);
        assertTrue(addressBook.hasBuyer(ALICE));
    }

    @Test
    public void hasBuyer_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addBuyer(ALICE);
        Buyer editedAlice = new BuyerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasBuyer(editedAlice));
    }

    @Test
    public void getBuyerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getBuyerList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{buyers=" + addressBook.getBuyerList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose buyers list can violate interface constraints.
     */
<<<<<<< HEAD
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Buyer> buyers = FXCollections.observableArrayList();
=======
    private static class AddressBookStub implements ReadOnlyBuyerList {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
>>>>>>> 3b7da801ba1480d359a05de2fa378e9b14b95c1c

        AddressBookStub(Collection<Buyer> buyers) {
            this.buyers.setAll(buyers);
        }

        @Override
        public ObservableList<Buyer> getBuyerList() {
            return buyers;
        }
    }

}
