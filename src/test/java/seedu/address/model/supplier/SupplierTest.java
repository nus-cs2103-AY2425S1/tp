package seedu.address.model.supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCT_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCT_BREAD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSuppliers.ALICE;
import static seedu.address.testutil.TypicalSuppliers.BOB;

import org.junit.jupiter.api.Test;

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
                .withCompany(VALID_COMPANY_BOB).withTags(VALID_TAG_HUSBAND).withProducts(VALID_PRODUCT_BREAD).build();
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

        // different company -> returns false
        editedAlice = new SupplierBuilder(ALICE).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new SupplierBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different products -> returns false
        editedAlice = new SupplierBuilder(ALICE).withProducts(VALID_PRODUCT_APPLE).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Supplier.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", company=" + ALICE.getCompany() + ", tags=" + ALICE.getTags()
                + ", products=" + ALICE.getProducts()
                + ", status=" + ALICE.getStatus() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
