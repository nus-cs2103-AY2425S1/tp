package seedu.sellsavvy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_NAME_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.sellsavvy.testutil.Assert.assertThrows;
import static seedu.sellsavvy.testutil.TypicalCustomers.ALICE;
import static seedu.sellsavvy.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.sellsavvy.model.customer.Customer;
import seedu.sellsavvy.model.customer.exceptions.CustomerNotFoundException;
import seedu.sellsavvy.model.customer.exceptions.DuplicateCustomerException;
import seedu.sellsavvy.testutil.CustomerBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getCustomerList());
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
    public void resetData_withDuplicateCustomers_throwsDuplicateCustomerException() {
        // Two customers with the same identity fields
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Customer> newCustomers = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newCustomers);

        assertThrows(DuplicateCustomerException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasCustomer_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasCustomer(null));
    }

    @Test
    public void hasCustomer_customerNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasCustomer(ALICE));
    }

    @Test
    public void hasCustomer_customerInAddressBook_returnsTrue() {
        addressBook.addCustomer(ALICE);
        assertTrue(addressBook.hasCustomer(ALICE));
    }

    @Test
    public void hasCustomer_customerWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addCustomer(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasCustomer(editedAlice));
    }

    @Test
    public void hasSimilarCustomer_noSimilar_returnsFalse() {
        assertFalse(addressBook.hasSimilarCustomer(ALICE));
    }

    @Test
    public void hasSimilarCustomer_onlyIdenticalCustomer_returnsFalse() {
        addressBook.addCustomer(ALICE);
        assertFalse(addressBook.hasSimilarCustomer(ALICE));
    }

    @Test
    public void hasSimilarCustomer_customerWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addCustomer(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasSimilarCustomer(editedAlice));
    }

    @Test
    public void hasSimilarCustomer_customerWithSimilarNameInAddressBook_returnsTrue() {
        addressBook.addCustomer(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE)
                .withName(ALICE.getName()
                        .fullName.toUpperCase())
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasSimilarCustomer(editedAlice));
    }

    @Test
    public void getCustomerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getCustomerList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{customers=" + addressBook.getCustomerList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void findEquivalentCustomer_modelContainsEquivalentCustomer() {
        AddressBook addressBook1 = getTypicalAddressBook().createCopy();
        AddressBook addressBook2 = addressBook1.createCopy();
        Customer selectedCustomer = addressBook1.getCustomerList().get(INDEX_FIRST.getZeroBased());
        Customer selectedCustomerCopy = addressBook2.findEquivalentCustomer(selectedCustomer);
        assertNotSame(selectedCustomerCopy, selectedCustomer);
        assertEquals(selectedCustomerCopy, selectedCustomer);
    }

    @Test
    public void findEquivalentCustomer_modelDoesNotContainsEquivalentCustomer() {
        AddressBook addressBook1 = getTypicalAddressBook().createCopy();
        Customer selectedCustomer = addressBook1.getCustomerList().get(INDEX_FIRST.getZeroBased());
        Customer differentCustomer = new CustomerBuilder(selectedCustomer).withName(VALID_NAME_BOB).build();
        assertThrows(CustomerNotFoundException.class, () -> addressBook.findEquivalentCustomer(differentCustomer));
    }

    @Test
    public void findEquivalentCustomer_nullInput_throwsAssertionError() {
        AddressBook addressBook1 = getTypicalAddressBook().createCopy();
        assertThrows(AssertionError.class, () -> addressBook1.findEquivalentCustomer(null));
    }

    /**
     * A stub ReadOnlyAddressBook whose customers list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Customer> customers = FXCollections.observableArrayList();

        AddressBookStub(Collection<Customer> customers) {
            this.customers.setAll(customers);
        }

        @Override
        public ObservableList<Customer> getCustomerList() {
            return customers;
        }

    }

}
