package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test class for {@code Property}.
 */
public class PropertyTest {

    @Test
    public void of_validAttributes_success() {
        Property property = Property.of(
                "123 Orchard Road", "Singapore", "Condo",
                120.5, 3, 2, 1500000.0);

        String expected = "Property at 123 Orchard Road, Singapore (Condo): 120.50 sqm, 3 bed, 2 bath - $1500000.00";
        assertEquals(expected, property.toString());
    }

    @Test
    public void of_negativeSize_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                Property.of("123 Orchard Road", "Singapore", "Condo",
                        -50.0, 2, 1, 800000.0));
    }

    @Test
    public void of_negativeBedrooms_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                Property.of("123 Orchard Road", "Singapore", "HDB",
                        80.0, -1, 1, 500000.0));
    }

    @Test
    public void of_negativeBathrooms_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                Property.of("123 Orchard Road", "Singapore", "Landed",
                        200.0, 4, -2, 3000000.0));
    }

    @Test
    public void of_negativePrice_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                Property.of("123 Orchard Road", "Singapore", "Condo",
                        120.0, 2, 2, -100000.0));
    }

    @Test
    public void of_nullAddress_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                Property.of(null, "Singapore", "Condo",
                        120.0, 2, 2, 1000000.0));
    }

    @Test
    public void of_nullTown_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                Property.of("123 Orchard Road", null, "Condo",
                        120.0, 2, 2, 1000000.0));
    }

    @Test
    public void of_nullPropertyType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                Property.of("123 Orchard Road", "Singapore", null,
                        120.0, 2, 2, 1000000.0));
    }
}
