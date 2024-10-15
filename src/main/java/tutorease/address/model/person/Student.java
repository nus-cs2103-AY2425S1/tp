package tutorease.address.model.person;

import tutorease.address.commons.util.ToStringBuilder;
import tutorease.address.model.tag.Tag;

import java.util.Objects;
import java.util.Set;

public class Student extends Person {
    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param role
     * @param tags
     */
    private final Address address;
    private final Email email;
    public Student(Name name, Phone phone, Email email, Address address, Role role, Set<Tag> tags) {
        super(name, phone, role, tags);
        this.address = address;
        this.email = email;
    }

    public Address getAddress() {
        return this.address;
    }

    public Email getEmail() {
        return this.email;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(super.getName(), super.getPhone(), email, address, super.getRole(), super.getTags());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(super.toString())
                .add("email", email)
                .add("address", address)
                .toString();
    }
}
