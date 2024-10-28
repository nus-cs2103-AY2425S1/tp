package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.PropertyTag;
import seedu.address.model.tag.PropertyTagType;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Static counter for generating unique IDs
    private static int personCounter = 0;

    // Identity fields
    private final int id; // Unique ID for each Person
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Remark remark;
    private final UniqueListingList listings;

    /**
     * Every field must be present and not null.
     */
    public Person(int id, Name name, Phone phone, Email email,
                  Address address, Set<Tag> tags, Remark remark,
                  UniqueListingList listings) {
        requireAllNonNull(name, phone, email, address, tags);
        if (id > 0) {
            this.id = id;
            if (id > personCounter) {
                personCounter = id;
            }
        } else {
            this.id = ++personCounter;
        }

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        // Since listings exist, we will sync the Property Tags with the current listings
        // We first remove any existing property tags
        for (Tag tag: tags) {
            if (PropertyTagType.isValidPropertyTag(tag.tagName)) {
                this.tags.remove(tag);
            }
        }

        // We then add in the current property tags
        for (Listing listing: listings) {
            Tag currentTag = new PropertyTag(listing.propertyType.toString().toLowerCase());
            if (!this.tags.contains(currentTag)) {
                this.tags.add(currentTag);
            }
        }

        this.remark = remark;
        this.listings = listings;
    }

    /**
     * Constructor without an ID parameter, increments counter automatically.
     */
    public Person(Name name, Phone phone, Email email,
                  Address address, Set<Tag> tags, Remark remark,
                  UniqueListingList listings) {
        this(++personCounter, name, phone, email, address, tags, remark, listings);
    }

    /**
     * Constructor for creating Person objects without a listing
     * creates an empty UniqueListingList internally.
     */
    public Person(Name name, Phone phone, Email email,
                  Address address, Set<Tag> tags, Remark remark) {
        this(name, phone, email, address, tags, remark, new UniqueListingList());
    }

    public int getId() {
        return id;
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

    public Remark getRemark() {
        return remark;
    }

    public UniqueListingList getListings() {
        return listings;
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
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone());
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
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, name, phone, email, address, tags);
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this)
                .add("id", id)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("remark", remark)
                .add("listings", listings)
                .toString();
    }

}
