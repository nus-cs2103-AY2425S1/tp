package seedu.address.model.addresses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.BTC_DAILY_ADDRESS;

import org.junit.jupiter.api.Test;

public class PublicAddressFactoryTest {

    @Test
    public void createPublicAddress_btcNetwork_returnsBtcAddress() {
        PublicAddress address =
                PublicAddressFactory.createPublicAddress(Network.BTC, BTC_DAILY_ADDRESS.publicAddress,
                        BTC_DAILY_ADDRESS.label);
        assertTrue(address instanceof BtcAddress);
        assertEquals(Network.BTC, address.getNetwork());
    }

}
