package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVendors.ALICE;
import static seedu.address.testutil.TypicalVendors.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;
import seedu.address.model.vendor.exceptions.DuplicateVendorException;
import seedu.address.testutil.VendorBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getVendorList());
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
    public void resetData_withDuplicateVendors_throwsDuplicateVendorException() {
        // Two vendors with the same identity fields
        Vendor editedAlice = new VendorBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Vendor> newVendors = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newVendors);

        assertThrows(DuplicateVendorException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasVendor_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasVendor(null));
    }

    @Test
    public void hasVendor_vendorNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasVendor(ALICE));
    }

    @Test
    public void hasVendor_vendorInAddressBook_returnsTrue() {
        addressBook.addVendor(ALICE);
        assertTrue(addressBook.hasVendor(ALICE));
    }

    @Test
    public void hasVendor_vendorWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addVendor(ALICE);
        Vendor editedAlice = new VendorBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasVendor(editedAlice));
    }

    @Test
    public void getVendorList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getVendorList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{vendors=" + addressBook.getVendorList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose vendors list can violate interface
     * constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Vendor> vendors = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();

        AddressBookStub(Collection<Vendor> vendors) {
            this.vendors.setAll(vendors);
        }

        @Override
        public ObservableList<Vendor> getVendorList() {
            return vendors;
        }

        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }
    }

}
