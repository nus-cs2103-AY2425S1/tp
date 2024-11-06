package seedu.address.model.company;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BookmarkTest {

    @Test
    public void equals() {
        Bookmark bookmarked = new Bookmark(true);
        Bookmark notBookmarked = new Bookmark(false);

        // same values -> returns true
        assertTrue(bookmarked.equals(new Bookmark(true)));
        assertTrue(notBookmarked.equals(new Bookmark(false)));

        // null -> returns false
        assertFalse(bookmarked.equals(null));
        assertFalse(notBookmarked.equals(null));

        // different values -> returns false
        assertFalse(bookmarked.equals(new Bookmark(false)));
        assertFalse(notBookmarked.equals(new Bookmark(true)));

        // different types -> returns false
        assertFalse(bookmarked.equals(1));
        assertFalse(notBookmarked.equals(2));
    }

    @Test
    public void prettyStringTest() {
        String expectedBookmarkedIndicator = "Bookmarked";
        String expectedNotBookmarkedIndicator = "Not bookmarked";

        Bookmark bookmarked = new Bookmark(true);
        Bookmark notBookmarked = new Bookmark(false);

        assertTrue(bookmarked.prettyString().equals(expectedBookmarkedIndicator));
        assertTrue(notBookmarked.prettyString().equals(expectedNotBookmarkedIndicator));
    }
}
