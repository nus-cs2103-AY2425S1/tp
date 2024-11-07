package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a parent in the address book
 */
public class Parent extends Person {
    private final Set<Name> childrensNames;

    /**
     * Constructs a {@code Parent} with the given details.
     * Parents initialised with this constructor will have isPinned set to false by default
     */
    public Parent(Name name, Phone phone, Email email, Address address, Set<Name> childrensNames, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        requireNonNull(childrensNames);
        this.childrensNames = childrensNames;
    }

    /**
     * Constructs a {@code Parent} with the given details.
     */
    public Parent(Name name, Phone phone, Email email, Address address,  Set<Name> childrensNames, Set<Tag> tags,
            boolean isPinned, boolean isArchived) {
        super(name, phone, email, address, tags, isPinned, isArchived);
        this.childrensNames = childrensNames;
    }

    /**
     * Constructs a {@code Parent} with the given {@code Person} as a base
     */
    public Parent(Person person, Set<Name> childrensNames) {
        super(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), person.getTags(),
                person.isPinned(), person.isArchived());
        this.childrensNames = childrensNames;
    }


    public Set<Name> getChildrensNames() {
        return childrensNames;
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
        return super.equals(otherParent) && childrensNames.equals(otherParent.childrensNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getPhone(), this.getEmail(), this.getAddress(),
                this.getChildrensNames(), this.getTags());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", this.getName())
                .add("phone", this.getPhone())
                .add("email", this.getEmail())
                .add("address", this.getAddress())
                .add("children", this.getChildrensNames())
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
                .append("; Children: ")
                .append(getChildrensNames())
                .append("; Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
