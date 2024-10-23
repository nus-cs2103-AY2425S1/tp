package seedu.ddd.model.event.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    //Assumption: The null input have been filtered out.
    @Test
    public void isValidDescriptionTest() {
        String testDescription1 = "";
        String testDescription2 = "A normal description";

        assertThrows(java.lang.IllegalArgumentException.class, () -> new Description(testDescription1));
        assertEquals(new Description(testDescription2).toString(), testDescription2);
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
