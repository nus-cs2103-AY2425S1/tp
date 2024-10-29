package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FavouriteTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Favourite(null));
    }
    @Test
    public void equals() {
        Favourite trueFavourite = new Favourite(true);
        Favourite falseFavourite = new Favourite(false);
        // same values -> returns true
        assertTrue(trueFavourite.equals(new Favourite(true)));
        assertTrue(falseFavourite.equals(new Favourite(false)));
        // same object -> returns true
        assertTrue(trueFavourite.equals(trueFavourite));
        assertTrue(falseFavourite.equals(falseFavourite));
        // null -> returns false
        assertFalse(trueFavourite.equals(null));
        assertFalse(falseFavourite.equals(null));
        // different types -> returns false
        assertFalse(trueFavourite.equals(5.0f));
        assertFalse(falseFavourite.equals(5.0f));
        // different values -> returns false
        assertFalse(trueFavourite.equals(new Favourite(false)));
        assertFalse(falseFavourite.equals(new Favourite(true)));
    }
}
