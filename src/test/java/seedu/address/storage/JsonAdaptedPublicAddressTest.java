package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.addresses.BtcAddress;
import seedu.address.model.addresses.Network;

public class JsonAdaptedPublicAddressTest {

    private static final String VALID_PUBLIC_ADDRESS = "14qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd";
    private static final String VALID_LABEL = "My BTC Address";
    private static final Network VALID_NETWORK = Network.BTC;

    private static final String INVALID_PUBLIC_ADDRESS = " ";
    private static final String INVALID_LABEL = "";

    @Test
    public void toModelType_validDetails_returnsPublicAddress() throws Exception {
        JsonAdaptedPublicAddress address = new JsonAdaptedPublicAddress(VALID_PUBLIC_ADDRESS, VALID_LABEL);
        assertEquals(new BtcAddress(VALID_PUBLIC_ADDRESS, VALID_LABEL), address.toModelType(VALID_NETWORK));
    }

    @Test
    public void toModelType_invalidPublicAddress_throwsIllegalValueException() {
        JsonAdaptedPublicAddress address = new JsonAdaptedPublicAddress(INVALID_PUBLIC_ADDRESS, VALID_LABEL);
        assertThrows(IllegalValueException.class, () -> address.toModelType(VALID_NETWORK));
    }

    @Test
    public void toModelType_invalidLabel_throwsIllegalValueException() {
        JsonAdaptedPublicAddress address = new JsonAdaptedPublicAddress(VALID_PUBLIC_ADDRESS, INVALID_LABEL);
        assertThrows(IllegalValueException.class, () -> address.toModelType(VALID_NETWORK));
    }

    @Test
    public void toModelType_nullPublicAddress_throwsIllegalValueException() {
        JsonAdaptedPublicAddress address = new JsonAdaptedPublicAddress(null, VALID_LABEL);
        assertThrows(IllegalValueException.class, () -> address.toModelType(VALID_NETWORK));
    }

    @Test
    public void toModelType_nullLabel_throwsIllegalValueException() {
        JsonAdaptedPublicAddress address = new JsonAdaptedPublicAddress(VALID_PUBLIC_ADDRESS, null);
        assertThrows(IllegalValueException.class, () -> address.toModelType(VALID_NETWORK));
    }

    @Test
    public void toModelType_invalidNetwork_throwsIllegalValueException() {
        JsonAdaptedPublicAddress address = new JsonAdaptedPublicAddress(VALID_PUBLIC_ADDRESS, VALID_LABEL);
        assertThrows(IllegalValueException.class, () -> address.toModelType(null));
    }

}
