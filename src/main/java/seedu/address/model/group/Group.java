package seedu.address.model.group;

import java.util.List;

import seedu.address.model.person.Person;

public class Group {
    private final String groupName;
    private final List<Person> members;

    public Group(String groupName, List<Person> members) {
        this.groupName = groupName;
        this.members = members;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<Person> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        return String.format("Group: %s, Members: %s", groupName, members);
    }
}
