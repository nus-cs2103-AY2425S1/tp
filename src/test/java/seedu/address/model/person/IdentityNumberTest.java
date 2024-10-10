package seedu.address.model.person;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IdentityNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new IdentityNumber(null));
    }

    @Test
    public void constructor_invalidIdentificationNumber_throwsIllegalArgumentException() {
        String invalidIdentificationNumber = "";
        assertThrows(IllegalArgumentException.class, () -> new IdentityNumber(invalidIdentificationNumber));
    }
}
