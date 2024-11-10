package seedu.address.model.addresses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_MAIN;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_SUB;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_ETH_MAIN;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_ETH_SUB;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_SOL_MAIN;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class PublicAddressesCompositionTest {

    //------------------ Constructor Tests ------------------

    @Test
    public void constructor_nullMap_throwsAssertionError() {
        // EP: null input
        assertThrows(AssertionError.class, () ->
            new PublicAddressesComposition(null));
    }

    @Test
    public void constructor_mapWithEmptySet_throwsAssertionError() {
        // EP: map with empty set
        Map<Network, Set<PublicAddress>> addresses = new HashMap<>();
        addresses.put(Network.BTC, new HashSet<>());

        assertThrows(AssertionError.class, () ->
            new PublicAddressesComposition(addresses));
    }

    //------------------ Add Public Address Tests ------------------

    @Test
    public void addPublicAddress_nullAddress_throwsAssertionError() {
        // EP: null address
        PublicAddressesComposition composition = new PublicAddressesComposition();
        assertThrows(AssertionError.class, () ->
            composition.addPublicAddress(null));
    }

    @Test
    public void addPublicAddress_duplicateAddresses() {
        // EP: duplicate addresses
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);

        assertEquals(1, composition.getByNetwork(Network.BTC).size());
    }

    @Test
    public void addPublicAddress_caseSensitivity() {
        // EP: case sensitivity
        PublicAddressesComposition composition = new PublicAddressesComposition();
        PublicAddress addressLowerCase =
            new BtcAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING.toLowerCase(), "Label");
        PublicAddress addressUpperCase = new BtcAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING
            .toUpperCase(),
            "Label");

        composition.addPublicAddress(addressLowerCase);
        composition.addPublicAddress(addressUpperCase);

        assertEquals(2, composition.getByNetwork(Network.BTC).size());
    }


    @Test
    public void addPublicAddress_newNetwork() {
        // EP: add address to new network
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);

        assertTrue(composition.getByNetwork(Network.BTC).contains(VALID_PUBLIC_ADDRESS_BTC_MAIN));
    }

    //------------------ Remove Public Address Tests ------------------

    @Test
    public void removePublicAddress_existingAddress() {
        // EP: remove existing address
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);
        composition.removePublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);

        assertFalse(composition.hasPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN.getPublicAddressString()));
    }

    @Test
    public void removePublicAddress_nonExistentAddress() {
        // EP: remove non-existent address
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);
        composition.removePublicAddress(VALID_PUBLIC_ADDRESS_BTC_SUB);

        assertTrue(composition.hasPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN.getPublicAddressString()));
    }

    @Test
    public void removePublicAddress_multipleAddresses() {
        // EP: remove address from network with multiple addresses
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_SUB);
        composition.removePublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);

        assertFalse(composition.hasPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN.getPublicAddressString()));
        assertTrue(composition.hasPublicAddress(VALID_PUBLIC_ADDRESS_BTC_SUB.getPublicAddressString()));
    }

    //------------------ Update Public Address Tests ------------------

    @Test
    public void updatePublicAddress_existingAddress() {
        // EP: update existing address
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);
        PublicAddress updatedAddress =
            new BtcAddress(VALID_PUBLIC_ADDRESS_BTC_SUB.getPublicAddressString(), "Updated Label");
        composition.updatePublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN,
            updatedAddress);

        assertTrue(composition.hasPublicAddress(VALID_PUBLIC_ADDRESS_BTC_SUB.getPublicAddressString()));
        assertFalse(composition.hasPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN.getPublicAddressString()));
    }

    @Test
    public void updatePublicAddress_nonExistentAddress() {
        // EP: update non-existent address
        PublicAddressesComposition composition = new PublicAddressesComposition();
        PublicAddress updatedAddress =
            new BtcAddress(VALID_PUBLIC_ADDRESS_SOL_MAIN.getPublicAddressString(), "Updated Label");

        assertThrows(AssertionError.class, () ->
            composition.updatePublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN,
                updatedAddress));
    }

    @Test
    public void updatePublicAddress_sameAddress() {
        // EP: update with the same address
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);
        composition.updatePublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN,
            VALID_PUBLIC_ADDRESS_BTC_MAIN);

        assertTrue(composition.hasPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN.getPublicAddressString()));
    }

    @Test
    public void updatePublicAddress_diffentAddressDifferentNetwork() {
        // EP: update with the diffent address different network
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);

        assertThrows(AssertionError.class, () ->
            composition.updatePublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN,
                VALID_PUBLIC_ADDRESS_ETH_MAIN));
    }

    //------------------ Empty Network Tests ------------------

    @Test
    public void getByNetwork_emptyNetwork() {
        // EP: get addresses from empty network
        PublicAddressesComposition composition = new PublicAddressesComposition();
        Set<PublicAddress> addresses = composition.getByNetwork(Network.BTC);

        assertTrue(addresses.isEmpty());
    }

    @Test
    public void getByNetwork_nullNetwork_throwsAssertionError() {
        // EP: null network
        PublicAddressesComposition composition = new PublicAddressesComposition();
        assertThrows(AssertionError.class, () ->
            composition.getByNetwork(null));
    }

    @Test
    public void getByNetwork_nonExistentNetwork() {
        // EP: get addresses from non-existent network
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);

        Set<PublicAddress> addresses = composition.getByNetwork(Network.ETH);

        assertTrue(addresses.isEmpty());
    }

    //------------------ Filter Tests ------------------

    @Test
    public void filterByPublicAddress_nullAddress_throwsAssertionError() {
        // EP: null address
        PublicAddressesComposition composition = new PublicAddressesComposition();
        assertThrows(AssertionError.class, () ->
            composition.filterByPublicAddress(null));
    }

    @Test
    public void filterByPublicAddress_existingAddress() {
        // EP: filter with existing address
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_SUB);

        PublicAddressesComposition filtered =
            composition.filterByPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN.getPublicAddressString());

        assertEquals(1, filtered.sizeOfAllPublicAddresses());
        assertTrue(filtered.hasPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN.getPublicAddressString()));
    }

    @Test
    public void filterByPublicAddress_nonExistentAddress() {
        // EP: filter with non-existent address
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);

        PublicAddressesComposition filtered =
            composition.filterByPublicAddress("non-existent-address");

        assertTrue(filtered.isEmpty());
    }

    //------------------ Network Tests ------------------

    @Test
    public void getByNetwork_existingNetwork() {
        // EP: get addresses from existing network
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);

        Set<PublicAddress> addresses = composition.getByNetwork(Network.BTC);

        assertEquals(1, addresses.size());
        assertTrue(addresses.contains(VALID_PUBLIC_ADDRESS_BTC_MAIN));
    }


    //------------------ Label Tests ------------------

    @Test
    public void hasPublicAddressWithLabelWithinNetwork_existingLabel() {
        // EP: check for existing label
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);

        assertTrue(composition.hasPublicAddressWithLabelWithinNetwork(
            Network.BTC, VALID_PUBLIC_ADDRESS_BTC_MAIN.getLabel()));
    }

    @Test
    public void hasPublicAddressWithLabelWithinNetwork_nonExistentLabel() {
        // EP: check for non-existent label
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);

        assertFalse(composition.hasPublicAddressWithLabelWithinNetwork(
            Network.BTC, "non-existent-label"));
    }

    //------------------ Copy Tests ------------------

    @Test
    public void copy_emptyComposition() {
        // EP: copy empty composition
        PublicAddressesComposition original = new PublicAddressesComposition();
        PublicAddressesComposition copy = original.copy();

        assertEquals(original, copy);
        assertTrue(copy.isEmpty());
    }

    @Test
    public void copy_nonEmptyComposition() {
        // EP: copy non-empty composition
        PublicAddressesComposition original = new PublicAddressesComposition();
        original.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);
        original.addPublicAddress(VALID_PUBLIC_ADDRESS_ETH_MAIN);

        PublicAddressesComposition copy = original.copy();

        assertEquals(original, copy);
        assertEquals(original.size(), copy.size());
        assertEquals(original.sizeOfAllPublicAddresses(), copy.sizeOfAllPublicAddresses());
    }

    //------------------ Size Tests ------------------

    @Test
    public void size_multipleNetworksAndAddresses() {
        // EP: size calculation with multiple networks and addresses
        PublicAddressesComposition composition = new PublicAddressesComposition();

        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_SUB);
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_ETH_MAIN);
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_ETH_SUB);

        assertEquals(2, composition.size()); // Number of networks
        assertEquals(4, composition.sizeOfAllPublicAddresses()); // Total number of addresses
    }

    //------------------ String Representation Tests ------------------

    @Test
    public void toStringIndented_multipleAddresses() {
        // EP: string representation with multiple addresses
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_ETH_MAIN);

        String result = composition.toStringIndented();

        assertTrue(result.contains(VALID_PUBLIC_ADDRESS_BTC_MAIN.getLabel()));
        assertTrue(result.contains(VALID_PUBLIC_ADDRESS_BTC_MAIN.getPublicAddressString()));
        assertTrue(result.contains(VALID_PUBLIC_ADDRESS_ETH_MAIN.getLabel()));
        assertTrue(result.contains(VALID_PUBLIC_ADDRESS_ETH_MAIN.getPublicAddressString()));
    }

    //------------------ Contains Public Address String Tests ------------------

    @Test
    public void containsPublicAddressStringAmongAllNetworks_existingAddress() {
        // EP: check if public address string exists among all networks
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);

        assertTrue(composition.containsPublicAddressStringAmongAllNetworks(VALID_PUBLIC_ADDRESS_BTC_MAIN));
    }

    @Test
    public void containsPublicAddressStringAmongAllNetworks_nonExistentAddress() {
        // EP: check if non-existent public address string exists among all networks
        PublicAddressesComposition composition = new PublicAddressesComposition();

        assertFalse(composition.containsPublicAddressStringAmongAllNetworks(VALID_PUBLIC_ADDRESS_BTC_MAIN));
    }

    @Test
    public void containsPublicAddressStringAmongAllNetworks_nullAddress_throwsAssertionError() {
        // EP: null address
        PublicAddressesComposition composition = new PublicAddressesComposition();
        assertThrows(AssertionError.class, () ->
            composition.containsPublicAddressStringAmongAllNetworks(null));
    }

    @Test
    public void getOnePublicAddress_success() {
        PublicAddressesComposition composition = new PublicAddressesComposition();
        composition.addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);

        assertEquals(VALID_PUBLIC_ADDRESS_BTC_MAIN, composition.getOnePublicAddress());
    }

    @Test
    public void getAllPublicAddresses_success() {
        PublicAddressesComposition composition = new PublicAddressesComposition();
        Set <PublicAddress> addresses = Set.of(
                VALID_PUBLIC_ADDRESS_BTC_MAIN,
                VALID_PUBLIC_ADDRESS_BTC_SUB,
                VALID_PUBLIC_ADDRESS_ETH_MAIN
        );

        addresses.forEach(composition::addPublicAddress);

        assertEquals(addresses, new HashSet<>(composition.getAllPublicAddresses()));
    }
}
