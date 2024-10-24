package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Map<Network, Set<PublicAddress>> publicAddresses;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Map<Network, Set<PublicAddress>> publicAddresses, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, publicAddresses, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.publicAddresses = new HashMap<>(publicAddresses);
        publicAddresses.forEach((network, addresses) -> this.publicAddresses.put(network, new HashSet<>(addresses)));
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Set<PublicAddress> getPublicAddressesByNetwork(Network network) {
        return Collections.unmodifiableSet(publicAddresses.getOrDefault(network, new HashSet<>()));
    }

    /**
     * Returns true if the person has a public address in the network
     *
     * @param publicAddressString
     * @return
     */
    public Boolean hasPublicAddressStringAmongAllNetworks(String publicAddressString) {
        return publicAddresses.values().stream()
                .flatMap(Set::stream)
                .anyMatch(publicAddress -> publicAddress.isPublicAddressStringEquals(publicAddressString));
    }

    public Set<PublicAddress> getPublicAddressObjectByPublicAddressStringMap(String publicAddressString) {
        return publicAddresses.values().stream()
                .flatMap(Set::stream)
                .filter(publicAddress -> publicAddress.isPublicAddressStringEquals(publicAddressString))
                .collect(Collectors.toSet());
    }

    public Map<Network, Set<PublicAddress>> getPublicAddressObjectByPublicAddressMap(String publicAddressString) {
        return publicAddresses.entrySet().stream()
                .map(entry -> {
                    Set<PublicAddress> filteredAddresses = entry.getValue().stream()
                            .filter(pa -> pa.isPublicAddressStringEquals(publicAddressString))
                            .collect(Collectors.toSet());
                    return new AbstractMap.SimpleEntry<>(entry.getKey(), filteredAddresses);
                })
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void setPublicAddressesByNetwork(Network network, HashSet<PublicAddress> addresses) {
        if (publicAddresses.containsKey(network)) {
            this.publicAddresses.put(network, new HashSet<>(addresses));
        }
    }


    /**
     * Gets the current Public address map
     * Should be replaced with a better method in the future
     *
     * @return publicAddresses
     */
    public Map<Network, Set<PublicAddress>> getPublicAddresses() {
        return Collections.unmodifiableMap(publicAddresses);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person otherPerson)) {
            return false;
        }

        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && publicAddresses.equals(otherPerson.publicAddresses)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, publicAddresses, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("publicAddresses", publicAddresses)
                .add("tags", tags)
                .toString();
    }

}
