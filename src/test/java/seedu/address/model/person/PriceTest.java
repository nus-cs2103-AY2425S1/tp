package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PriceTest {
    @Test
    public void isValidHashCode() {
        // null price
        Price price = new Price("100000");
        assertEquals(price.hashCode(), price.hashCode());
    }
}
