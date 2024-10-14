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

    @Test
    public void isValidApartmentName_validNames_success() {
        assertTrue(Apartment.isValidApartmentName("Apartment1"));
        assertTrue(Apartment.isValidApartmentName("12345"));
    }

    @Test
    public void isValidApartmentName_invalidNames_failure() {
        assertFalse(Apartment.isValidApartmentName("!@#$"));
        assertFalse(Apartment.isValidApartmentName("Apartment 123")); // Contains space
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
        String expectedString = "[123456] Unit Number: 01-01";
        assertEquals(expectedString, apartment1.toString());
    }
}
