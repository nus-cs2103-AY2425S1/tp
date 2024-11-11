package seedu.ddd.model.event.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void isValidDescriptionTest() {
        // null input
        assertThrows(java.lang.IllegalArgumentException.class, () -> new Description(""));

        // invalid description
        assertFalse(Description.isValidDescription("/Wedding at the beach")); // starts with a forward slash
        assertFalse(Description.isValidDescription("Bridal/shower event")); // contains a forward slash


        // valid addresses
        assertTrue(Description.isValidDescription("Wedding ceremony at church"));
        assertTrue(Description.isValidDescription("W")); // one character
        assertTrue(Description.isValidDescription("Romantic beachside wedding event"));

    }

    @Test
    public void equalsTest() {
        String testString1 = "A valid string";
        String testString2 = "Another valid string";
        Description nullDescription = null;
        Description description1 = new Description(testString1);
        Description description2 = new Description(testString1);
        Description description3 = new Description(testString2);

        assertFalse(description1.equals(nullDescription));
        assertTrue(description1.equals(description2));
        assertTrue(description2.equals(description1));
        assertFalse(description1.equals(description3));
    }
}
