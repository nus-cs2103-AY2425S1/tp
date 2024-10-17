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
    public void testHashCode_sameObject_consistentHashCode() {
        Remark remark = new Remark("This is a remark");

        // Hash code should remain the same when called multiple times
        int initialHashCode = remark.hashCode();
        assertEquals(initialHashCode, remark.hashCode());
        assertEquals(initialHashCode, remark.hashCode());
    }

    @Test
    public void testHashCode_equalObjects_sameHashCode() {
        Remark remark1 = new Remark("This is a remark");
        Remark remark2 = new Remark("This is a remark");

        // Two objects with the same value should have the same hash code
        assertEquals(remark1.hashCode(), remark2.hashCode());
    }

    @Test
    public void testHashCode_differentObjects_differentHashCodes() {
        Remark remark1 = new Remark("Remark 1");
        Remark remark2 = new Remark("Remark 2");

        // Objects with different values should ideally have different hash codes
        assertNotEquals(remark1.hashCode(), remark2.hashCode());
    }
}
