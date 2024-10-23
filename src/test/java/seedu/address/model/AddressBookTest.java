package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_WITH_RENTAL;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithRental;
import static seedu.address.testutil.TypicalRentalInformation.RENTAL_ONE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.client.exceptions.DuplicatePersonException;
import seedu.address.model.rentalinformation.RentalInformation;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RentalInformationBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void constructor_addressBookTobeCopied_addressBookWithCopiedData() {
        AddressBook addressBookToBeCopied = getTypicalAddressBookWithRental();
        AddressBook latestAddressBook = new AddressBook(addressBookToBeCopied);
        assertEquals(addressBookToBeCopied, latestAddressBook);
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
        // Two clients with the same identity fields
        Client editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        List<Client> newClients = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newClients);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
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
        Client editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void removePerson_personToRemove_success() {
        addressBook.addPerson(FIONA);
        assertTrue(addressBook.hasPerson(FIONA));
        addressBook.removePerson(FIONA);
        assertFalse(addressBook.hasPerson(FIONA));
    }

    @Test
    public void setPerson_updatedPerson_success() {
        Client updatedClient = new PersonBuilder(FIONA).withPhone("88888888").build();
        addressBook.addPerson(FIONA);
        assertTrue(addressBook.hasPerson(FIONA));
        addressBook.setPerson(FIONA, updatedClient);
        assertTrue(addressBook.hasPerson(updatedClient));
        assertFalse(addressBook.hasPerson(FIONA));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void hasRentalInformation_nullClientAndRentalInformation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasRentalInformation(null, null));
        assertThrows(NullPointerException.class, () -> addressBook.hasRentalInformation(ALICE_WITH_RENTAL, null));
        assertThrows(NullPointerException.class, () -> addressBook.hasRentalInformation(null, RENTAL_ONE));
    }

    @Test
    public void hasRentalInformation_clientNotExist_returnsFalse() {
        AddressBook addressBookWithRental = getTypicalAddressBookWithRental();
        assertFalse(addressBookWithRental.hasRentalInformation(CARL, RENTAL_ONE));
    }

    @Test
    public void hasRentalInformation_rentalInformationNotInClientInAddressBook_returnsFalse() {
        AddressBook addressBookWithRental = getTypicalAddressBookWithRental();
        assertFalse(addressBookWithRental.hasRentalInformation(ALICE, RENTAL_ONE));
    }

    @Test
    public void hasRentalInformation_rentalInformationInClientInAddressBook_returnsTrue() {
        AddressBook addressBookWithRental = getTypicalAddressBookWithRental();
        RentalInformation rentalInformation = new RentalInformationBuilder().withAddress("BLK 10 Ang Mo Kio")
                .withRentalStartDate("01/01/2024").withRentalEndDate("31/12/2024").withRentDueDate("10")
                .withMonthlyRent("3000").withDeposit("9000").withCustomerList("David").build();
        assertTrue(addressBookWithRental.hasRentalInformation(ALICE_WITH_RENTAL, rentalInformation));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{clients=" + addressBook.getPersonList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void equalsMethod() {
        assertEquals(addressBook, addressBook);
        assertNotEquals(addressBook, RENTAL_ONE);
    }

    /**
     * A stub ReadOnlyAddressBook whose clients list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Client> clients = FXCollections.observableArrayList();

        AddressBookStub(Collection<Client> clients) {
            this.clients.setAll(clients);
        }

        @Override
        public ObservableList<Client> getPersonList() {
            return clients;
        }
    }

}
