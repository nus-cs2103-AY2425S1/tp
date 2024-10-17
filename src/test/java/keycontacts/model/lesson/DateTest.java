package keycontacts.model.lesson;

import static keycontacts.logic.commands.CommandTestUtil.VALID_DATE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void equals() {
        assertTrue(new Date(VALID_DATE).equals(new Date(VALID_DATE))); // same object
        assertTrue(new Date(VALID_DATE).equals(new Date(VALID_DATE))); // same values
        assertFalse(new Date(VALID_DATE).equals(null)); // equals null
        assertFalse(new Date(VALID_DATE).equals(new Date("15-12-2002"))); // different values
    }

    @Test
    public void compareTo() {
        Date newerDate = new Date("15-12-2022");
        Date olderDate = new Date("14-02-2022");
        assertTrue(new Date(VALID_DATE).compareTo(newerDate) < 0);
        assertTrue(new Date(VALID_DATE).compareTo(olderDate) > 0);
        assertTrue(new Date(VALID_DATE).compareTo(new Date(VALID_DATE)) == 0);
    }
}
