package seedu.address.model.clienttype;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Test
    public void isEqualClientType() {
        ClientType defaultClientType = new ClientType("investment");
        ClientType defaultClientType2 = new ClientType("investment1");
        assertTrue(defaultClientType.equals(defaultClientType));
        assertFalse(defaultClientType.equals(defaultClientType2));

    }

}
