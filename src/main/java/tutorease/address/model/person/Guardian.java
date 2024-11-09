package tutorease.address.model.person;

import java.util.Set;

import tutorease.address.model.tag.Tag;
/**
 * Represents a Guardian in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Guardian extends Person {
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
    public Guardian(Name name, Phone phone, Email email, Address address, Role role, Set<Tag> tags) {
        super(name, phone, email, address, role, tags);
    }

    public Role getRole() {
        return new Role(Role.GUARDIAN);
    }

    @Override
    public boolean isGuardian() {
        return true;
    }

    @Override
    public boolean isStudent() {
        return false;
    }
}
