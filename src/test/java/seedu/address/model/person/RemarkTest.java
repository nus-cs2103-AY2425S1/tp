package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RemarkTest {
    @Test
    public void equals() {
        Remark remark = new Remark("Hello");
        // same object -> returns true
        assertTrue(remark.equals(remark));
        // same values -> returns true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));
        // different types -> returns false
        assertFalse(remark.equals(1));
        // null -> returns false
        assertFalse(remark.equals(null));
        // different remark -> returns false
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.equals(differentRemark));
    }
    @Test
    public void testToString() {
        Remark remark = new Remark("This is a test remark.");
        assertEquals("This is a test remark.", remark.toString());
    }

    @Test
    public void testHashCode_sameRemark_sameHashCode() {
        Remark remark1 = new Remark("Test hash code.");
        Remark remark2 = new Remark("Test hash code.");
        assertEquals(remark1.hashCode(), remark2.hashCode(), "Hash codes should be equal for same values");
    }

    @Test
    public void testHashCode_differentRemarks_differentHashCodes() {
        Remark remark1 = new Remark("First remark.");
        Remark remark2 = new Remark("Second remark.");
        assertNotEquals(remark1.hashCode(), remark2.hashCode(), "Hash codes should be different for different values");
    }
}
