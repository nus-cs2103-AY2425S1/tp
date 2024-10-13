package seedu.address.model.issue;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IssueTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Issue(null));
    }

    @Test
    public void constructor_invalidIssueName_throwsIllegalArgumentException() {
        String invalidIssueName = "";
        assertThrows(IllegalArgumentException.class, () -> new Issue(invalidIssueName));
    }

    @Test
    public void isValidIssueName() {
        // null issue name
        assertThrows(NullPointerException.class, () -> Issue.isValidIssueName(null));
    }

}
