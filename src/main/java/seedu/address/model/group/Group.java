package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.StringJoiner;

import seedu.address.model.person.Person;

/**
 * Represents a group of persons.
 */
public class Group {
    private final GroupName groupName;
    private final List<Person> members;

    /**
     * Constructs a {@code Group} with the specified group name and members.
     *
     * @param groupName The name of the group.
     * @param members   The list of persons in the group.
     * @throws NullPointerException if {@code groupName} or {@code members} is null.
     */
    public Group(String groupName, List<Person> members) {

        requireNonNull(groupName);
        requireNonNull(members);

        this.groupName = new GroupName(groupName);
        this.members = members;
    }

    /**
     * Returns the name of the group.
     *
     * @return The group name.
     */
    public GroupName getGroupName() {
        return groupName;
    }

    /**
     * Returns the list of persons in the group.
     *
     * @return The list of group members.
     */
    public List<Person> getMembers() {
        return members;
    }

    /**
     * Returns a string representation of the group, including the group name and members.
     *
     * @return A string representation of the group.
     */
    @Override
    public String toString() {
        StringJoiner memberString = new StringJoiner(", ");
        for (Person person : members) {
            memberString.add(person.getName().fullName);
        }
        return String.format("[Group: %s, Members: %s]", groupName, memberString);
    }

    /**
     * Returns true if both groups have the same name.
     * This defines a weaker notion of equality between two groups.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }
        return otherGroup != null
                && otherGroup.getGroupName().equals(getGroupName());
    }

    @Override
    public boolean equals(Object other) throws ClassCastException {

        requireNonNull(other);
        // Check if the object is the same reference
        if (this == other) {
            return true;
        }

        // Check if the object is an instance of Group
        if (!(other instanceof Group)) {
            throw new ClassCastException();
        }

        // Cast the other object to Group and compare fields
        Group otherGroup = (Group) other;
        return this.groupName.equals(otherGroup.groupName)
                && this.members.equals(otherGroup.members);
    }
}
