package seedu.address.model.restaurant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PriceCategoryTest {

    @Test
    public void isSymbol_validSymbols_success() {
        assertTrue(PriceCategory.isSymbol("$"));
        assertTrue(PriceCategory.isSymbol("$$"));
        assertTrue(PriceCategory.isSymbol("$$$"));
        assertTrue(PriceCategory.isSymbol("$$$$"));
    }

    @Test
    public void isSymbol_invalidSymbols_failure() {
        assertFalse(PriceCategory.isSymbol("a"));
        assertFalse(PriceCategory.isSymbol("$$$$$"));
        assertFalse(PriceCategory.isSymbol(""));
        assertFalse(PriceCategory.isSymbol(" "));
    }
}
