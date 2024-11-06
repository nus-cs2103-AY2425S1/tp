package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LastPaidDateTest {
    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "invalid date";
        assertThrows(IllegalArgumentException.class, () -> new LastPaidDate(invalidDate));
    }

    @Test
    public void constructor_validDate_createsLastPaidDate() {
        String validDate = "07 11 2024";
        LastPaidDate lastPaidDate = new LastPaidDate(validDate);
        assertEquals(validDate, lastPaidDate.toString());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        LastPaidDate lastPaidDate = new LastPaidDate("07 11 2024");
        assertEquals(lastPaidDate, lastPaidDate);
    }

    @Test
    public void equals_differentObjectSameValue_returnsTrue() {
        LastPaidDate lastPaidDate1 = new LastPaidDate("07 11 2024");
        LastPaidDate lastPaidDate2 = new LastPaidDate("07 11 2024");
        assertEquals(lastPaidDate1, lastPaidDate2);
    }

    @Test
    public void equals_differentObjectDifferentValue_returnsFalse() {
        LastPaidDate lastPaidDate1 = new LastPaidDate("07 11 2024");
        LastPaidDate lastPaidDate2 = new LastPaidDate("08 11 2024");
        assertEquals(false, lastPaidDate1.equals(lastPaidDate2));
    }
}
