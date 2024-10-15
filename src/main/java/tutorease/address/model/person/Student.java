package tutorease.address.model.person;

import tutorease.address.model.tag.Tag;

import java.util.Set;

public class Student extends Person{
    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param tags
     */
    public Student(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
    }
}
