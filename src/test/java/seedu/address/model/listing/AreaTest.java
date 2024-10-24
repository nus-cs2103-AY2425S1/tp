package seedu.address.model.listing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AreaTest {

    @Test
    public void equals() {
        Area area = new Area(3);

        // same values -> returns true
        assertTrue(area.equals(new Area(3)));

        // same object -> returns true
        assertTrue(area.equals(area));

        // null -> returns false
        assertFalse(area.equals(null));

        // different types -> returns false
        assertFalse(area.equals(5.0f));

        // different values -> returns false
        assertFalse(area.equals(new Area(1)));
    }
}
