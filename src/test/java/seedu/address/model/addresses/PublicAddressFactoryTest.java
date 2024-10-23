package seedu.address.model.addresses;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PublicAddressFactoryTest {

    private static final String VALID_PUBLIC_ADDRESS = "14qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd";
    private static final String VALID_LABEL = "Wallet label";

    @Test
    public void createPublicAddress_btcNetwork_returnsBtcAddress() {
        PublicAddress address =
                PublicAddressFactory.createPublicAddress(Network.BTC, VALID_PUBLIC_ADDRESS, VALID_LABEL);
        assertTrue(address instanceof BtcAddress);
    }

    @Test
    public void createPublicAddress_ethNetwork_returnsEthAddress() {
        PublicAddress address =
                PublicAddressFactory.createPublicAddress(Network.ETH, VALID_PUBLIC_ADDRESS, VALID_LABEL);
        assertTrue(address instanceof EthAddress);
    }

    @Test
    public void createPublicAddress_solNetwork_returnsSolAddress() {
        PublicAddress address =
                PublicAddressFactory.createPublicAddress(Network.SOL, VALID_PUBLIC_ADDRESS, VALID_LABEL);
        assertTrue(address instanceof SolAddress);
    }

}
