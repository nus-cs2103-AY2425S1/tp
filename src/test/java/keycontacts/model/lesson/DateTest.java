package keycontacts.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DateTest {
    Date date = new Date("06-07-2002");

    @Test
    public void equals() {
        assertTrue(date.equals(date)); // same object
        assertTrue(date.equals(new Date("06-07-2002"))); // same values
        assertFalse(date.equals(null)); // equals null
        assertFalse(date.equals(new Date("15-12-2002"))); // different values
    }

    @Test
    public void compareTo() {
        Date newerDate = new Date("15-12-2002");
        Date olderDate = new Date("14-02-2002");
        assertTrue(date.compareTo(newerDate) < 0);
        assertTrue(date.compareTo(olderDate) > 0);
    }
}
