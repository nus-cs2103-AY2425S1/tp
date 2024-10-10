package seedu.address.model.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    public void isSameProduct() {
        ProductName productName1 = new ProductName("Product A");
        ProductName productName2 = new ProductName("Product B");
        Product product1 = new Product(productName1);
        Product product2 = new Product(productName2);
        Product product3 = new Product(productName1);

        // same object -> returns true
        assertTrue(product1.isSameProduct(product1));

        // null -> returns false
        assertFalse(product1.isSameProduct(null));

        // different name -> returns false
        assertFalse(product1.isSameProduct(product2));

        // same name -> returns true
        assertTrue(product1.isSameProduct(product3));
    }

    @Test
    public void equals() {
        ProductName productName1 = new ProductName("Product A");
        ProductName productName2 = new ProductName("Product B");
        Product product1 = new Product(productName1);
        Product product2 = new Product(productName2);
        Product product3 = new Product(productName1);

        // same values -> returns true
        assertTrue(product1.equals(product3));

        // same object -> returns true
        assertTrue(product1.equals(product1));

        // null -> returns false
        assertFalse(product1.equals(null));

        // different type -> returns false
        assertFalse(product1.equals(5));

        // different product -> returns false
        assertFalse(product1.equals(product2));
    }

    @Test
    public void hashCodeTest() {
        ProductName productName1 = new ProductName("Product A");
        ProductName productName2 = new ProductName("Product B");
        Product product1 = new Product(productName1);
        Product product2 = new Product(productName2);
        Product product3 = new Product(productName1);

        // same values -> returns same hashcode
        assertTrue(product1.hashCode() == product3.hashCode());

        // different values -> returns different hashcode

        assertFalse(product1.hashCode() == product2.hashCode());
    }
}
