package seedu.address.model.supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSuppliers.ALICE;
import static seedu.address.testutil.TypicalSuppliers.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.product.StockLevel;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.SupplierBuilder;

public class SupplierTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Supplier supplier = new SupplierBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> supplier.getTags().remove(0));
    }

    @Test
    public void isSameSupplier() {
        // same object -> returns true
        assertTrue(ALICE.isSameSupplier(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameSupplier(null));

        // same name, all other attributes different -> returns true
        Supplier editedAlice = new SupplierBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameSupplier(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new SupplierBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameSupplier(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Supplier editedBob = new SupplierBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameSupplier(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new SupplierBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameSupplier(editedBob));
    }

    @Test
    public void getProducts_throwsUnsupportedOperationException() {
        Supplier supplier = new SupplierBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> supplier.getProducts().remove(0));
    }

    @Test
    public void hasProduct_productInList_returnsTrue() {
        Product product = new Product(new ProductName("Test Product"), new StockLevel(0, 0, 0));
        Supplier supplier = new SupplierBuilder().withProducts(SampleDataUtil.getProductSet("Test Product")).build();
        assertTrue(supplier.hasProduct(product));
    }

    @Test
    public void hasProduct_productNotInList_returnsFalse() {
        Product product = new Product(new ProductName("Test Product"), new StockLevel(0, 0, 0));
        Supplier supplier = new SupplierBuilder().build();
        assertFalse(supplier.hasProduct(product));
    }
    @Test
    public void equals() {
        // same values -> returns true
        Supplier aliceCopy = new SupplierBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different supplier -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Supplier editedAlice = new SupplierBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new SupplierBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new SupplierBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new SupplierBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new SupplierBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Supplier.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
