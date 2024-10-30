package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a parent in the address book
 */
public class Parent extends Person {
    private final Name childName;

    /**
     * Constructs a {@code Parent} with the given details.
     */
    public Parent(Name name, Phone phone, Email email, Address address, Name childName, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        this.childName = childName;
    }

    /**
     * Constructs a {@code Parent} with the given {@code Person} as a base.
     */
    public Parent(Person person, Name childName) {
        super(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), person.getTags());
        this.childName = childName;
    }

    public Name getChildName() {
        return childName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Parent)) {
            return false;
        }

        Parent otherParent = (Parent) other;
        return super.equals(otherParent) && getChildName().equals(otherParent.getChildName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getPhone(), this.getEmail(), this.getAddress(),
                this.getChildName(), this.getTags());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", this.getName())
                .add("phone", this.getPhone())
                .add("email", this.getEmail())
                .add("address", this.getAddress())
                .add("child", this.getChildName())
                .add("tags", this.getTags())
                .toString();
    }

    @Override
    public String toMessageString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress())
                .append("; Child: ")
                .append(getChildName())
                .append("; Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
