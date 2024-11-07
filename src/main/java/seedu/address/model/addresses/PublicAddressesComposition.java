package seedu.address.model.addresses;

import static seedu.address.commons.util.StringUtil.INDENT;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a composition of public addresses categorized by network.
 */
public class PublicAddressesComposition {

    public static final String MESSAGE_LABELS_CONSTRAINTS =
        "Public addresses must have unique labels within a network.";

    public static final String MESSAGE_ADD_CONSTRAINTS =
        "Public address label already exists within the same network.";

    public static final String MESSAGE_EDIT_CONSTRAINTS =
        "Public address label does not exists within the same network.";

    public static final String MESSAGE_DUPLICATE_LABEL =
        "Label %1$s under the network %2$s already exists.";

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
        assert publicAddresses != null;
        assert publicAddresses.values().stream().noneMatch(Set::isEmpty);

        if (!areValidPublicAddressLabels(publicAddresses)) {
            throw new IllegalArgumentException(MESSAGE_LABELS_CONSTRAINTS);
        }

        this.publicAddresses = publicAddresses.entrySet().stream()
            .collect(HashMap::new, (m, e) -> m.put(e.getKey(),
                new HashSet<>(e.getValue())), HashMap::putAll);
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
     * Adds a public address to the composition.
     *
     * @param publicAddress The public address to be added.
     */
    public void addPublicAddress(PublicAddress publicAddress) {
        assert publicAddress != null : "Public address cannot be null.";
        assert publicAddress.getNetwork() != null : "Network cannot be null.";
        publicAddresses.computeIfAbsent(publicAddress.getNetwork(), k -> new HashSet<>());
        publicAddresses.get(publicAddress.getNetwork()).add(publicAddress);
    }

    /**
     * Sets the public addresses for the specified network and replaces the existing set of public addresses.
     *
     * @param network         The network to set the public addresses for.
     * @param publicAddresses The set of public addresses to be set.
     */
    public void setPublicAddressForNetwork(Network network, Set<PublicAddress> publicAddresses) {
        assert network != null : "Network cannot be null.";
        assert publicAddresses != null : "Public addresses cannot be null.";
        this.publicAddresses.put(network, new HashSet<>(publicAddresses));
    }


    /**
     * Returns a copy of the map of public addresses.
     *
     * @return A map of networks to sets of public addresses.
     */
    public Map<Network, Set<PublicAddress>> getPublicAddresses() {
        return publicAddresses.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> new HashSet<>(entry.getValue())
            ));
    }

    /**
     * Returns the set of public addresses for the specified network.
     *
     * @param network The network to get the public addresses for.
     * @return An unmodifiable set of public addresses for the specified network.
     */
    public Set<PublicAddress> getByNetwork(Network network) {
        assert network != null : "Network cannot be null.";
        return Collections.unmodifiableSet(publicAddresses.getOrDefault(network, new HashSet<>()));
    }

    /**
     * Adds a set of public addresses to the specified network.
     *
     * @param network   The network to add the public addresses to.
     * @param addresses The set of public addresses to be added.
     */
    public void addPublicAddressesToNetwork(Network network, Set<PublicAddress> addresses) {
        assert network != null : "Network cannot be null.";
        assert addresses != null : "Addresses cannot be null.";
        this.publicAddresses.computeIfAbsent(network, k -> new HashSet<>()).addAll(addresses);
    }

    /**
     * Checks if a public address exists in any network.
     *
     * @param publicAddressString The public address string to check.
     * @return True if the public address exists, false otherwise.
     */

    public boolean hasPublicAddress(String publicAddressString) {
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
        assert network != null : "Network cannot be null.";
        assert label != null : "Label cannot be null.";
        return publicAddresses.entrySet().stream()
            .filter(entry -> entry.getKey().equals(network))
            .flatMap(entry -> entry.getValue().stream())
            .anyMatch(publicAddress -> publicAddress.getLabel().equals(label));
    }

    /**
     * Checks if a public address label exists within the specified network.
     *
     * @param network The network to search for.
     * @param label   The label to match against the public addresses.
     * @return True if a public address label exists within the specified network, false otherwise.
     */
    public boolean containsPublicAddressLabel(Network network, String label) {
        assert network != null;
        assert label != null;
        return publicAddresses
            .getOrDefault(network, Collections.emptySet())
            .stream()
            .anyMatch(publicAddress -> publicAddress.getLabel().equalsIgnoreCase(label));
    }

    /**
     * Checks if a public address string exists among all networks.
     *
     * @param publicAddress The public address to check.
     * @return True if the public address string exists, false otherwise.
     */
    public boolean containsPublicAddressStringAmongAllNetworks(PublicAddress publicAddress) {
        assert publicAddress != null;
        return publicAddresses.values().stream()
            .flatMap(Set::stream)
            .anyMatch(pa -> pa.isPublicAddressStringEquals(publicAddress.getPublicAddressString()));


    }

    /**
     * Filters the public addresses by the specified public address string.
     *
     * @param publicAddressString The public address string to filter by.
     * @return A new PublicAddressesComposition containing the filtered public addresses.
     */
    public PublicAddressesComposition filterByPublicAddress(String publicAddressString) {
        assert publicAddressString != null : "Public address string cannot be null.";
        assert !publicAddressString.isEmpty() : "Public address string cannot be empty.";
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
     * @throws IllegalArgumentException if label already exists within the same network.
     */

    public PublicAddressesComposition add(PublicAddress newPublicAddress) throws IllegalArgumentException {
        assert newPublicAddress != null;

        if (containsPublicAddressLabel(newPublicAddress.getNetwork(), newPublicAddress.getLabel())) {
            throw new IllegalArgumentException(MESSAGE_ADD_CONSTRAINTS);
        }

        Map<Network, Set<PublicAddress>> updatedPublicAddresses = getPublicAddresses();
        Set<PublicAddress> networkAddresses = updatedPublicAddresses.getOrDefault(
            newPublicAddress.getNetwork(),
            new HashSet<>()
        );
        networkAddresses.add(newPublicAddress);
        updatedPublicAddresses.put(newPublicAddress.getNetwork(), networkAddresses);

        return new PublicAddressesComposition(updatedPublicAddresses);
    }

    /**
     * Removes the specified public address from the composition.
     *
     * @param existingPublicAddress The public address to be removed.
     */
    public void removePublicAddress(PublicAddress existingPublicAddress) {
        assert existingPublicAddress != null : "Existing public address cannot be null.";
        publicAddresses.get(existingPublicAddress.getNetwork())
            .stream().filter(pa -> pa.equals(existingPublicAddress)).findFirst()
            .ifPresent(pa -> {
                publicAddresses.get(existingPublicAddress.getNetwork()).remove(pa);
                if (publicAddresses.get(existingPublicAddress.getNetwork()).isEmpty()) {
                    publicAddresses.remove(existingPublicAddress.getNetwork());
                }
            });
    }

    /**
     * Updates the specified public address in the composition.
     *
     * @param existingPublicAddress
     * @param updatedPublicAddress
     */
    public void updatePublicAddress(PublicAddress existingPublicAddress, PublicAddress updatedPublicAddress) {
        assert existingPublicAddress != null : "Existing public address cannot be null.";
        assert updatedPublicAddress != null : "Updated public address cannot be null.";
        assert existingPublicAddress.getNetwork().equals(updatedPublicAddress.getNetwork())
            : "Existing and updated public addresses must belong to the same network.";
        assert publicAddresses.containsKey(existingPublicAddress.getNetwork())
            : "Public addresses must contain the specified network.";
        assert publicAddresses.get(existingPublicAddress.getNetwork()).contains(existingPublicAddress)
            : "Existing public address must be present in the public addresses.";
        publicAddresses.get(existingPublicAddress.getNetwork())
            .stream().filter(pa -> pa.equals(existingPublicAddress)).findFirst()
            .ifPresent(pa -> {
                publicAddresses.get(existingPublicAddress.getNetwork()).remove(pa);
                publicAddresses.get(existingPublicAddress.getNetwork()).add(updatedPublicAddress);
            });
    }

    /**
     * Creates a new PublicAddressesComposition with updated public address.
     *
     * @param newPublicAddress The new public address.
     * @return A new PublicAddressesComposition with the updated public address.
     * @throws IllegalArgumentException if label does not exist within network.
     */
    public PublicAddressesComposition update(PublicAddress newPublicAddress) throws IllegalArgumentException {
        assert newPublicAddress != null;

        if (!containsPublicAddressLabel(newPublicAddress.getNetwork(), newPublicAddress.getLabel())) {
            throw new IllegalArgumentException(MESSAGE_EDIT_CONSTRAINTS);
        }

        Map<Network, Set<PublicAddress>> updatedPublicAddresses = getPublicAddresses();
        Set<PublicAddress> networkAddresses = updatedPublicAddresses.getOrDefault(
            newPublicAddress.getNetwork(),
            new HashSet<>()
        );
        networkAddresses.removeIf(addr -> addr.getLabel().equalsIgnoreCase(newPublicAddress.getLabel()));
        networkAddresses.add(newPublicAddress);
        updatedPublicAddresses.put(newPublicAddress.getNetwork(), networkAddresses);

        return new PublicAddressesComposition(updatedPublicAddresses);
    }

    /**
     * Returns the number of public addresses in the composition.
     *
     * @return The number of public addresses.
     */
    public int sizeOfAllPublicAddresses() {
        return publicAddresses.values().stream().mapToInt(Set::size).sum();
    }

  

    /**
     * Combines this PublicAddressesComposition with another.
     * Adds all public addresses from the other composition that don't already exist in this one.
     *
     * @param other The other PublicAddressesComposition to combine with.
     * @return A new PublicAddressesComposition containing all unique addresses from both compositions.
     * @throws IllegalArgumentException if there's a conflicting address (same label in the same network).
     */


    public PublicAddressesComposition combineWith(PublicAddressesComposition other) {
        Map<Network, Set<PublicAddress>> combinedAddresses = Stream
            .concat(
                publicAddresses.entrySet().stream(),
                other.getPublicAddresses().entrySet().stream()
            )
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (set1, set2) -> {
                    Set<PublicAddress> combinedSet = new HashSet<>(set1);
                    set2.stream()
                        .filter(addr -> {
                            if (combinedSet.stream().anyMatch(existing ->
                                existing.getLabel().equalsIgnoreCase(addr.getLabel()))) {
                                throw new IllegalArgumentException(String.format(MESSAGE_DUPLICATE_LABEL,
                                    addr.getLabel(), addr.getNetwork()));
                            }
                            return true;
                        })
                        .forEach(combinedSet::add);
                    return combinedSet;
                }
            ));

        return new PublicAddressesComposition(combinedAddresses);

    }

    /**
     * Removes all public address from the network.
     *
     * @param network The network to be removed.
     * @return A new PublicAddressesComposition with the updated public addresses.
     */
    public PublicAddressesComposition remove(Network network) {
        assert network != null;
        Map<Network, Set<PublicAddress>> updatedPublicAddresses = getPublicAddresses();
        updatedPublicAddresses.remove(network);
        return new PublicAddressesComposition(updatedPublicAddresses);
    }

    /**
     * Removes the public address with the specified label from the network.
     *
     * @param label   The label of the public address to be removed.
     * @param network The network to remove the public address from.
     * @return A new PublicAddressesComposition with the updated public addresses
     */
    public PublicAddressesComposition remove(String label, Network network) {
        assert network != null;
        assert label != null;

        Map<Network, Set<PublicAddress>> updatedPublicAddresses = getPublicAddresses();

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
     * Creates a copy of the PublicAddressesComposition.
     *
     * @return A new PublicAddressesComposition with the same public addresses.
     */
    public PublicAddressesComposition copy() {
        return new PublicAddressesComposition(publicAddresses);
    }


    public boolean isEmpty() {
        return publicAddresses.isEmpty();
    }


    /**
     * Returns a string representation of the public addresses with indentation.
     *
     * @return A string representation of the public addresses.
     */
    public String toStringIndented() {
        StringBuilder sb = new StringBuilder();
        publicAddresses.forEach((network, addresses) -> {
            sb.append(INDENT)
                    .append(network)
                    .append(":\n");
            addresses.forEach(address -> {
                sb.append(INDENT + INDENT)
                        .append(address.getLabel())
                        .append(":\n")
                        .append(INDENT + INDENT + INDENT)
                        .append(address.getPublicAddressString())
                        .append("\n");
            });
        });
        return sb.toString();
    }


    /**
     * Returns the number of networks in the composition.
     *
     * @return The number of networks.
     */
    public int size() {
        return publicAddresses.size();
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
