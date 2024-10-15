package tutorease.address.model.person;

import tutorease.address.model.tag.Tag;

import java.util.Set;

import static tutorease.address.model.person.Role.STUDENT;

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

    public Student(Name name, Phone phone, Email email, Address address, Role role, Set<Tag> tags) {
        super(name, phone, email, address, role, tags);
    }

    public String getRole() {
        return STUDENT;
    }
}
