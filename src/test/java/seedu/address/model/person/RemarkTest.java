package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RemarkTest {
    @Test
    public void equals() {
        Remark remark = new Remark("Hello");
        assertTrue(remark.equals(remark));

        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        assertFalse(remark.equals(1));

        assertFalse(remark.equals(null));

        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.equals(differentRemark));
    }
}
