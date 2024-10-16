package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.util.Set;

public class Tutee extends Person {
    public Tutee(Name name, Phone phone, Email email, Address address, Hours hours, Set<Tag> tags) {
        super(name, phone, email, address, hours, tags);
    }

    @Override
    public String toString() {
        return "Tutee: " + super.toString();
    }

}