package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.product.Product;
import seedu.address.testutil.TypicalDeliveries;

public class DeliveryProductPredicateTest {

    @Test
    public void test_productMatches_returnsTrue() {
        Delivery delivery = TypicalDeliveries.BREAD; // Product is "bread"
        DeliveryProductPredicate predicate = new DeliveryProductPredicate(new Product("bread"));

        assertTrue(predicate.test(delivery));
    }

    @Test
    public void test_productDoesNotMatch_returnsFalse() {
        Delivery delivery = TypicalDeliveries.CAN; // Product is "cannedDrinks"
        DeliveryProductPredicate predicate = new DeliveryProductPredicate(new Product("bread"));

        assertFalse(predicate.test(delivery));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        DeliveryProductPredicate predicate = new DeliveryProductPredicate(new Product("Iphone16Pro"));

        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_differentObjectSameProduct_returnsTrue() {
        DeliveryProductPredicate predicate1 = new DeliveryProductPredicate(new Product("Apples"));
        DeliveryProductPredicate predicate2 = new DeliveryProductPredicate(new Product("Apples"));

        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentProduct_returnsFalse() {
        DeliveryProductPredicate predicate1 = new DeliveryProductPredicate(new Product("Iphone16Pro"));
        DeliveryProductPredicate predicate2 = new DeliveryProductPredicate(new Product("bread"));

        assertFalse(predicate1.equals(predicate2));
    }
}

