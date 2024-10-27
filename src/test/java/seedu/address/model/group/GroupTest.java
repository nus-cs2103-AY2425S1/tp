package seedu.address.model.group;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GroupTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Group(null));
    }

    @Test
    public void constructor_invalidGroupName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Group(invalidTagName));
    }

    @Test
    public void isValidGroupName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Group.isValidGroupName(null));
    }

}
