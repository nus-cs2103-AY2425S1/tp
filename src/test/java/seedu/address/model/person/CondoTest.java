package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class CondoTest {

    @Test
    public void constructor_validInputs_success() {
        PostalCode postalCode = new PostalCode("567510");
        UnitNumber unitNumber = new UnitNumber("10-65");
        Price price = new Price("1500000");
        Set<Tag> tags = new HashSet<>(Collections.singleton(new Tag("Spacious")));

        Condo condo = new Condo(postalCode, unitNumber, price, tags);

        assertEquals(postalCode, condo.getPostalCode());
        assertEquals(unitNumber, condo.getUnitNumber());
        assertEquals(price, condo.getPrice());
        assertEquals(tags, condo.getTags());
    }

    @Test
    public void equals() {
        // Create a common set of tags
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Luxury"));

        // Create some Condo objects
        Condo condo1 = new Condo(new PostalCode("123456"), new UnitNumber("10-01"), new Price("500000"), tags);
        Condo condo2 = new Condo(new PostalCode("123456"), new UnitNumber("10-01"), new Price("500000"), tags);
        Condo condo3 = new Condo(new PostalCode("654321"), new UnitNumber("20-01"), new Price("750000"), tags);
        Apartment apartment1 = new Apartment(new PostalCode("123456"),
                new UnitNumber("10-01"), new Price("500000"), tags);

        // Test for equality with the same object
        assertTrue(condo1.equals(condo1)); // Same object should return true

        assertFalse(condo1.equals(apartment1)); // Different subclass should return false
        assertTrue(condo1.equals(condo2)); // Different object, same content should return true

        /*// Test for equality with a different but identical object
        assertTrue(condo1.equals(condo2)); // Different object, same content should return true

        // Test for inequality with a different Condo object
        assertFalse(condo1.equals(condo3)); // Different content should return false

        // Test for inequality with an object that is not a Condo
        assertFalse(condo1.equals(null)); // Null should return false
        assertFalse(condo1.equals(new Object())); // Different type should return false

        // Test for inequality with a different Property subclass (e.g., Bto)
        Bto bto = new Bto(new PostalCode("123456"), new UnitNumber("10-01"), new Price("500000"), tags);
        assertFalse(condo1.equals(bto)); // Different subclass should return false*/
    }

    @Test
    public void equals_differentCondo_returnsFalse() {
        PostalCode postalCode1 = new PostalCode("567510");
        PostalCode postalCode2 = new PostalCode("123456");
        UnitNumber unitNumber = new UnitNumber("10-65");
        Price price = new Price("1500000");
        Set<Tag> tags = new HashSet<>(Collections.singleton(new Tag("Spacious")));

        Condo condo1 = new Condo(postalCode1, unitNumber, price, tags);
        Condo condo2 = new Condo(postalCode2, unitNumber, price, tags);

        assertFalse(condo1.equals(condo2)); // different condo
    }

    @Test
    public void hashCode_sameAttributes_sameHashCode() {
        PostalCode postalCode = new PostalCode("567510");
        UnitNumber unitNumber = new UnitNumber("10-65");
        Price price = new Price("1500000");
        Set<Tag> tags = new HashSet<>(Collections.singleton(new Tag("Spacious")));

        Condo condo1 = new Condo(postalCode, unitNumber, price, tags);
        Condo condo2 = new Condo(postalCode, unitNumber, price, tags);

        assertEquals(condo1.hashCode(), condo2.hashCode()); // same attributes, same hash code
    }

    @Test
    public void toString_validCondo_correctFormat() {
        PostalCode postalCode = new PostalCode("567510");
        UnitNumber unitNumber = new UnitNumber("10-65");
        Price price = new Price("1500000");
        Set<Tag> tags = new HashSet<>(Collections.singleton(new Tag("Spacious")));

        Condo condo = new Condo(postalCode, unitNumber, price, tags);

        String expectedString = "Condo Postal Code: 567510;  Unit Number: 10-65;  Price: 1500000;  "
                + "Actual Price: 0; Tags: [Spacious]";
        assertEquals(expectedString, condo.toString());
    }
}
