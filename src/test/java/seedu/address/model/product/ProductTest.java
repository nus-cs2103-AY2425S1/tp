package seedu.address.model.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Product(null));
    }

    @Test
    public void constructor_emptyString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Product(""));
    }

    @Test
    public void constructor_tooLong_throwsIllegalArgumentException() {
        String invalidProductName = "a".repeat(51); // 51 characters
        assertThrows(IllegalArgumentException.class, () -> new Product(invalidProductName));
    }


    // Invalid Product Names
    @Test
    public void constructor_productNameWithSpecialChars_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Product@123"));
    }

    @Test
    public void constructor_productNameWithMultipleSpacesAndSpecialCharacter_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Product$    123"));
    }



    @Test
    public void isValidProductName_validInputs_returnTrue() {
        // Valid partition
        assertTrue(Product.isValidProductName("Product123"));
        assertTrue(Product.isValidProductName("Product 123"));
        assertTrue(Product.isValidProductName("a"));
        assertTrue(Product.isValidProductName("Valid Product"));
        assertTrue(Product.isValidProductName("100abc")); // no space between number and unit
        assertTrue(Product.isValidProductName("100  123")); // multiple spaces
        assertTrue(Product.isValidProductName("Product   123")); // multiple spaces between words


        // Invalid partition
        assertFalse(Product.isValidProductName(""));
        assertFalse(Product.isValidProductName(" ")); // spaces only
        assertFalse(Product.isValidProductName(" Product123")); // leading space
        assertFalse(Product.isValidProductName("Product123$")); // special char
    }


}
