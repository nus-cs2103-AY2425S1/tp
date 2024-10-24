package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
    private Map<Network, Set<PublicAddress>> publicAddresses;
    private final Set<Tag> tags = new HashSet<>();

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

    public void setPublicAddressesByNetwork(Network network, Set<PublicAddress> addresses) {
        requireAllNonNull(network, addresses);
        if (addresses.isEmpty()) {
            this.publicAddresses.remove(network);
            return;
        }
        this.publicAddresses.put(network, new HashSet<>(addresses));
    }

    /**
     * Gets the current Public address map
     *
     * @return publicAddresses
     */
    public Map<Network, Set<PublicAddress>> getPublicAddresses() {
        return Collections.unmodifiableMap(publicAddresses);
    }

    /**
     * Checks if there is a public address associated with the specified network and label.
     *
     * @param network The network to search for
     * @param label   The label to match against the public addresses
     * @return true if a public address with the specified label exists for the given network, false otherwise
     */
    public boolean hasPublicAddressWithLabel(Network network, String label) {
        return publicAddresses
                .getOrDefault(network, Collections.emptySet())
                .stream()
                .anyMatch(addr -> addr.label.equals(label));
    }

    /**
     * Returns a new {@code Person} object with the updated public address.
     * If the public address already exists for the network, it is replaced with the new one.
     *
     * @param newPublicAddress The new or updated public address to be added or replaced
     * @return A new {@code Person} object with the updated public address
     */
    public Person withUpdatedPublicAddress(PublicAddress newPublicAddress) {
        Map<Network, Set<PublicAddress>> updatedPublicAddresses = publicAddresses
                .entrySet()
                .stream()
                .map(entry -> Map.entry(
                        entry.getKey(),
                        entry.getKey().equals(newPublicAddress.getNetwork())
                        ? updateAddressSet(entry.getValue(), newPublicAddress)
                        : entry.getValue()
                ))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new Person(name, phone, email, address, updatedPublicAddresses, tags);
    }

    private Set<PublicAddress> updateAddressSet(Set<PublicAddress> addresses, PublicAddress newAddress) {
        return addresses.stream()
                .map(addr -> addr.label.equals(newAddress.label)
                             ? newAddress
                             : addr)
                .collect(Collectors.toSet());
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
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
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
