package seedu.address.model.addresses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PublicAddressTest {

    private static final String VALID_PUBLIC_ADDRESS_1 = "14qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd";
    private static final String VALID_PUBLIC_ADDRESS_2 = "34qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd";
    private static final String VALID_LABEL_1 = "Wallet label";
    private static final String VALID_LABEL_2 = "Other label";

    @Test
    public void toString_returnsCorrectString() {
        BtcAddress btcAddress = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        assertEquals(VALID_LABEL_1 + ": " + VALID_PUBLIC_ADDRESS_1, btcAddress.toString());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        BtcAddress btcAddress = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        assertTrue(btcAddress.equals(btcAddress));
    }

    @Test
    public void equals_equalObjects_returnsTrue() {
        BtcAddress btcAddress1 = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        BtcAddress btcAddress2 = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        assertTrue(btcAddress1.equals(btcAddress2));
    }

    @Test
    public void equals_differentObjects_returnsFalse() {
        BtcAddress btcAddress1 = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        BtcAddress btcAddress2 = new BtcAddress(VALID_PUBLIC_ADDRESS_2, VALID_LABEL_1);
        BtcAddress btcAddress3 = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_2);
        assertFalse(btcAddress1.equals(btcAddress2));
        assertFalse(btcAddress1.equals(btcAddress3));
    }

    @Test
    public void equals_null_returnsFalse() {
        BtcAddress btcAddress = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        assertFalse(btcAddress.equals(null));
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        BtcAddress btcAddress = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        assertFalse(btcAddress.equals("Not a BtcAddress"));
    }

}
