package seedu.address.model.addresses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_SOL_MAIN;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_SOL_SUB;

import org.junit.jupiter.api.Test;

public class SolAddressTest {

    @Test
    public void constructor_nullPublicAddress_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new SolAddress(null, VALID_PUBLIC_ADDRESS_SOL_MAIN.getLabel()));
    }

    @Test
    public void constructor_nullLabel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new SolAddress(VALID_PUBLIC_ADDRESS_SOL_MAIN.getPublicAddressString(), null));
    }

    @Test
    public void constructor_invalidPublicAddress_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            new SolAddress("", VALID_PUBLIC_ADDRESS_SOL_MAIN.getLabel()));
    }

    @Test
    public void constructor_invalidLabel_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
            new SolAddress(VALID_PUBLIC_ADDRESS_SOL_MAIN.getPublicAddressString(), ""));
    }

    @Test
    public void isValidPublicAddress_validInputs_returnsTrue() {
        assertTrue(new SolAddress(VALID_PUBLIC_ADDRESS_SOL_MAIN.getPublicAddressString(),
            VALID_PUBLIC_ADDRESS_SOL_MAIN.getLabel()).isValidPublicAddress(
            VALID_PUBLIC_ADDRESS_SOL_MAIN.getPublicAddressString()));
        assertTrue(new SolAddress(VALID_PUBLIC_ADDRESS_SOL_SUB.getPublicAddressString(),
            VALID_PUBLIC_ADDRESS_SOL_SUB.getLabel())
            .isValidPublicAddress(VALID_PUBLIC_ADDRESS_SOL_SUB.getPublicAddressString()));
    }

    @Test
    public void isValidPublicAddress_emptyAddress_returnsFalse() {
        assertFalse(new SolAddress(VALID_PUBLIC_ADDRESS_SOL_MAIN.getPublicAddressString(),
            VALID_PUBLIC_ADDRESS_SOL_MAIN.getLabel()).isValidPublicAddress(""));
    }

    @Test
    public void isValidPublicAddress_blankAddress_returnsFalse() {
        assertFalse(new SolAddress(VALID_PUBLIC_ADDRESS_SOL_MAIN.getPublicAddressString(),
            VALID_PUBLIC_ADDRESS_SOL_MAIN.getLabel()).isValidPublicAddress(" "));
    }

    @Test
    public void testGetNetwork() {
        SolAddress solAddress = new SolAddress(VALID_PUBLIC_ADDRESS_SOL_MAIN.getPublicAddressString(),
            VALID_PUBLIC_ADDRESS_SOL_MAIN.getLabel());
        assertEquals(Network.SOL, solAddress.getNetwork());
    }
}
