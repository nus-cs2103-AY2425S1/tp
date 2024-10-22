package seedu.edulog.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NumericUtilTest {

    //---------------- Tests for isNumeric --------------------------------------

    @Test
    public void isNumeric_positiveIntegers_returnsTrue() {
        assertTrue(NumericUtil.isNumeric("1"));
        assertTrue(NumericUtil.isNumeric("2"));
        assertTrue(NumericUtil.isNumeric("4"));
        assertTrue(NumericUtil.isNumeric("9999"));

        // leading zero positive integers may be accepted by leniency
        assertTrue(NumericUtil.isNumeric("00002"));
        assertTrue(NumericUtil.isNumeric("00102"));
    }

    @Test
    public void isNumeric_zero_returnsTrue() {
        assertTrue(NumericUtil.isNumeric("0"));
        assertTrue(NumericUtil.isNumeric("000"));
    }

    @Test
    public void isNumeric_negativeIntegers_returnsFalse() {
        assertFalse(NumericUtil.isNumeric("-1"));
        assertFalse(NumericUtil.isNumeric("-111"));
        assertFalse(NumericUtil.isNumeric("-012"));
    }

    @Test
    public void isNumeric_decimals_returnsFalse() {
        assertFalse(NumericUtil.isNumeric("1.5982"));
        assertFalse(NumericUtil.isNumeric("1....90"));
        assertFalse(NumericUtil.isNumeric("4.1"));
        assertFalse(NumericUtil.isNumeric("-0.1"));
    }

    @Test
    public void isNumeric_nonNumber_returnsFalse() {
        // no numbers at all
        assertFalse(NumericUtil.isNumeric("abcde"));
        assertFalse(NumericUtil.isNumeric("abc%def"));

        // spaces between numbers
        assertFalse(NumericUtil.isNumeric("1 0"));

        // mixed alphanumeric string
        assertFalse(NumericUtil.isNumeric("abc123"));
    }
}
