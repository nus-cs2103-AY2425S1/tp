package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProducts.APPLE;
import static seedu.address.testutil.TypicalSuppliers.ALICE;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.product.Product;
import seedu.address.model.product.exceptions.DuplicateProductException;
import seedu.address.model.product.exceptions.ProductNotFoundException;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.exceptions.DuplicateSupplierException;
import seedu.address.testutil.ProductBuilder;
import seedu.address.testutil.SupplierBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getSupplierList());
        assertEquals(Collections.emptyList(), addressBook.getProductList());
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
        Supplier editedAlice = new SupplierBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Supplier> newSuppliers = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newSuppliers, Collections.emptyList());

        assertThrows(DuplicateSupplierException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateProducts_throwsDuplicateProductException() {
        Product editedApple = new ProductBuilder(APPLE).withName("Apple").build();
        List<Product> newProducts = Arrays.asList(APPLE, editedApple);
        AddressBookStub newData = new AddressBookStub(Collections.emptyList(), newProducts);

        assertThrows(DuplicateProductException.class, () -> addressBook.resetData(newData));
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
        Supplier editedAlice = new SupplierBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasSupplier(editedAlice));
    }

    @Test
    public void hasProduct_nullProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasProduct(null));
    }

    @Test
    public void hasProduct_productNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasProduct(APPLE));
    }

    @Test
    public void hasProduct_productInAddressBook_returnsTrue() {
        addressBook.addProduct(APPLE);
        assertTrue(addressBook.hasProduct(APPLE));
    }

    @Test
    public void hasProduct_productWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addProduct(APPLE);
        Product editedApple = new ProductBuilder(APPLE).withName("Apple").build();
        assertTrue(addressBook.hasProduct(editedApple));
    }

    @Test
    public void getSupplierList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getSupplierList().remove(0));
    }

    @Test
    public void getProductList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getProductList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{suppliers=" + addressBook.getSupplierList()
            + ", products=" + addressBook.getProductList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void setProduct_nullTargetProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setProduct(null, APPLE));
    }

    @Test
    public void setProduct_nullEditedProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setProduct(APPLE, null));
    }

    @Test
    public void setProduct_editedProductIsSameProduct_success() {
        addressBook.addProduct(APPLE);
        addressBook.setProduct(APPLE, APPLE);
        assertEquals(1, addressBook.getProductList().size());
        assertTrue(addressBook.hasProduct(APPLE));
    }

    @Test
    public void setProduct_editedProductHasSameIdentity_success() {
        addressBook.addProduct(APPLE);
        Product editedApple = new ProductBuilder(APPLE).withName("Apple").build();
        addressBook.setProduct(APPLE, editedApple);
        assertEquals(1, addressBook.getProductList().size());
        assertTrue(addressBook.hasProduct(editedApple));
    }

    @Test
    public void setProduct_editedProductHasDifferentIdentity_success() {
        addressBook.addProduct(APPLE);
        Product editedApple = new ProductBuilder().withName("Banana").build();
        addressBook.setProduct(APPLE, editedApple);
        assertEquals(1, addressBook.getProductList().size());
        assertTrue(addressBook.hasProduct(editedApple));
    }

    @Test
    public void setProduct_editedProductHasNonUniqueIdentity_throwsDuplicateProductException() {
        addressBook.addProduct(APPLE);
        addressBook.addProduct(new ProductBuilder().withName("Banana").build());
        Product editedApple = new ProductBuilder().withName("Banana").build();
        assertThrows(DuplicateProductException.class, () -> addressBook.setProduct(APPLE, editedApple));
    }

    @Test
    public void removeProduct_nullProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.removeProduct(null));
    }

    @Test
    public void removeProduct_productNotInAddressBook_throwsProductNotFoundException() {
        assertThrows(ProductNotFoundException.class, () -> addressBook.removeProduct(APPLE));
    }

    @Test
    public void removeProduct_productInAddressBook_success() {
        addressBook.addProduct(APPLE);
        addressBook.removeProduct(APPLE);
        assertFalse(addressBook.hasProduct(APPLE));
        assertEquals(0, addressBook.getProductList().size());
    }

    @Test
    public void removeProduct_productWithSameIdentityFieldsInAddressBook_success() {
        addressBook.addProduct(APPLE);
        Product editedApple = new ProductBuilder(APPLE).withName("Apple").build();
        addressBook.removeProduct(editedApple);
        assertFalse(addressBook.hasProduct(APPLE));
        assertEquals(0, addressBook.getProductList().size());
    }

    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
        private final ObservableList<Product> products = FXCollections.observableArrayList();

        AddressBookStub(Collection<Supplier> suppliers, Collection<Product> products) {
            this.suppliers.setAll(suppliers);
            this.products.setAll(products);
        }

        @Override
        public ObservableList<Supplier> getSupplierList() {
            return suppliers;
        }

        @Override
        public ObservableList<Product> getProductList() {
            return products;
        }

    }
}
