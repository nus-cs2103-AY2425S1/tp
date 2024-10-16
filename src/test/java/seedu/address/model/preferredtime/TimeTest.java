package seedu.address.model.preferredtime;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class TimeTest {

    @Test
    public void isValidTime() {
        // TODO: implement test for time format

        // null tag name
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));
    }
}
