package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class OtherPropertyTest {

    private PostalCode postalCode1;
    private PostalCode postalCode2;
    private UnitNumber unitNumber1;
    private UnitNumber unitNumber2;
    private Price price1;
    private Price price2;
    private Tag tag1;
    private Tag tag2;
    private Set<Tag> tags1;
    private Set<Tag> tags2;
    private OtherProperty otherProperty1;
    private OtherProperty otherProperty2;

    @BeforeEach
    public void setUp() {
        postalCode1 = new PostalCode("123456");
        postalCode2 = new PostalCode("654321");
        unitNumber1 = new UnitNumber("01-01");
        unitNumber2 = new UnitNumber("02-02");
        price1 = new Price("1000000");
        price2 = new Price("2000000");

        tag1 = new Tag("Tag1");
        tag2 = new Tag("Tag2");

        tags1 = new HashSet<>();
        tags1.add(tag1);
        tags2 = new HashSet<>();
        tags2.add(tag2);

        otherProperty1 = new OtherProperty(postalCode1, unitNumber1, price1, tags1);
        otherProperty2 = new OtherProperty(postalCode2, unitNumber2, price2, tags2);
    }

    @Test
    public void equals() {
        // Create a common set of tags
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Luxurious"));

        // Create some OtherProperty objects
        OtherProperty otherProperty1 = new OtherProperty(new PostalCode("123456"),
                new UnitNumber("01-01"), new Price("500000"), tags);
        OtherProperty otherProperty2 = new OtherProperty(new PostalCode("123456"),
                new UnitNumber("01-01"), new Price("500000"), tags);
        OtherProperty otherProperty3 = new OtherProperty(new PostalCode("654321"),
                new UnitNumber("02-02"), new Price("600000"), tags);
        Apartment apartment1 = new Apartment(new PostalCode("123456"), new UnitNumber("01-01"),
                new Price("500000"), tags);

        // Test for equality with the same object
        assertTrue(otherProperty1.equals(otherProperty1)); // Same object should return true
        assertFalse(otherProperty1.equals(apartment1)); // Different subclass should return false
        assertTrue(otherProperty1.equals(otherProperty2)); // Different object, same content should return true

        /*// Test for equality with a different but identical object
        assertTrue(otherProperty1.equals(otherProperty2)); // Different object, same content should return true

        // Test for inequality with a different OtherProperty object
        assertFalse(otherProperty1.equals(otherProperty3)); // Different content should return false

        // Test for inequality with an object that is not an OtherProperty
        assertFalse(otherProperty1.equals(null)); // Null should return false
        assertFalse(otherProperty1.equals(new Object())); // Different type should return false
        // Test for inequality with a different Property subclass (e.g., Bto)
        Bto bto = new Bto(new PostalCode("123456"), new UnitNumber("01-01"), new Price("500000"), tags);
        assertFalse(otherProperty1.equals(bto)); // Different subclass should return false
        */
    }
    @Test
    public void getPostalCode_success() {
        assertEquals(postalCode1, otherProperty1.getPostalCode());
    }

    @Test
    public void getUnitNumber_success() {
        assertEquals(unitNumber1, otherProperty1.getUnitNumber());
    }

    @Test
    public void getPrice_success() {
        assertEquals(price1, otherProperty1.getPrice());
    }

    @Test
    public void getTags_success() {
        assertEquals(Collections.unmodifiableSet(tags1), otherProperty1.getTags());
    }

    @Test
    public void equals_nullObject_failure() {
        assertFalse(otherProperty1.equals(null));
    }

    @Test
    public void equals_differentTypes_failure() {
        assertFalse(otherProperty1.equals("String"));
    }

    @Test
    public void equals_differentOtherProperty_failure() {
        assertFalse(otherProperty1.equals(otherProperty2));
    }

    @Test
    public void hashCode_sameOtherProperty_success() {
        OtherProperty sameOtherProperty = new OtherProperty(postalCode1, unitNumber1, price1, tags1);
        assertEquals(otherProperty1.hashCode(), sameOtherProperty.hashCode());
    }

    @Test
    public void toString_success() {
        String expectedString = "OtherProperty Postal Code: 123456;  Unit Number: 01-01;  Price: 1000000;  "
                + "Actual Price: null; Tags: [Tag1]";
        assertEquals(expectedString, otherProperty1.toString());
    }
}
