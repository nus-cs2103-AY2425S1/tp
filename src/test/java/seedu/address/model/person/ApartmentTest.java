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

public class ApartmentTest {

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
    private Apartment apartment1;
    private Apartment apartment2;

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

        apartment1 = new Apartment(postalCode1, unitNumber1, price1, tags1);
        apartment2 = new Apartment(postalCode2, unitNumber2, price2, tags2);
    }

    @Test
    public void equals() {
        // Create a common set of tags
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Luxury"));

        // Create some Apartment objects
        Apartment apartment1 = new Apartment(new PostalCode("123456"), new UnitNumber("10-01"),
                new Price("1000000"), tags);
        Apartment apartment2 = new Apartment(new PostalCode("123456"), new UnitNumber("10-01"),
                new Price("1000000"), tags);
        Apartment apartment3 = new Apartment(new PostalCode("654321"), new UnitNumber("20-01"),
                new Price("2000000"), tags);
        Bto bto1 = new Bto(new PostalCode("123456"), new UnitNumber("10-01"), new Price("1000000"), tags);

        // Test for equality with the same object
        assertTrue(apartment1.equals(apartment1)); // Same object should return true
        // Example: Asserting that two different objects are not equal (should return false)
        assertFalse(apartment1.equals(bto1));
        assertTrue(apartment1.equals(apartment2));


        /*// Test for equality with a different but identical object
        assertTrue(apartment1.equals(apartment2)); // Different object, same content should return true

        // Test for inequality with a different Apartment object
        assertFalse(apartment1.equals(apartment3)); // Different content should return false

        // Test for inequality with an object that is not an Apartment
        assertFalse(apartment1.equals(null)); // Null should return false
        assertFalse(apartment1.equals(new Object())); // Different type should return false*/
    }

    @Test
    public void getPostalCode_success() {
        assertEquals(postalCode1, apartment1.getPostalCode());
    }

    @Test
    public void getUnitNumber_success() {
        assertEquals(unitNumber1, apartment1.getUnitNumber());
    }

    @Test
    public void getPrice_success() {
        assertEquals(price1, apartment1.getPrice());
    }

    @Test
    public void getTags_success() {
        assertEquals(Collections.unmodifiableSet(tags1), apartment1.getTags());
    }

    /*@Test
    public void equals_sameObject_success() {
        assertTrue(apartment1.equals(apartment1));
    }*/

    @Test
    public void equals_nullObject_failure() {
        assertFalse(apartment1.equals(null));
    }

    @Test
    public void equals_differentTypes_failure() {
        assertFalse(apartment1.equals("String"));
    }

    @Test
    public void equals_differentApartment_failure() {
        assertFalse(apartment1.equals(apartment2));
    }

    /*@Test
    public void equals_sameApartment_success() {
        Apartment sameApartment = new Apartment(postalCode1, unitNumber1, price1, tags1);
        assertTrue(apartment1.equals(sameApartment));
    }*/

    @Test
    public void hashCode_sameApartment_success() {
        Apartment sameApartment = new Apartment(postalCode1, unitNumber1, price1, tags1);
        assertEquals(apartment1.hashCode(), sameApartment.hashCode());
    }

    @Test
    public void toString_success() {
        String expectedString = "Apartment Postal Code: 123456;  Unit Number: 01-01;  Price: 1000000;  "
                + "Actual Price: 0; Tags: [Tag1]";
        assertEquals(expectedString, apartment1.toString());
    }
}
