package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StarredStatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StarredStatus(null));
    }

    @Test
    public void constructor_invalidStarredStatus_throwsIllegalArgumentException() {
        String invalidStarredStatus = "foo";
        assertThrows(IllegalArgumentException.class, () -> new StarredStatus(invalidStarredStatus));
    }

    @Test
    public void isValidStarredStatus() {
        // null starred status
        assertThrows(NullPointerException.class, () -> StarredStatus.isValidStarredStatus(null));

        // invalid starred status
        assertFalse(StarredStatus.isValidStarredStatus("")); // empty string
        assertFalse(StarredStatus.isValidStarredStatus(" ")); // spaces only

        // valid starred statuses
        assertTrue(StarredStatus.isValidStarredStatus("true"));
        assertTrue(StarredStatus.isValidStarredStatus("false"));
    }

    @Test
    public void equals() {
        StarredStatus starredStatus = new StarredStatus("true");

        // same values -> returns true
        assertTrue(starredStatus.equals(new StarredStatus("true")));

        // same object -> returns true
        assertTrue(starredStatus.equals(starredStatus));

        // null -> returns false
        assertFalse(starredStatus.equals(null));

        // different types -> returns false
        assertFalse(starredStatus.equals(5.0f));

        // different values -> returns false
        assertFalse(starredStatus.equals(new StarredStatus("false")));
    }
}


