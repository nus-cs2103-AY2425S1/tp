package seedu.address.model.person;

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
    public void isValidRemark() {
        // null remark
        assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));

        // invalid remark
        assertFalse(Remark.isValidRemark("")); // empty string
        assertFalse(Remark.isValidRemark("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12345678910"
                + "1112131415161718192021222324252627282930abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "123456789101112131415161718192021222324252627282930")); // more than 200 characters


        // valid remark
        assertTrue(Remark.isValidRemark("a")); // 1 character
        assertTrue(Remark.isValidRemark("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12345678910"
                + "1112131415161718192021222324252627282930abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "123456789101112131415161718192021222324252627")); // 200 characters
        assertTrue(Remark.isValidRemark("abc")); // consists of letters only
        assertTrue(Remark.isValidRemark("123")); // consists of numbers only
        assertTrue(Remark.isValidRemark("abc123")); // consists of letters and numbers
        assertTrue(Remark.isValidRemark("@abc_")); // includes special characters

    }

    @Test
    public void equals() {
        Remark remark = new Remark("likes cats");

        // same values -> returns true
        assertTrue(remark.equals(new Remark("likes cats")));

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different types -> returns false
        assertFalse(remark.equals(5.0f));

        // different values -> returns false
        assertFalse(remark.equals(new Remark("likes dogs")));
    }
}


