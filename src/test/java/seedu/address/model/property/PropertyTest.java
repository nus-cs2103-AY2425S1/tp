package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * Test class for Property.
 */
class PropertyTest {

    @Test
    void testPropertyCreation() {
        Property property = new Property(
                "123 Main St",
                "Central Town",
                "Apartment",
                85.5,
                3,
                2,
                500000.0,
                Optional.of("Great view")
        );

        // Check if fields are correctly assigned
        assertEquals("123 Main St", property.getAddress());
        assertEquals("Central Town", property.getTown());
        assertEquals("Apartment", property.getPropertyType());
        assertEquals(85.5, property.getSize());
        assertEquals(3, property.getNumberOfBedrooms());
        assertEquals(2, property.getNumberOfBathrooms());
        assertEquals(500000.0, property.getPrice());
        assertEquals(Optional.of("Great view"), property.getRemark());
    }

    @Test
    void testEqualsSameProperties() {
        Property property1 = new Property(
                "123 Main St",
                "Central Town",
                "Apartment",
                85.5,
                3,
                2,
                500000.0,
                Optional.of("Great view")
        );

        Property property2 = new Property(
                "123 Main St",
                "Central Town",
                "Apartment",
                85.5,
                3,
                2,
                500000.0,
                Optional.of("Great view")
        );

        // Ensure that two properties with identical values are considered equal
        assertEquals(property1, property2);
    }

    @Test
    void testEqualsDifferentProperties() {
        Property property1 = new Property(
                "123 Main St",
                "Central Town",
                "Apartment",
                85.5,
                3,
                2,
                500000.0,
                Optional.of("Great view")
        );

        Property property2 = new Property(
                "456 Side St",
                "West Town",
                "House",
                120.0,
                4,
                3,
                750000.0,
                Optional.empty()
        );

        // Ensure that two different properties are not equal
        assertNotEquals(property1, property2);
    }

    @Test
    void testToString() {
        Property property = new Property(
                "123 Main St",
                "Central Town",
                "Apartment",
                85.5,
                3,
                2,
                500000.0,
                Optional.of("Great view")
        );

        String expected = "Property[address=123 Main St, town=Central Town, type=Apartment, "
                + "size=85.50, bedrooms=3, bathrooms=2, price=500000.00, remark=Great view]";

        assertEquals(expected, property.toString());
    }

    @Test
    void testOptionalRemarkEmpty() {
        Property property = new Property(
                "123 Main St",
                "Central Town",
                "Apartment",
                85.5,
                3,
                2,
                500000.0,
                Optional.empty()
        );

        assertEquals(Optional.empty(), property.getRemark());
        assertEquals("Property[address=123 Main St, town=Central Town, type=Apartment, "
                        + "size=85.50, bedrooms=3, bathrooms=2, price=500000.00, remark=No remark]",
                property.toString());
    }

    @Test
    void testPropertyCreationWithEdgeValues() {
        Property property = new Property(
                "123 Main St",
                "Central Town",
                "Apartment",
                0.0,  // Edge case for size
                0,     // Edge case for bedrooms
                0,     // Edge case for bathrooms
                0.0,   // Edge case for price
                Optional.empty() // No remark
        );

        assertEquals(0.0, property.getSize());
        assertEquals(0, property.getNumberOfBedrooms());
        assertEquals(0, property.getNumberOfBathrooms());
        assertEquals(0.0, property.getPrice());
        assertEquals(Optional.empty(), property.getRemark());
    }

    @Test
    void testHashCodeConsistency() {
        Property property1 = new Property("123 Main St", "Central Town", "Apartment", 85.5, 3, 2, 500000.0, Optional.of("Great view"));
        Property property2 = new Property("123 Main St", "Central Town", "Apartment", 85.5, 3, 2, 500000.0, Optional.of("Great view"));

        assertEquals(property1.hashCode(), property2.hashCode());
    }

    @Test
    void testEqualsNullComparison() {
        Property property = new Property("123 Main St", "Central Town", "Apartment", 85.5, 3, 2, 500000.0, Optional.of("Great view"));

        assertNotEquals(property, null);
    }

    @Test
    void testEqualsDifferentClass() {
        Property property = new Property("123 Main St", "Central Town", "Apartment", 85.5, 3, 2, 500000.0, Optional.of("Great view"));

        assertNotEquals(property, "Not a Property");
    }

    @Test
    void testPropertyWithDifferentRemarks() {
        Property property1 = new Property("123 Main St", "Central Town", "Apartment", 85.5, 3, 2, 500000.0, Optional.of("Great view"));
        Property property2 = new Property("123 Main St", "Central Town", "Apartment", 85.5, 3, 2, 500000.0, Optional.of("No view"));

        assertNotEquals(property1, property2);
    }

    @Test
    void testToStringWithEmptyRemark() {
        Property property = new Property(
                "123 Main St",
                "Central Town",
                "Apartment",
                85.5,
                3,
                2,
                500000.0,
                Optional.empty()
        );

        String expected = "Property[address=123 Main St, town=Central Town, type=Apartment, "
                + "size=85.50, bedrooms=3, bathrooms=2, price=500000.00, remark=No remark]";

        assertEquals(expected, property.toString());
    }
}
