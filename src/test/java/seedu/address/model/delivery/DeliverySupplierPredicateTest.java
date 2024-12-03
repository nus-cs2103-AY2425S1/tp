package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.supplier.Supplier;
import seedu.address.testutil.TypicalDeliveries;
import seedu.address.testutil.TypicalSuppliers;

public class DeliverySupplierPredicateTest {

    @Test
    public void test_supplierMatches_returnsTrue() {
        Delivery delivery = TypicalDeliveries.BREAD; // Supplier is BENSON
        Supplier supplier = TypicalSuppliers.BENSON;
        DeliverySupplierPredicate predicate = new DeliverySupplierPredicate(supplier);

        assertTrue(predicate.test(delivery));
    }

    @Test
    public void test_supplierDoesNotMatch_returnsFalse() {
        Delivery delivery = TypicalDeliveries.CAN; // Supplier is CARL
        Supplier supplier = TypicalSuppliers.BENSON;
        DeliverySupplierPredicate predicate = new DeliverySupplierPredicate(supplier);

        assertFalse(predicate.test(delivery));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        DeliverySupplierPredicate predicate = new DeliverySupplierPredicate(TypicalSuppliers.ALICE);

        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_differentObjectSameSupplier_returnsTrue() {
        DeliverySupplierPredicate predicate1 = new DeliverySupplierPredicate(TypicalSuppliers.BOB);
        DeliverySupplierPredicate predicate2 = new DeliverySupplierPredicate(TypicalSuppliers.BOB);

        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentSupplier_returnsFalse() {
        DeliverySupplierPredicate predicate1 = new DeliverySupplierPredicate(TypicalSuppliers.ALICE);
        DeliverySupplierPredicate predicate2 = new DeliverySupplierPredicate(TypicalSuppliers.CARL);

        assertFalse(predicate1.equals(predicate2));
    }
}

