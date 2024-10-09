package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ClientTypesTest {

    @Test
    public void getType_buyer_returnsCorrectString() {
        assertEquals("buyer", ClientTypes.BUYER.getType());
    }

    @Test
    public void getType_seller_returnsCorrectString() {
        assertEquals("seller", ClientTypes.SELLER.getType());
    }

    @Test
    public void isValidClientType_validBuyer_returnsTrue() {
        assertTrue(ClientTypes.isValidClientType("BUYER"));
    }

    @Test
    public void isValidClientType_validSeller_returnsTrue() {
        assertTrue(ClientTypes.isValidClientType("SELLER"));
    }

    @Test
    public void isValidClientType_invalidType_returnsFalse() {
        assertFalse(ClientTypes.isValidClientType("INVALID"));
    }

    @Test
    public void isValidClientType_lowerCaseBuyer_returnsFalse() {
        assertFalse(ClientTypes.isValidClientType("buyer")); // Case-sensitive check
    }

    @Test
    public void isValidClientType_lowerCaseSeller_returnsFalse() {
        assertFalse(ClientTypes.isValidClientType("seller")); // Case-sensitive check
    }

    @Test
    public void isValidClientType_nullValue_returnsFalse() {
        assertFalse(ClientTypes.isValidClientType(null));
    }

    @Test
    public void isValidClientType_emptyString_returnsFalse() {
        assertFalse(ClientTypes.isValidClientType(""));
    }
}
