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

public class HdbTest {

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
    private Hdb hdb1;
    private Hdb hdb2;

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

        hdb1 = new Hdb(postalCode1, unitNumber1, price1, tags1);
        hdb2 = new Hdb(postalCode2, unitNumber2, price2, tags2);
    }

    @Test
    public void equals() {
        // Create a common set of tags
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Affordable"));

        // Create some Hdb objects
        Hdb hdb1 = new Hdb(new PostalCode("123456"), new UnitNumber("01-01"), new Price("300000"), tags);
        Hdb hdb2 = new Hdb(new PostalCode("123456"), new UnitNumber("01-01"), new Price("300000"), tags);
        Hdb hdb3 = new Hdb(new PostalCode("654321"), new UnitNumber("02-02"), new Price("350000"), tags);
        Apartment apartment1 = new Apartment(new PostalCode("123456"), new UnitNumber("01-01"),
                new Price("300000"), tags);

        // Test for equality with the same object
        assertTrue(hdb1.equals(hdb1)); // Same object should return true
        assertFalse(hdb1.equals(apartment1)); // Different subclass should return false
        assertTrue(hdb1.equals(hdb2)); // Different object, same content should return true

        /*// Test for equality with a different but identical object
        assertTrue(hdb1.equals(hdb2)); // Different object, same content should return true

        // Test for inequality with a different Hdb object
        assertFalse(hdb1.equals(hdb3)); // Different content should return false

        // Test for inequality with an object that is not an Hdb
        assertFalse(hdb1.equals(null)); // Null should return false
        assertFalse(hdb1.equals(new Object())); // Different type should return false

        // Test for inequality with a different Property subclass (e.g., Bto)
        Bto bto = new Bto(new PostalCode("123456"), new UnitNumber("01-01"), new Price("300000"), tags);
        assertFalse(hdb1.equals(bto)); // Different subclass should return false*/
    }

    @Test
    public void getPostalCode_success() {
        assertEquals(postalCode1, hdb1.getPostalCode());
    }

    @Test
    public void getUnitNumber_success() {
        assertEquals(unitNumber1, hdb1.getUnitNumber());
    }

    @Test
    public void getPrice_success() {
        assertEquals(price1, hdb1.getPrice());
    }

    @Test
    public void getTags_success() {
        assertEquals(Collections.unmodifiableSet(tags1), hdb1.getTags());
    }

    /*@Test
    public void equals_sameObject_success() {
        assertTrue(hdb1.equals(hdb1));
    }*/

    @Test
    public void equals_nullObject_failure() {
        assertFalse(hdb1.equals(null));
    }

    @Test
    public void equals_differentTypes_failure() {
        assertFalse(hdb1.equals("String"));
    }

    @Test
    public void equals_differentHdb_failure() {
        assertFalse(hdb1.equals(hdb2));
    }

    /*@Test
    public void equals_sameHdb_success() {
        Hdb sameHdb = new Hdb(postalCode1, unitNumber1, price1, tags1);
        assertTrue(hdb1.equals(sameHdb));
    }*/

    @Test
    public void hashCode_sameHdb_success() {
        Hdb sameHdb = new Hdb(postalCode1, unitNumber1, price1, tags1);
        assertEquals(hdb1.hashCode(), sameHdb.hashCode());
    }

    @Test
    public void toString_success() {
        String expectedString = "Hdb Postal Code: 123456;  Unit Number: 01-01;  Price: 1000000;  "
                + "Actual Price: 0; Tags: [Tag1]";
        assertEquals(expectedString, hdb1.toString());
    }
}
