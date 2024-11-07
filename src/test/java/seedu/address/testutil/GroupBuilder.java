package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.model.person.Group;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Group objects.
 * @param <T> the type of the builder (either GroupBuilder or its subclass)
 */
public class GroupBuilder<T extends GroupBuilder<T>> {
    public static final String DEFAULT_NAME = "NUS_GROUP";
    protected String name;
    private String name1 = "Johnson Goh";
    private String name2 = "Bravo Folly";
    private String name3 = "Tyrone Louise";
    private Person member1 = new PersonBuilder<>().withName(name1).build();
    private Person member2 = new PersonBuilder<>().withName(name2).build();
    private Person member3 = new PersonBuilder<>().withName(name3).build();
    public GroupBuilder() {
        this.name = DEFAULT_NAME;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public T withName(String name) {
        this.name = name;
        return self();
    }

    /**
     * Builds a new group for testing
     *
     * @return a group with the members added and name.
     */
    public Group build() {
        ArrayList<Person> members = new ArrayList<>();
        members.add(member1);
        members.add(member2);
        members.add(member3);
        Group group = new Group(name);
        group.setPersons(members);
        return group;
    }

    /**
     * Returns this builder instance cast to the correct type.
     * @return this builder
     */
    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }
}
