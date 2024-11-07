package seedu.sellsavvy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.customercommands.PersonCommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.PersonCommandTestUtil.VALID_NAME_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.PersonCommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.sellsavvy.testutil.Assert.assertThrows;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.sellsavvy.testutil.TypicalCustomers.ALICE;
import static seedu.sellsavvy.testutil.TypicalCustomers.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.sellsavvy.model.customer.Customer;
import seedu.sellsavvy.model.customer.exceptions.DuplicateCustomerException;
import seedu.sellsavvy.model.customer.exceptions.CustomerNotFoundException;
import seedu.sellsavvy.testutil.CustomerBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
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
        // Two customers with the same identity fields
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Customer> newCustomers = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newCustomers);

        assertThrows(DuplicateCustomerException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void hasSimilarPerson_noSimilar_returnsFalse() {
        assertFalse(addressBook.hasSimilarPerson(ALICE));
    }

    @Test
    public void hasSimilarPerson_onlyIdenticalPerson_returnsFalse() {
        addressBook.addPerson(ALICE);
        assertFalse(addressBook.hasSimilarPerson(ALICE));
    }

    @Test
    public void hasSimilarPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasSimilarPerson(editedAlice));
    }

    @Test
    public void hasSimilarPerson_personWithSimilarNameInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE)
                .withName(ALICE.getName()
                        .fullName.toUpperCase())
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasSimilarPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{customers=" + addressBook.getPersonList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void findEquivalentPerson_modelContainsEquivalentPerson() {
        AddressBook addressBook1 = getTypicalAddressBook().createCopy();
        AddressBook addressBook2 = addressBook1.createCopy();
        Customer selectedCustomer = addressBook1.getPersonList().get(INDEX_FIRST.getZeroBased());
        Customer selectedCustomerCopy = addressBook2.findEquivalentPerson(selectedCustomer);
        assertNotSame(selectedCustomerCopy, selectedCustomer);
        assertEquals(selectedCustomerCopy, selectedCustomer);
    }

    @Test
    public void findEquivalentPerson_modelDoesNotContainsEquivalentPerson() {
        AddressBook addressBook1 = getTypicalAddressBook().createCopy();
        Customer selectedCustomer = addressBook1.getPersonList().get(INDEX_FIRST.getZeroBased());
        Customer differentCustomer = new CustomerBuilder(selectedCustomer).withName(VALID_NAME_BOB).build();
        assertThrows(CustomerNotFoundException.class, () -> addressBook.findEquivalentPerson(differentCustomer));
    }

    @Test
    public void findEquivalentPerson_nullInput_throwsAssertionError() {
        AddressBook addressBook1 = getTypicalAddressBook().createCopy();
        assertThrows(AssertionError.class, () -> addressBook1.findEquivalentPerson(null));
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
        public ObservableList<Customer> getPersonList() {
            return customers;
        }

    }

}
