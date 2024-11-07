package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class WeddingDateTest {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WeddingDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "oibfoiebrwer";
        assertThrows(DateTimeException.class, () -> new WeddingDate(LocalDate.parse(invalidDate, formatter)));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> WeddingDate.isValidWeddingDate(null));

        // blank date
        assertFalse(WeddingDate.isValidWeddingDate(""));
        assertFalse(WeddingDate.isValidWeddingDate(" "));

        // missing parts
        assertFalse(WeddingDate.isValidWeddingDate("1/1/"));
        assertFalse(WeddingDate.isValidWeddingDate("10//2023"));
        assertFalse(WeddingDate.isValidWeddingDate("/5/2034"));
        assertFalse(WeddingDate.isValidWeddingDate("1/5/"));
        assertFalse(WeddingDate.isValidWeddingDate("//"));

        // invalid parts
        assertFalse(WeddingDate.isValidWeddingDate("1/10/0001"));
        assertFalse(WeddingDate.isValidWeddingDate("0/1/2023"));
        assertFalse(WeddingDate.isValidWeddingDate("1/45/2300"));
        assertFalse(WeddingDate.isValidWeddingDate("00/00/0000"));
        assertFalse(WeddingDate.isValidWeddingDate("*/0-u*()1"));

        // valid dates
        assertTrue(WeddingDate.isValidWeddingDate("10/01/2000"));
        assertTrue(WeddingDate.isValidWeddingDate("03/01/0023"));
        assertTrue(WeddingDate.isValidWeddingDate("10/05/2300"));
        assertTrue(WeddingDate.isValidWeddingDate("01/02/0001"));
    }

    @Test
    public void equals() {
        WeddingDate weddingDate = new WeddingDate(LocalDate.parse("12/12/2024", formatter));

        // same values -> returns true
        assertTrue(weddingDate.equals(new WeddingDate(LocalDate.parse("12/12/2024", formatter))));

        // same object -> returns true
        assertTrue(weddingDate.equals(weddingDate));

        // null -> returns false
        assertFalse(weddingDate.equals(null));

        // different values -> returns false
        assertFalse(weddingDate.equals(new WeddingDate(LocalDate.parse("12/11/2024", formatter))));
    }
}
