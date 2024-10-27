package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;

/**
 * Jackson-friendly version of {@link Group}.
 */
class JsonAdaptedGroup {

    private final String groupName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedGroup(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        groupName = source.groupName;
    }

    @JsonValue
    public String getGroupName() {
        return groupName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Group toModelType() throws IllegalValueException {
        if (!Group.isValidGroupName(groupName)) {
            throw new IllegalValueException(Group.MESSAGE_CONSTRAINTS);
        }
        return new Group(groupName);
    }

}
