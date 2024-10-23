package seedu.address.model.addresses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SolAddressTest {

    private static final String VALID_PUBLIC_ADDRESS_1 = "14qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd";
    private static final String VALID_PUBLIC_ADDRESS_2 = "34qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd";
    private static final String VALID_LABEL_1 = "Wallet label";
    private static final String VALID_LABEL_2 = "Other label";

    @Test
    public void constructor_nullPublicAddress_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SolAddress(null, VALID_LABEL_1));
    }

    @Test
    public void constructor_nullLabel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SolAddress(VALID_PUBLIC_ADDRESS_1, null));
    }

    @Test
    public void constructor_invalidPublicAddress_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new SolAddress("", VALID_LABEL_1));
    }

    @Test
    public void constructor_invalidLabel_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new SolAddress(VALID_PUBLIC_ADDRESS_1, ""));
    }

    @Test
    public void isValidPublicAddress_validInputs_returnsTrue() {
        assertTrue(new SolAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1).isValidPublicAddress(VALID_PUBLIC_ADDRESS_1,
                VALID_LABEL_1));
        assertTrue(new SolAddress(VALID_PUBLIC_ADDRESS_2, VALID_LABEL_2).isValidPublicAddress(VALID_PUBLIC_ADDRESS_2,
                VALID_LABEL_2));
    }

    @Test
    public void isValidPublicAddress_emptyAddress_returnsFalse() {
        assertFalse(new SolAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1).isValidPublicAddress("", VALID_LABEL_1));
    }

    @Test
    public void isValidPublicAddress_emptyLabel_returnsFalse() {
        assertFalse(
                new SolAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1).isValidPublicAddress(VALID_PUBLIC_ADDRESS_1, ""));
    }

    @Test
    public void isValidPublicAddress_blankAddress_returnsFalse() {
        assertFalse(new SolAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1).isValidPublicAddress(" ", VALID_LABEL_1));
    }

    @Test
    public void isValidPublicAddress_blankLabel_returnsFalse() {
        assertFalse(new SolAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1).isValidPublicAddress(VALID_PUBLIC_ADDRESS_1,
                " "));
    }

    @Test
    public void testGetNetwork() {
        SolAddress solAddress = new SolAddress(VALID_PUBLIC_ADDRESS_1, VALID_LABEL_1);
        assertEquals(Network.SOL, solAddress.getNetwork());
    }

}
