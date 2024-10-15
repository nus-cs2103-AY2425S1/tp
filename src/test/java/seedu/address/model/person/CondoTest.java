package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

    /*@Test
    public void equals_sameCondo_returnsTrue() {
        PostalCode postalCode = new PostalCode("567510");
        UnitNumber unitNumber = new UnitNumber("10-65");
        Price price = new Price("1500000");
        Set<Tag> tags = new HashSet<>(Collections.singleton(new Tag("Spacious")));

        Condo condo1 = new Condo(postalCode, unitNumber, price, tags);
        Condo condo2 = new Condo(postalCode, unitNumber, price, tags);

        assertTrue(condo1.equals(condo2)); // same condo
    }*/

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

        String expectedString = "[567510] Unit Number: 10-65";
        assertEquals(expectedString, condo.toString());
    }

    /*@Test
    public void constructor_nullInputs_throwsNullPointerException() {
        PostalCode postalCode = new PostalCode("567510");
        UnitNumber unitNumber = new UnitNumber("10-65");
        Price price = new Price("1500000");
        Set<Tag> tags = new HashSet<>(Collections.singleton(new Tag("Spacious")));

        // Null postal code
        assertThrows(NullPointerException.class, () -> new Condo(null, unitNumber, price, tags));

        // Null unit number
        assertThrows(NullPointerException.class, () -> new Condo(postalCode, null, price, tags));

        // Null price
        assertThrows(NullPointerException.class, () -> new Condo(postalCode, unitNumber, null, tags));

        // Null tags
        assertThrows(NullPointerException.class, () -> new Condo(postalCode, unitNumber, price, null));
    }*/
}
