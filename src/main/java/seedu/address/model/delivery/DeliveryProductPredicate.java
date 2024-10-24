package seedu.address.model.delivery;

import java.util.function.Predicate;

import seedu.address.model.product.Product;

/**
 * Tests if a {@code Delivery}'s product matches the given product name.
 */
public class DeliveryProductPredicate implements Predicate<Delivery> {
    private final Product targetProduct;

    /**
     * Creates a DeliveryProductPredicate instance based on the given targetProduct.
     * @param targetProduct Product used to filter deliveries
     */
    public DeliveryProductPredicate(Product targetProduct) {
        this.targetProduct = targetProduct;
    }

    @Override
    public boolean test(Delivery delivery) {
        Product deliveryProduct = delivery.getDeliveryProduct();
        return deliveryProduct.equals(targetProduct);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeliveryProductPredicate
                && targetProduct.equals(((DeliveryProductPredicate) other).targetProduct));
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{targetProduct=" + targetProduct.toString() + "}";
    }
}

