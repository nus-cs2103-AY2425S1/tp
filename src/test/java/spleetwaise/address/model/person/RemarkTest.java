package spleetwaise.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import spleetwaise.commons.testutil.Assert;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemark_throwsIllegalArgumentException() {
        String invalidRemark = "a".repeat(Remark.MAX_LENGTH + 1);
        Assert.assertThrows(IllegalArgumentException.class, () -> new Remark(invalidRemark));
    }

    @Test
    public void isValidRemark() {
        // null remark
        Assert.assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));

        // invalid remark
        assertFalse(Remark.isValidRemark("a".repeat(Remark.MAX_LENGTH + 1))); // exceeds max length

        // valid remark
        assertTrue(Remark.isValidRemark("")); // empty string
        assertTrue(Remark.isValidRemark("peter jack")); // alphabets only
        assertTrue(Remark.isValidRemark("12345")); // numbers only
        assertTrue(Remark.isValidRemark("peter the 2nd")); // alphanumeric characters
        assertTrue(Remark.isValidRemark("Capital Tan")); // with capital letters
        assertTrue(Remark.isValidRemark("a".repeat(Remark.MAX_LENGTH))); // exactly max length
        assertTrue(Remark.isValidRemark("David Roger Jackson Ray Jr 2nd is bankrupt")); // long remarks
        assertTrue(Remark.isValidRemark("peter*")); // contains non-alphanumeric characters
        assertTrue(Remark.isValidRemark("Jack's mortgage isn't paid yet")); // contains apostrophe
        assertTrue(Remark.isValidRemark("Jack's mortgage is due on 12/12/2022")); // contains forward slash
        assertTrue(Remark.isValidRemark("Jack's mortgage is due on 12/12/2022, not paid")); // contains comma
        assertTrue(Remark.isValidRemark("Jack's mortgage is due on 12/12/2022, not paid.")); // contains full stop
    }

    @Test
    public void equals() {
        Remark remark = new Remark("Valid Remark");

        // same values -> returns true
        assertTrue(remark.equals(new Remark("Valid Remark")));

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different types -> returns false
        assertFalse(remark.equals(5.0f));

        // different values -> returns false
        assertFalse(remark.equals(new Remark("Other Valid Remark")));
    }
}
