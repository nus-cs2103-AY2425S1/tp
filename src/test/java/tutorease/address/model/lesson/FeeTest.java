package tutorease.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tutorease.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tutorease.address.logic.parser.exceptions.ParseException;

public class FeeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Fee(null));
    }
    @Test
    public void constructor_invalidFee_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Fee(""));
        assertThrows(IllegalArgumentException.class, () -> new Fee("one"));
        assertThrows(IllegalArgumentException.class, () -> new Fee("-1"));
        assertThrows(IllegalArgumentException.class, () -> new Fee("1.1"));
    }
    @Test
    public void constructor_validFee() throws ParseException {
        String value = "1";
        Fee fee = new Fee(value);
        assertEquals(value, fee.getValueString());
        value = "0";
        fee = new Fee(value);
        assertEquals(value, fee.getValueString());
    }
    @Test
    public void isValidFee() {
        // null fee
        assertThrows(NullPointerException.class, () -> Fee.isValidFee(null));

        // invalid fee
        assertFalse(Fee.isValidFee(""));
        assertFalse(Fee.isValidFee("one"));
        assertFalse(Fee.isValidFee("-1"));
        assertFalse(Fee.isValidFee("1.1"));
    }
    @Test
    public void toStringTest() throws ParseException {
        String value = "1";
        Fee fee = new Fee(value);
        assertEquals("$" + value, fee.toString());
    }
    @Test
    public void equals() throws ParseException {
        Fee fee = new Fee("1");

        // same object -> returns true
        assertEquals(fee, fee);

        // same values -> returns true
        Fee feeCopy = new Fee("1");
        assertEquals(fee, feeCopy);

        // null -> returns false
        assertFalse(fee.equals(null));

        // different value -> returns false
        Fee differentFee = new Fee("2");
        assertFalse(fee.equals(differentFee));
    }
}
