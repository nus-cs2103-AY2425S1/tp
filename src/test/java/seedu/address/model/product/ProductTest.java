package seedu.address.model.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.product.exceptions.DuplicateProductException;
import seedu.address.model.product.exceptions.ProductNotFoundException;

public class ProductTest {

    @Test
    public void isSameProduct() {
        ProductName productName1 = new ProductName("Product A");
        ProductName productName2 = new ProductName("Product B");

        StockLevel stockLevel = new StockLevel(0, 0, 80);
        Product product1 = new Product(productName1, stockLevel);
        Product product2 = new Product(productName2, stockLevel);
        Product product3 = new Product(productName1, stockLevel);

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

        StockLevel stockLevel = new StockLevel(0, 0, 80);
        Product product1 = new Product(productName1, stockLevel);
        Product product2 = new Product(productName2, stockLevel);
        Product product3 = new Product(productName1, stockLevel);

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

        StockLevel stockLevel = new StockLevel(0, 0, 80);
        Product product1 = new Product(productName1, stockLevel);
        Product product2 = new Product(productName2, stockLevel);
        Product product3 = new Product(productName1, stockLevel);

        // same values -> returns same hashcode
        assertTrue(product1.hashCode() == product3.hashCode());

        // different values -> returns different hashcode
        assertFalse(product1.hashCode() == product2.hashCode());
    }

    @Test
    public void productNameTest() {
        // valid name
        ProductName validName = new ProductName("Valid Name");
        assertTrue(validName.toString().equals("Valid Name"));

        // invalid name
        assertThrows(NullPointerException.class, () -> new ProductName(null));
        assertThrows(IllegalArgumentException.class, () -> new ProductName(""));
    }

    @Test
    public void uniqueProductListTest() {
        UniqueProductList uniqueProductList = new UniqueProductList();
        ProductName productName1 = new ProductName("Product A");
        StockLevel stockLevel = new StockLevel(0, 0, 80);
        Product product1 = new Product(productName1, stockLevel);
        // add product
        uniqueProductList.add(product1);
        assertTrue(uniqueProductList.contains(product1));

        // add duplicate product -> throws exception
        assertThrows(DuplicateProductException.class, () -> uniqueProductList.add(product1));

        // remove product
        uniqueProductList.remove(product1);
        assertFalse(uniqueProductList.contains(product1));

        // remove non-existent product -> throws exception
        assertThrows(ProductNotFoundException.class, () -> uniqueProductList.remove(product1));
    }
}
