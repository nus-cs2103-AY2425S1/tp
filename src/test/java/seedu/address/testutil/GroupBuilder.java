package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Group objects.
 */
public class GroupBuilder {

    public static final String DEFAULT_GROUP_NAME = "Study Group";
    public static final List<Person> DEFAULT_MEMBERS = new ArrayList<>();

    private GroupName groupName;
    private List<Person> members;

    /**
     * Creates a {@code GroupBuilder} with the default details.
     */
    public GroupBuilder() {
        groupName = new GroupName(DEFAULT_GROUP_NAME);
        members = new ArrayList<>(DEFAULT_MEMBERS);
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public GroupBuilder(Group groupToCopy) {
        groupName = groupToCopy.getGroupName();
        members = new ArrayList<>(groupToCopy.getMembers());
    }

    /**
     * Sets the {@code GroupName} of the {@code Group} that we are building.
     */
    public GroupBuilder withGroupName(String groupName) {
        this.groupName = new GroupName(groupName);
        return this;
    }

    /**
     * Sets the {@code members} of the {@code Group} that we are building.
     */
    public GroupBuilder withMembers(Person... persons) {
        this.members = new ArrayList<>(Arrays.asList(persons));
        return this;
    }

    /**
     * Builds and returns the {@code Group}.
     */
    public Group build() {
        return new Group(groupName.toString(), members);
    }
}
