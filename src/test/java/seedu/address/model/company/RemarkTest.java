package seedu.address.model.company;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RemarkTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemark_throwsIllegalArgumentException() {
        String invalidRemark = "D4xacLPJRNcpzdMJo6VVTAZJmEwFxPK0YWONE6cd3B5IqCya1dJA26NH8"
                + "JYNvp5wtFp1ZxGzw3lUNSJBtwWTLCNX1kuEGzRw5CbnqHwPKDIdeDrSsNwTf8Hc3YHrNd"
                + "u3fkmKdytxx2s1ZnPo9t9V76lgY8o88b2IMlAXUviDTNmNgjTxTBcVtKb4IcQMviHKpus"
                + "EGlpm11111";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidRemark));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));

        // valid phone numbers
        assertTrue(Remark.isValidRemark("")); // blank remark
        assertTrue(Remark.isValidRemark("Hello world"));
    }

    @Test
    public void equals() {
        Remark remark = new Remark("hello world");

        // same values -> returns true
        assertTrue(remark.equals(new Remark("hello world")));

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different types -> returns false
        assertFalse(remark.equals(5.0f));

        // different values -> returns false
        assertFalse(remark.equals(new Remark("hello world!")));
    }

    @Test
    public void getRemarkValue_validRemark_returnsValue() {
        Remark remark = new Remark("hello world");
        assertTrue(remark.getRemarkValue().equals("hello world"));
    }

    @Test
    public void getRemarkValue_emptyRemark_returnsDefaultValue() {
        Remark remark = new Remark("");
        assertTrue(remark.getRemarkValue().equals("None"));
    }
}
