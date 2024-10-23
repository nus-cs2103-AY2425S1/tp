package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EtaTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Eta(null));
    }

    @Test
    public void constructor_invalidEta_throwsIllegalArgumentException() {
        String invalidEta = "";
        assertThrows(IllegalArgumentException.class, () -> new Eta(invalidEta));
    }

    @Test
    public void isValidEta() {
        // null date
        assertThrows(NullPointerException.class, () -> Eta.isValidEta(null));

        // invalid date
        assertFalse(Eta.isValidEta(""));
        assertFalse(Eta.isValidEta(" "));
        assertFalse(Eta.isValidEta("23-12-2024"));
        assertFalse(Eta.isValidEta("12-23-2024"));

        // valid date
        assertTrue(Eta.isValidEta("2024-12-23"));
        assertTrue(Eta.isValidEta("2103-03-21"));
    }

}
