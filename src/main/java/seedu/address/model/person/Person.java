package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.addresses.PublicAddressesComposition;
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
    private final PublicAddressesComposition publicAddressesComposition;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  PublicAddressesComposition publicAddresses, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, publicAddresses, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.publicAddressesComposition = publicAddresses;
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
        return publicAddressesComposition.getByNetwork(network);
    }


    /**
     * Returns true if the person has a public address in the network
     *
     * @param publicAddressString
     * @return
     */
    public Boolean hasPublicAddressStringAmongAllNetworks(String publicAddressString) {
        return publicAddressesComposition.hasPublicAddress(publicAddressString);
    }


    public PublicAddressesComposition getPublicAddressObjectByPublicAddressMap(String publicAddressString) {
        return publicAddressesComposition.filterByPublicAddress(publicAddressString);
    }


    public void addPublicAddressToNetwork(Network network, Set<PublicAddress> addresses) {
        publicAddressesComposition.addPublicAddressesToNetwork(network, addresses);
    }

    /**
     * Gets the current Public address map
     *
     * @return publicAddresses
     */
    public PublicAddressesComposition getPublicAddressesComposition() {
        return publicAddressesComposition;
    }

    /**
     * Checks if there is a public address associated with the specified network and label.
     *
     * @param network The network to search for
     * @param label   The label to match against the public addresses
     * @return true if a public address with the specified label exists for the given network, false otherwise
     */
    public boolean hasPublicAddressWithLabelWithinNetwork(Network network, String label) {
        return publicAddressesComposition.hasPublicAddressWithLabelWithinNetwork(network, label);
    }

    /**
     * Returns a new {@code Person} object with the updated public address.
     * If the public address already exists for the network, it is replaced with the new one.
     *
     * @param newPublicAddress The new or updated public address to be added or replaced
     * @return A new {@code Person} object with the updated public address
     */
    public Person withUpdatedPublicAddress(PublicAddress newPublicAddress) {
        PublicAddressesComposition updatedPublicAddresses = publicAddressesComposition.add(newPublicAddress);
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
        if (!(other instanceof Person otherPerson)) {
            return false;
        }

        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && publicAddressesComposition.equals(otherPerson.publicAddressesComposition)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, publicAddressesComposition, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("publicAddresses", publicAddressesComposition)
                .add("tags", tags)
                .toString();
    }

}
