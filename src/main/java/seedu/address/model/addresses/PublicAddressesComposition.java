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
        this.publicAddresses = publicAddresses.entrySet().stream()
                .collect(HashMap::new, (m, e) -> m.put(e.getKey(), new HashSet<>(e.getValue())), HashMap::putAll);
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
     * Sets the public addresses for the specified network.
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
        if (addresses.isEmpty()) {
            this.publicAddresses.remove(network);
            return;
        }
        this.publicAddresses.put(network, new HashSet<>(addresses));
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
        return publicAddresses
                .getOrDefault(network, Collections.emptySet())
                .stream()
                .anyMatch(addr -> addr.label.equals(label));
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
     *
     * @param newPublicAddress The new public address to be added.
     * @return A new PublicAddressesComposition with the updated public addresses.
     */
    public PublicAddressesComposition add(PublicAddress newPublicAddress) {
        Map<Network, Set<PublicAddress>> updatedPublicAddresses = publicAddresses.entrySet().stream()
                .map(entry -> {
                    Set<PublicAddress> updatedAddresses = entry.getValue().stream()
                            .map(addr -> addr.label.equals(newPublicAddress.label)
                                    ? newPublicAddress
                                    : addr)
                            .collect(Collectors.toSet());
                    return new AbstractMap.SimpleEntry<>(entry.getKey(), updatedAddresses);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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
    public Boolean isPublicAddressStringAmongAllNetworks(PublicAddress publicAddress) {
        return publicAddresses.values().stream()
                .flatMap(Set::stream)
                .anyMatch(pa -> pa.isPublicAddressStringEquals(publicAddress.getPublicAddressString()));
    }

    /**
     * Returns the first public address in the composition.
     *
     * @return The first public address, or null if the composition is empty.
     */
    public PublicAddress getFirstPublicAddress() {
        // publicAddresses map should not be null
        assert publicAddresses != null;
        // map should not be empty
        // assert !publicAddresses.isEmpty();

        // each set should not be empty
        assert !publicAddresses.values().stream().anyMatch(Set::isEmpty);

        // flatten map to get first public address
        PublicAddress publicAddress =
                publicAddresses.values().stream().flatMap(Set::stream).findFirst().orElse(null);
        // get first public address
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
