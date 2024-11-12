package seedu.address.model.addresses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_ETH_MAIN;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_ETH_SUB;

import org.junit.jupiter.api.Test;

public class EthAddressTest {

    @Test
    public void constructor_nullPublicAddress_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new EthAddress(null, VALID_PUBLIC_ADDRESS_ETH_MAIN.getLabel()));
    }

    @Test
    public void constructor_nullLabel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new EthAddress(VALID_PUBLIC_ADDRESS_ETH_MAIN.getPublicAddressString(), null));
    }

    @Test
    public void constructor_invalidPublicAddress_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            new EthAddress("", VALID_PUBLIC_ADDRESS_ETH_MAIN.getLabel()));
    }

    @Test
    public void constructor_invalidLabel_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            new EthAddress(VALID_PUBLIC_ADDRESS_ETH_MAIN.getPublicAddressString(), ""));
    }

    @Test
    public void isValidPublicAddress_validInputs_returnsTrue() {
        assertTrue(new EthAddress(VALID_PUBLIC_ADDRESS_ETH_MAIN.getPublicAddressString(),
            VALID_PUBLIC_ADDRESS_ETH_MAIN.getLabel())
            .isValidPublicAddress(VALID_PUBLIC_ADDRESS_ETH_MAIN.getPublicAddressString()));
        assertTrue(new EthAddress(VALID_PUBLIC_ADDRESS_ETH_SUB.getPublicAddressString(),
            VALID_PUBLIC_ADDRESS_ETH_SUB.getLabel()).isValidPublicAddress(VALID_PUBLIC_ADDRESS_ETH_SUB
            .getPublicAddressString()));
    }

    @Test
    public void isValidPublicAddress_emptyAddress_returnsFalse() {
        assertFalse(new EthAddress(VALID_PUBLIC_ADDRESS_ETH_MAIN.getPublicAddressString(),
            VALID_PUBLIC_ADDRESS_ETH_MAIN.getLabel()).isValidPublicAddress(""));
    }

    @Test
    public void isValidPublicAddress_blankAddress_returnsFalse() {
        assertFalse(new EthAddress(VALID_PUBLIC_ADDRESS_ETH_MAIN.getPublicAddressString(),
            VALID_PUBLIC_ADDRESS_ETH_MAIN.getLabel()).isValidPublicAddress(" "));
    }

    @Test
    public void testGetNetwork() {
        EthAddress ethAddress = new EthAddress(VALID_PUBLIC_ADDRESS_ETH_MAIN.getPublicAddressString(),
            VALID_PUBLIC_ADDRESS_ETH_MAIN.getLabel());
        assertEquals(Network.ETH, ethAddress.getNetwork());
    }
}
