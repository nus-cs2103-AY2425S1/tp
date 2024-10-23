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

public class PropertyTest {

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
    private Property property1;
    private Property property2;

    @BeforeEach
    public void setUp() {
        // Assuming the constructors for PostalCode, UnitNumber, and Price are simple and available
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

        property1 = new Property(postalCode1, unitNumber1, price1, tags1) {};
        property2 = new Property(postalCode2, unitNumber2, price2, tags2) {};
    }

    @Test
    public void getPostalCode_success() {
        assertEquals(postalCode1, property1.getPostalCode());
    }

    @Test
    public void getUnitNumber_success() {
        assertEquals(unitNumber1, property1.getUnitNumber());
    }

    @Test
    public void getPrice_success() {
        assertEquals(price1, property1.getPrice());
    }

    @Test
    public void getTags_success() {
        assertEquals(Collections.unmodifiableSet(tags1), property1.getTags());
    }

    /*@Test
    public void isSameProperty_sameProperty_success() {
        Property sameProperty = new Property(postalCode1, unitNumber1, price1, tags1) {};
        assertTrue(property1.isSameProperty(sameProperty));
    }*/

    @Test
    public void isSameProperty_differentProperties_failure() {
        assertFalse(property1.isSameProperty(property2));
    }

    @Test
    public void equals_sameObject_success() {
        assertTrue(property1.equals(property1));
    }

    @Test
    public void equals_nullObject_failure() {
        assertFalse(property1.equals(null));
    }

    @Test
    public void equals_differentTypes_failure() {
        assertFalse(property1.equals("String"));
    }

    @Test
    public void equals_differentProperties_failure() {
        assertFalse(property1.equals(property2));
    }

    @Test
    public void equals_sameProperties_success() {
        Property sameProperty = new Property(postalCode1, unitNumber1, price1, tags1) {};
        assertTrue(property1.equals(sameProperty));
    }

    @Test
    public void hashCode_sameProperties_success() {
        Property sameProperty = new Property(postalCode1, unitNumber1, price1, tags1) {};
        assertEquals(property1.hashCode(), sameProperty.hashCode());
    }

    @Test
    public void toString_success() {
        String expectedString = "Postal Code: 123456;  Unit Number: 01-01;  Price: 1000000;  "
                + "Actual Price: null; Tags: [Tag1]";
        assertEquals(expectedString, property1.toString());
    }
}



