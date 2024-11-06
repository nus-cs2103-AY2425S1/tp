package seedu.address.model.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RatingTest {

    @Test
    public void constructor_nullRating_noException() {
        Rating rating = new Rating(null);
        assertEquals("No Rating", rating.getStringValue());
    }

    @Test
    public void constructor_validRating_success() {
        Rating rating = new Rating(5);
        assertEquals(5, rating.value);
    }

    @Test
    public void constructor_invalidRating_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Rating(-1));
        assertThrows(IllegalArgumentException.class, () -> new Rating(11));
    }

    @Test
    public void isValidRating() {
        // valid ratings
        assertTrue(Rating.isValidRating(0));
        assertTrue(Rating.isValidRating(5));
        assertTrue(Rating.isValidRating(10));

        // invalid ratings
        assertFalse(Rating.isValidRating(-1));
        assertFalse(Rating.isValidRating(11));
    }

    @Test
    public void fromString_validInput_success() {
        Rating rating = Rating.fromString("7");
        assertEquals(7, rating.value);
    }

    @Test
    public void fromString_nullOrEmptyInput_returnsNoRating() {
        Rating nullRating = Rating.fromString(null);
        Rating emptyRating = Rating.fromString("");

        assertEquals("No Rating", nullRating.getStringValue());
        assertEquals("No Rating", emptyRating.getStringValue());
    }

    @Test
    public void fromString_invalidInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Rating.fromString("invalid"));
        assertThrows(IllegalArgumentException.class, () -> Rating.fromString("11")); // out of valid range
        assertThrows(IllegalArgumentException.class, () -> Rating.fromString("-1")); // out of valid range
    }

    @Test
    public void getStringValue() {
        Rating rating = new Rating(5);
        assertEquals("5", rating.getStringValue());

        Rating noRating = new Rating(null);
        assertEquals("No Rating", noRating.getStringValue());
    }

    @Test
    public void testEquals() {
        Rating rating1 = new Rating(5);
        Rating rating2 = new Rating(5);
        Rating differentRating = new Rating(3);

        // same values -> returns true
        assertEquals(rating1.value, rating2.value);

        // null -> returns false
        assertNotNull(rating1.value);

        // different values -> returns false
        assertNotEquals(rating1, differentRating);
    }

    @Test
    public void testHashCode() {
        Rating rating = new Rating(5);
        assertEquals(5, rating.hashCode());
    }
}
