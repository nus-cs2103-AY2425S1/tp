package seedu.edulog.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edulog.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.edulog.model.calendar.Description;
public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_emptyDescription_throwsIllegalArgumentException() {
        String invalidDescriptionEmpty = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescriptionEmpty));

    }

    @Test
    public void checkEmptyDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.checkEmptyDescription(null));

        // represents description length.
        int[] boundaryValues = {0, 1, Description.MAX_CHARACTER_LIMIT + 100};

        for (int descriptionLength: boundaryValues) {
            String testDescription = "X".repeat(descriptionLength);

            if (descriptionLength == 0) {
                assertTrue(Description.checkEmptyDescription(testDescription));
            } else {
                assertFalse(Description.checkEmptyDescription(testDescription));
            }
        }
    }

    @Test
    public void checkTooLongDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.checkTooLongDescription(null));

        // represents description length.
        int[] boundaryValues = {0, 1, Description.MAX_CHARACTER_LIMIT / 2, Description.MAX_CHARACTER_LIMIT,
            Description.MAX_CHARACTER_LIMIT + 1, Description.MAX_CHARACTER_LIMIT + 100};

        for (int descriptionLength: boundaryValues) {
            String testDescription = "X".repeat(descriptionLength);

            if (descriptionLength > Description.MAX_CHARACTER_LIMIT) {
                assertTrue(Description.checkTooLongDescription(testDescription));
            } else {
                assertFalse(Description.checkTooLongDescription(testDescription));
            }
        }
    }

    @Test
    public void equals() {
        Description description = new Description("Valid Description");

        // same values -> returns true
        assertTrue(description.equals(new Description("Valid Description")));

        // same object -> returns true
        assertTrue(description.equals(description));

        // different case description -> returns true
        assertTrue(description.equals(new Description("valid DESCRIPTION")));

        // null -> returns false
        assertFalse(description.equals(null));

        // different types -> returns false
        assertFalse(description.equals(5.0f));

        // different values -> returns false
        assertFalse(description.equals(new Description("Other Valid Description")));

        // trailing spaces -> returns false
        assertFalse(description.equals(new Description(" Valid Description")));
    }
}
