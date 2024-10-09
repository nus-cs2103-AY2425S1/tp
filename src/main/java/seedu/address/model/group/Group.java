package seedu.address.model.group;

import java.util.List;
import java.util.StringJoiner;

import seedu.address.model.person.Person;

/**
 * Represents a group of persons.
 */
public class Group {
    private final String groupName;
    private final List<Person> members;

    /**
     * Constructs a {@code Group} with the specified group name and members.
     *
     * @param groupName The name of the group.
     * @param members   The list of persons in the group.
     * @throws NullPointerException if {@code groupName} or {@code members} is null.
     */
    public Group(String groupName, List<Person> members) {
        this.groupName = groupName;
        this.members = members;
    }

    /**
     * Returns the name of the group.
     *
     * @return The group name.
     */
    public String getGroupName() {
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
}
