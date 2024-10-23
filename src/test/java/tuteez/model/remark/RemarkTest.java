package tuteez.model.remark;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    void testRemarkConstructor_validRemark_success() {
        Remark remark = new Remark("This is a valid remark.");
        assertTrue("This is a valid remark.".equals(remark.toString()));
    }

    @Test
    void testEquals_differentRemark_returnsFalse() {
        Remark remark1 = new Remark("Remark 1");
        Remark remark2 = new Remark("Remark 2");
        assertFalse(remark1.equals(remark2));
    }

    @Test
    void testHashCode_sameRemark_returnsSameHashCode() {
        Remark remark1 = new Remark("Same remark.");
        Remark remark2 = new Remark("Same remark.");
        assertTrue(remark1.hashCode() == remark2.hashCode());
    }

    @Test
    void testHashCode_differentRemark_returnsDifferentHashCode() {
        Remark remark1 = new Remark("Remark 1");
        Remark remark2 = new Remark("Remark 2");
        assertFalse(remark1.hashCode() == remark2.hashCode());
    }
}
