package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

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
    public void constructor_validLocalDate_createsLastPaidDate() {
        LocalDate validDate = LocalDate.of(2024, 11, 7);
        LastPaidDate lastPaidDate = new LastPaidDate(validDate);
        assertEquals(validDate.toString(), lastPaidDate.toString());
        assertEquals(validDate, lastPaidDate.date);
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

    @Test
    public void equals_differentObjectDifferentType_returnsFalse() {
        LastPaidDate lastPaidDate = new LastPaidDate("07 11 2024");
        Frequency frequency = new Frequency("3");
        assertEquals(false, lastPaidDate.equals(frequency));
    }
    @Test
    public void toStringMethod() {
        LastPaidDate lastPaidDate = new LastPaidDate("07 11 2024");
        assertEquals("07 11 2024", lastPaidDate.toString());
    }

    @Test
    public void hashCodeMethod() {
        LastPaidDate lastPaidDate = new LastPaidDate("07 11 2024");
        assertEquals("07 11 2024".hashCode(), lastPaidDate.hashCode());
    }
}
