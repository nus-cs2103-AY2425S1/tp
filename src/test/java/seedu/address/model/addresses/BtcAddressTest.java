package seedu.address.model.addresses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BtcAddressTest {

    private static final String VALID_PUBLIC_ADDRESS_1 = "14qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd";
    private static final String VALID_PUBLIC_ADDRESS_2 = "34qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd";
    private static final String VALID_LABEL_1 = "Wallet label";
    private static final String VALID_LABEL_2 = "Other label";

    @Test
    public void constructor_nullPublicAddress_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BtcAddress(null, VALID_LABEL_1));
    }

    @Test
    public void constructor_nullLabel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BtcAddress(VALID_PUBLIC_ADDRESS_1, null));
    }

    @Test
    public void constructor_invalidPublicAddress_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new BtcAddress("", VALID_LABEL_1));
    }

    @Test
    public void constructor_invalidLabel_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new BtcAddress(VALID_PUBLIC_ADDRESS_1, ""));
    }

    @Test
    public void isValidPublicAddress_validInputs_returnsTrue() {
        assertTrue(new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1).isValidPublicAddress(VALID_PUBLIC_ADDRESS_1,
                VALID_LABEL_1));
        assertTrue(new BtcAddress(VALID_PUBLIC_ADDRESS_2, VALID_LABEL_2).isValidPublicAddress(VALID_PUBLIC_ADDRESS_2,
                VALID_LABEL_2));
    }

    @Test
    public void isValidPublicAddress_emptyAddress_returnsFalse() {
        assertFalse(new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1).isValidPublicAddress("", VALID_LABEL_1));
    }

    @Test
    public void isValidPublicAddress_emptyLabel_returnsFalse() {
        assertFalse(
                new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1).isValidPublicAddress(VALID_PUBLIC_ADDRESS_1, ""));
    }

    @Test
    public void isValidPublicAddress_blankAddress_returnsFalse() {
        assertFalse(new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1).isValidPublicAddress(" ", VALID_LABEL_1));
    }

    @Test
    public void isValidPublicAddress_blankLabel_returnsFalse() {
        assertFalse(new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1).isValidPublicAddress(VALID_PUBLIC_ADDRESS_1,
                " "));
    }

    @Test
    public void getNetwork_returnsCorrectNetwork() {
        BtcAddress btcAddress = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        assertEquals(Network.BTC, btcAddress.getNetwork());
    }

    @Test
    public void toString_returnsCorrectString() {
        BtcAddress btcAddress = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        assertEquals(VALID_LABEL_1 + ": " + VALID_PUBLIC_ADDRESS_1, btcAddress.toString());
    }

    @Test
    public void equals_returnsTrueForSameObject() {
        BtcAddress btcAddress = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        assertTrue(btcAddress.equals(btcAddress));
    }

    @Test
    public void equals_returnsTrueForEqualObjects() {
        BtcAddress btcAddress1 = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        BtcAddress btcAddress2 = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        assertTrue(btcAddress1.equals(btcAddress2));
    }

    @Test
    public void equals_returnsFalseForDifferentObjects() {
        BtcAddress btcAddress1 = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        BtcAddress btcAddress2 = new BtcAddress(VALID_PUBLIC_ADDRESS_2, VALID_LABEL_1);
        BtcAddress btcAddress3 = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_2);
        assertFalse(btcAddress1.equals(btcAddress2));
        assertFalse(btcAddress1.equals(btcAddress3));
    }

    @Test
    public void equals_returnsFalseForNull() {
        BtcAddress btcAddress = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        assertFalse(btcAddress.equals(null));
    }

    @Test
    public void equals_returnsFalseForDifferentClass() {
        BtcAddress btcAddress = new BtcAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        assertFalse(btcAddress.equals("Not a BtcAddress"));
    }

}
