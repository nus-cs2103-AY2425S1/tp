package seedu.address.model.clienttype;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ClientTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClientType(null));
    }

    @Test
    public void constructor_invalidClientTypeName_throwsIllegalArgumentException() {
        String invalidClientTypeName = "";
        assertThrows(IllegalArgumentException.class, () -> new ClientType(invalidClientTypeName));
    }

    @Test
    public void isValidClientTypeName() {
        // null client type name
        assertThrows(NullPointerException.class, () -> ClientType.isValidClientTypeName(null));
    }

}
