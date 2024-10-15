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

public class BtoTest {

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
    private Bto bto1;
    private Bto bto2;

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

        bto1 = new Bto(postalCode1, unitNumber1, price1, tags1);
        bto2 = new Bto(postalCode2, unitNumber2, price2, tags2);
    }

    @Test
    public void getPostalCode_success() {
        assertEquals(postalCode1, bto1.getPostalCode());
    }

    @Test
    public void getUnitNumber_success() {
        assertEquals(unitNumber1, bto1.getUnitNumber());
    }

    @Test
    public void getPrice_success() {
        assertEquals(price1, bto1.getPrice());
    }

    @Test
    public void getTags_success() {
        assertEquals(Collections.unmodifiableSet(tags1), bto1.getTags());
    }

    @Test
    public void equals_sameObject_success() {
        assertTrue(bto1.equals(bto1));
    }

    @Test
    public void equals_nullObject_failure() {
        assertFalse(bto1.equals(null));
    }

    @Test
    public void equals_differentTypes_failure() {
        assertFalse(bto1.equals("String"));
    }

    @Test
    public void equals_differentBto_failure() {
        assertFalse(bto1.equals(bto2));
    }

    @Test
    public void equals_sameBto_success() {
        Bto sameBto = new Bto(postalCode1, unitNumber1, price1, tags1);
        assertTrue(bto1.equals(sameBto));
    }

    @Test
    public void hashCode_sameBto_success() {
        Bto sameBto = new Bto(postalCode1, unitNumber1, price1, tags1);
        assertEquals(bto1.hashCode(), sameBto.hashCode());
    }

    @Test
    public void toString_success() {
        String expectedString = "[123456] Unit Number: 01-01";
        assertEquals(expectedString, bto1.toString());
    }
}
