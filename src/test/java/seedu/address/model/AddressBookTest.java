package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCT_RICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.APPLE;
import static seedu.address.testutil.TypicalSuppliers.ALICE;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Status;
import seedu.address.model.delivery.exceptions.DuplicateDeliveryException;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.exceptions.DuplicateSupplierException;
import seedu.address.testutil.DeliveryBuilder;
import seedu.address.testutil.SupplierBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getSupplierList());
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
    public void resetData_withDuplicateSuppliers_throwsDuplicateSupplierException() {
        // Two suppliers with the same identity fields
        Supplier editedAlice = new SupplierBuilder(ALICE).withCompany(VALID_COMPANY_BOB).withTags(VALID_TAG_HUSBAND)
                .withProducts(VALID_PRODUCT_RICE).build();
        List<Supplier> newSuppliers = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = AddressBookStub.withSuppliers(newSuppliers);

        assertThrows(DuplicateSupplierException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateDelivery_throwsDuplicateDeliveryException() {
        // Two deliveries with the same identity fields
        Delivery editedApple = new DeliveryBuilder(APPLE).build();
        List<Delivery> newDeliveries = Arrays.asList(editedApple, APPLE);
        AddressBookStub newData = AddressBookStub.withDeliveries(newDeliveries);

        assertThrows(DuplicateDeliveryException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasSupplier_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasSupplier(null));
    }

    @Test
    public void hasSupplier_supplierNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasSupplier(ALICE));
    }

    @Test
    public void hasSupplier_supplierInAddressBook_returnsTrue() {
        addressBook.addSupplier(ALICE);
        assertTrue(addressBook.hasSupplier(ALICE));
    }

    @Test
    public void hasSupplier_supplierWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addSupplier(ALICE);
        Supplier editedAlice = new SupplierBuilder(ALICE).withCompany(VALID_COMPANY_BOB).withTags(VALID_TAG_HUSBAND)
                .withProducts(VALID_PRODUCT_RICE).build();
        assertTrue(addressBook.hasSupplier(editedAlice));
    }

    @Test
    public void getSupplierList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getSupplierList().remove(0));
    }

    @Test
    public void setDelivery_validTargetAndUpdatedDelivery_success() {
        Delivery delivery = new DeliveryBuilder().build();
        Delivery updatedDelivery = new DeliveryBuilder().withStatus(Status.DELIVERED).build();

        addressBook.addDelivery(delivery);
        addressBook.setDelivery(delivery, updatedDelivery);

        assertEquals(updatedDelivery, addressBook.getDeliveryList().get(0));
    }


    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{suppliers=" + addressBook.getSupplierList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose suppliers list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
        private final ObservableList<Delivery> deliveries = FXCollections.observableArrayList();


        public static AddressBookStub withSuppliers(Collection<Supplier> suppliers) {
            AddressBookStub stub = new AddressBookStub();
            stub.suppliers.setAll(suppliers);
            return stub;
        }

        public static AddressBookStub withDeliveries(Collection<Delivery> deliveries) {
            AddressBookStub stub = new AddressBookStub();
            stub.deliveries.setAll(deliveries);
            return stub;
        }
        @Override
        public ObservableList<Supplier> getSupplierList() {
            return suppliers;
        }

        @Override
        public ObservableList<Delivery> getDeliveryList() {
            return deliveries;
        }
    }

}
