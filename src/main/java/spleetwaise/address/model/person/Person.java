package spleetwaise.address.model.person;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import spleetwaise.address.model.tag.Tag;
import spleetwaise.commons.util.CollectionUtil;
import spleetwaise.commons.util.IdUtil;
import spleetwaise.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book. Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Person {

    // Identity fields
    private final String id;
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Remark remark;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(String id, Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        CollectionUtil.requireAllNonNull(name, phone, email, address, remark, tags);
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.tags.addAll(tags);
    }

    public Person(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        this(IdUtil.getId(), name, phone, email, address, remark, tags);
    }

    public String getId() {
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException} if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name and phone number. This defines a notion of equality between two
     * persons based on their identity.
     * <br>
     * Note: The current implementation is case-sensitive for names, meaning "John" and "john" are treated as different
     * individuals. This is planned to be updated in a future release to be case-insensitive.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            // This means that the otherPerson has the same id, name, phone, email, address, remark and tags
            return true;
        }

        return otherPerson != null && name.equals(otherPerson.name) && phone.equals(otherPerson.phone);
    }

    /**
     * Returns true if both persons have the same id.
     */
    public boolean hasSameId(Person otherPerson) {
        return otherPerson.getId().equals(getId());
    }

    /**
     * Returns true if both persons have the same identity and data fields. This defines a stronger notion of equality
     * between two persons.
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

        return id.equals(otherPerson.id)
                && name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && remark.equals(otherPerson.remark)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("remark", remark)
                .add("tags", tags)
                .toString();
    }

}
