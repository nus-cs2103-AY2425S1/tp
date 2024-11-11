package tutorease.address.model.person;

import static tutorease.address.model.person.Role.STUDENT;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.model.tag.Tag;
/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student extends Person {
    private static Logger logger = LogsCenter.getLogger(Student.class);
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

    public Student(Name name, Phone phone, Email email, Address address, Role role, Set<Tag> tags) {
        super(name, phone, email, address, role, tags);
        logger.log(Level.INFO, "Creating Student object with name: " + name
                + " phone: " + phone + " email: " + email + " address: " + address
                + " role: " + role + " tags: " + tags);
    }

    public Role getRole() {
        return new Role(STUDENT);
    }

    @Override
    public boolean isGuardian() {
        return false;
    }

    @Override
    public boolean isStudent() {
        return true;
    }

}
