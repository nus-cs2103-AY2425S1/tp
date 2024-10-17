package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Group;
import seedu.address.model.person.Person;

/**
 * A JSON-friendly version of a {@code Group}.
 */
public class JsonAdaptedGroup {
    private static final String MISSING_GROUP_NAME = "Group name is missing!";
    private final String groupName;

    private final List<JsonAdaptedPerson> members = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedGroup} with the given details.
     */
    @JsonCreator
    public JsonAdaptedGroup(@JsonProperty("groupName") String groupName,
                            @JsonProperty("members") List<JsonAdaptedPerson> members) {
        this.groupName = groupName;
        if (members != null) {
            this.members.addAll(members);
        }
    }

    /**
     * Constructs a {@code JsonAdaptedGroup} with the given {@code Group}.
     */
    public JsonAdaptedGroup(Group group) {
        groupName = group.getName();
        for (Person person : group) {
            members.add(new JsonAdaptedPerson(person));
        }
    }

    /**
     * Returns a {@code Group} object from this object, with the given {@code Person}s as members.
     * @throws IllegalValueException If the group name or any of the persons' fields are missing/invalid.
     */
    public Group toModelType() throws IllegalValueException {
        if (groupName == null) {
            throw new IllegalValueException(MISSING_GROUP_NAME);
        }
        Group group = new Group(groupName);
        for (JsonAdaptedPerson member : members) {
            group.add(member.toModelType());
        }
        return group;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof JsonAdaptedGroup jsonAdaptedGroup)) {
            return false;
        }
        return groupName.equals(jsonAdaptedGroup.groupName) && members.equals(jsonAdaptedGroup.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName, members);
    }
}
