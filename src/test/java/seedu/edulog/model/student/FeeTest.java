package seedu.edulog.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.edulog.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;



public class FeeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Fee(null));
    }

    @Test
    public void testInvalidArguments_on_constructor() {
        // EP: exceed boundary values
        assertThrows(IllegalArgumentException.class, () -> new Fee(1000000));
        assertThrows(IllegalArgumentException.class, () -> new Fee("1000000"));
        assertThrows(IllegalArgumentException.class, () -> new Fee(0));
        assertThrows(IllegalArgumentException.class, () -> new Fee("0"));

        // EP: negative values
        assertThrows(IllegalArgumentException.class, () -> new Fee(-1000));
        assertThrows(IllegalArgumentException.class, () -> new Fee("-1000"));

        // EP: invalid strings
        assertThrows(IllegalArgumentException.class, () -> new Fee("abcde"));
        assertThrows(IllegalArgumentException.class, () -> new Fee("1sa"));
    }

    @Test
    public void testValidArguments_on_constructor() {
        try {
            // EP: valid boundary values
            Fee fee1 = new Fee("999999");
            Fee fee2 = new Fee(999999);
            Fee fee3 = new Fee("1");
            Fee fee4 = new Fee(1);

            // EP: Typical values
            Fee fee5 = new Fee("500");
            Fee fee6 = new Fee(300);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testIsValidFee_onInvalidArguments() {
        assertFalse(Fee.isValidFee("1000000"));
        assertFalse(Fee.isValidFee(1000000));
        assertFalse(Fee.isValidFee("-999999"));
        assertFalse(Fee.isValidFee(-999999));
        assertFalse(Fee.isValidFee("abcde"));
    }

    @Test
    public void testEquals() {
        assertEquals(new Fee("1000"), new Fee("1000"));
        assertEquals(new Fee("1000"), new Fee(1000));

        assertNotEquals(new Fee("1000"), new Fee(100));
        assertNotEquals(new Fee(1000), new Fee("100"));
    }
}
