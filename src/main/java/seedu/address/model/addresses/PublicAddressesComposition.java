package seedu.address.model.addresses;

import static seedu.address.commons.util.StringUtil.INDENT;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a composition of public addresses categorized by network.
 */
public class PublicAddressesComposition {

    public static final String MESSAGE_LABELS_CONSTRAINTS =
            "Public addresses must have unique labels within a network.";

    private final Map<Network, Set<PublicAddress>> publicAddresses;

    /**
     * Constructs an empty PublicAddressesComposition.
     */
    public PublicAddressesComposition() {
        this.publicAddresses = new HashMap<>();
    }

    /**
     * Constructs a PublicAddressesComposition with the given map of public addresses.
     *
     * @param publicAddresses A map of networks to sets of public addresses.
     */
    public PublicAddressesComposition(Map<Network, Set<PublicAddress>> publicAddresses) {
        //assert that for each network there is no empty set of public addresses;
        assert publicAddresses != null;
        assert publicAddresses.values().stream().noneMatch(Set::isEmpty);

        if (!areValidPublicAddressLabels(publicAddresses)) {
            throw new IllegalArgumentException(MESSAGE_LABELS_CONSTRAINTS);
        }

        this.publicAddresses = publicAddresses.entrySet().stream()
            .collect(HashMap::new, (m, e) -> m.put(e.getKey(), new HashSet<>(e.getValue())), HashMap::putAll);
    }

    /**
     * Checks whether labels within in a network are unique.
     * Case is ignored.
     *
     * @param publicAddresses A map of networks to sets of public addresses.
     * @return True if labels within a network are unique, false otherwise.
     */
    private boolean areValidPublicAddressLabels(Map<Network, Set<PublicAddress>> publicAddresses) {
        assert publicAddresses != null;
        assert publicAddresses.values().stream().noneMatch(Set::isEmpty);

        return publicAddresses.values().stream()
                .allMatch(addresses -> {
                    Set<String> labels = new HashSet<>();
                    return addresses.stream()
                            .map(PublicAddress::getLabel)
                            .map(String::toLowerCase)
                            .allMatch(labels::add);
                });
    }

    /**
     * Adds a public address to the specified network. If the network does not exist, it is created.
     *
     * @param network       The network to which the public address belongs.
     * @param publicAddress The public address to be added.
     */
    public void addPublicAddress(Network network, PublicAddress publicAddress) {
        assert network != null;
        assert publicAddress != null;
        publicAddresses.computeIfAbsent(network, k -> new HashSet<>());
        publicAddresses.get(network).add(publicAddress);
    }

    /**
     * Sets the public addresses for the specified network and replaces the existing set of public addresses.
     *
     * @param network         The network to set the public addresses for.
     * @param publicAddresses The set of public addresses to be set.
     */
    public void setPublicAddressForNetwork(Network network, Set<PublicAddress> publicAddresses) {
        assert network != null;
        assert publicAddresses != null;
        this.publicAddresses.put(network, new HashSet<>(publicAddresses));
    }

    /**
     * Returns the map of public addresses.
     *
     * @return A map of networks to sets of public addresses.
     */
    public Map<Network, Set<PublicAddress>> getPublicAddresses() {
        return publicAddresses;
    }

    /**
     * Returns the set of public addresses for the specified network.
     *
     * @param network The network to get the public addresses for.
     * @return An unmodifiable set of public addresses for the specified network.
     */
    public Set<PublicAddress> getByNetwork(Network network) {
        assert network != null;
        return Collections.unmodifiableSet(publicAddresses.getOrDefault(network, new HashSet<>()));
    }

    /**
     * Adds a set of public addresses to the specified network.
     *
     * @param network   The network to add the public addresses to.
     * @param addresses The set of public addresses to be added.
     */
    public void addPublicAddressesToNetwork(Network network, Set<PublicAddress> addresses) {
        assert network != null;
        assert addresses != null;
        this.publicAddresses.computeIfAbsent(network, k -> new HashSet<>()).addAll(addresses);
    }

    /**
     * Checks if a public address exists in any network.
     *
     * @param publicAddressString The public address string to check.
     * @return True if the public address exists, false otherwise.
     */
    public Boolean hasPublicAddress(String publicAddressString) {
        assert publicAddressString != null;
        return publicAddresses.values().stream()
            .flatMap(Set::stream)
            .anyMatch(publicAddress -> publicAddress.isPublicAddressStringEquals(publicAddressString));
    }

    /**
     * Checks if there is a public address associated with the specified network and label.
     *
     * @param network The network to search for.
     * @param label   The label to match against the public addresses.
     * @return True if a public address with the specified label exists for the given network, false otherwise.
     */
    public boolean hasPublicAddressWithLabelWithinNetwork(Network network, String label) {
        return publicAddresses.entrySet().stream()
            .filter(entry -> entry.getKey().equals(network))
            .flatMap(entry -> entry.getValue().stream())
            .anyMatch(publicAddress -> publicAddress.getLabel().equals(label));
    }

    /**
     * Filters the public addresses by the specified public address string.
     *
     * @param publicAddressString The public address string to filter by.
     * @return A new PublicAddressesComposition containing the filtered public addresses.
     */
    public PublicAddressesComposition filterByPublicAddress(String publicAddressString) {
        Map<Network, Set<PublicAddress>> filteredPublicAddresses = publicAddresses.entrySet().stream()
            .map(entry -> {
                Set<PublicAddress> filteredAddresses = entry.getValue().stream()
                    .filter(pa -> pa.isPublicAddressStringEquals(publicAddressString))
                    .collect(Collectors.toSet());
                return new AbstractMap.SimpleEntry<>(entry.getKey(), filteredAddresses);
            })
            .filter(entry -> !entry.getValue().isEmpty())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new PublicAddressesComposition(filteredPublicAddresses);
    }

    /**
     * Adds a new public address to the composition.
     * Addresses with existing labels will be replaced.
     *
     * @param newPublicAddress The new public address to be added.
     * @return A new PublicAddressesComposition with the updated public addresses.
     */
    public PublicAddressesComposition add(PublicAddress newPublicAddress) {
        assert newPublicAddress != null;

        Map<Network, Set<PublicAddress>> updatedPublicAddresses = new HashMap<>(publicAddresses);

        // Get or create the set of addresses for this network
        Set<PublicAddress> networkAddresses = updatedPublicAddresses.getOrDefault(
                newPublicAddress.getNetwork(),
                new HashSet<>()
        );

        // Remove any existing address with the same label (if exists)
        networkAddresses.removeIf(addr -> addr.label.equals(newPublicAddress.label));

        // Add the new address
        networkAddresses.add(newPublicAddress);

        // Put the updated set back in the map
        updatedPublicAddresses.put(newPublicAddress.getNetwork(), networkAddresses);

        return new PublicAddressesComposition(updatedPublicAddresses);
    }

    /**
     * Removes all public address from the network.
     *
     * @param network The network to be removed.
     * @return A new PublicAddressesComposition with the updated public addresses.
     */
    public PublicAddressesComposition remove(Network network) {
        assert network != null;
        Map<Network, Set<PublicAddress>> updatedPublicAddresses = new HashMap<>(publicAddresses);
        updatedPublicAddresses.remove(network);
        return new PublicAddressesComposition(updatedPublicAddresses);
    }

    /**
     * Removes the public address with the specified label from the network.
     *
     * @param label The label of the public address to be removed.
     * @param network The network to remove the public address from.
     * @return A new PublicAddressesComposition with the updated public addresses
     */
    public PublicAddressesComposition remove(String label, Network network) {
        assert network != null;
        assert label != null;

        Map<Network, Set<PublicAddress>> updatedPublicAddresses = new HashMap<>(publicAddresses);

        // Get the set of addresses for this network
        Set<PublicAddress> networkAddresses = updatedPublicAddresses.get(network);

        // If network exists and has addresses
        if (networkAddresses != null) {
            // Remove the address with matching label
            Set<PublicAddress> updatedAddresses = networkAddresses.stream()
                    .filter(addr -> !addr.label.equals(label))
                    .collect(Collectors.toSet());

            // If set is empty after removal, remove the network entirely
            if (updatedAddresses.isEmpty()) {
                updatedPublicAddresses.remove(network);
            } else {
                updatedPublicAddresses.put(network, updatedAddresses);
            }
        }
        return new PublicAddressesComposition(updatedPublicAddresses);
    }

    /**
     * Checks if the composition is empty.
     *
     * @return True if the composition is empty, false otherwise.
     */
    public Boolean isEmpty() {
        return publicAddresses.isEmpty();
    }

    /**
     * Returns a string representation of the public addresses with indentation.
     *
     * @return A string representation of the public addresses.
     */
    public String toStringIndented() {
        return publicAddresses.entrySet().stream().map(entry -> entry.getKey() + "\n" + INDENT
            + INDENT + entry.getValue().stream().map(publicAddress -> publicAddress.getLabel() + ": "
                + publicAddress.getPublicAddressString())
            .reduce((a, b) -> a + "\n" + b).orElse("")).reduce((a, b) -> a + "\n" + b).orElse("");
    }

    /**
     * Creates a copy of the PublicAddressesComposition.
     *
     * @return A new PublicAddressesComposition with the same public addresses.
     */
    public PublicAddressesComposition copy() {
        return new PublicAddressesComposition(publicAddresses);
    }

    /**
     * Returns the number of networks in the composition.
     *
     * @return The number of networks.
     */
    public Integer size() {
        return publicAddresses.size();
    }

    /**
     * Returns the total number of public addresses in all networks.
     *
     * @return The total number of public addresses.
     */
    public Integer sizeOfAllPublicAddresses() {
        return publicAddresses.values().stream().map(Set::size).reduce(0, Integer::sum);
    }

    /**
     * Checks if a public address string exists among all networks.
     *
     * @param publicAddress The public address to check.
     * @return True if the public address string exists, false otherwise.
     */
    public Boolean containsPublicAddressStringAmongAllNetworks(PublicAddress publicAddress) {
        return publicAddresses.values().stream()
            .flatMap(Set::stream)
            .anyMatch(pa -> pa.isPublicAddressStringEquals(publicAddress.getPublicAddressString()));
    }

    /**
     * Returns any public address in the composition, this public address is randomly selected.
     *
     * @return a any public address, or null if the composition is empty.
     */
    public PublicAddress getAnyPublicAddress() {
        // publicAddresses map should not be null
        assert publicAddresses != null;
        // map should not be empty
        // assert !publicAddresses.isEmpty();

        // each set should not be empty
        assert !publicAddresses.values().stream().anyMatch(Set::isEmpty);

        // flatten map to get any public address
        PublicAddress publicAddress =
            publicAddresses.values().stream().flatMap(Set::stream).findFirst().orElse(null);
        // get any public address
        return publicAddress;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof PublicAddressesComposition
            && publicAddresses.equals(((PublicAddressesComposition) other).publicAddresses));
    }

    @Override
    public String toString() {
        return "PublicAddressesComposition{"
            + publicAddresses
            + '}';
    }
}
