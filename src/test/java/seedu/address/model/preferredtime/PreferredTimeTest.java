package seedu.address.model.preferredtime;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;



public class PreferredTimeTest {

    @Test
    public void isValidPreferredTime() {
        // TODO: implement test for PreferredTime

        // null preferredtime
        assertThrows(NullPointerException.class, () -> PreferredTime.isValidPreferredTime(null));

        // invalid preferred time

    }
}
