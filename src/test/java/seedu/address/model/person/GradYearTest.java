package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GradYearTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GradYear(null));
    }

    @Test
    public void constructor_invalidGradYear_throwsIllegalArgumentException() {
        String invalidGradYear = "22123";
        assertThrows(IllegalArgumentException.class, () -> new GradYear(invalidGradYear));
    }

    @Test
    public void isValidGradYear() {
        // null gradYear
        assertThrows(NullPointerException.class, () -> GradYear.isValidGradYear(null));

        // invalid gradYears
        assertFalse(GradYear.isValidGradYear("#2003")); // additional special character #
        assertFalse(GradYear.isValidGradYear("17")); // incorrect digits
        assertFalse(GradYear.isValidGradYear("22222")); // incorrect digits
        assertFalse(GradYear.isValidGradYear("3222")); // first digit is not 2

        // valid gradYears
        assertTrue(GradYear.isValidGradYear("2027"));
    }

    @Test
    public void equals() {
        String validGradYear = "2027";
        String otherGradYear = "2028";

        GradYear gradYear = new GradYear(validGradYear);

        // same values -> returns true
        assertTrue(gradYear.equals(new GradYear(validGradYear)));

        // same object -> returns true
        assertTrue(gradYear.equals(gradYear));

        // null -> returns false
        assertFalse(gradYear.equals(null));

        // different types -> returns false
        assertFalse(gradYear.equals(5.0f));

        // different values -> returns false
        assertFalse(gradYear.equals(new GradYear(otherGradYear)));
    }
}
