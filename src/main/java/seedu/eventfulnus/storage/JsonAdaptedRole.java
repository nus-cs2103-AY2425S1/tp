package seedu.eventfulnus.storage;

import static seedu.eventfulnus.logic.parser.ParserUtil.parseRole;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.eventfulnus.commons.exceptions.IllegalValueException;
import seedu.eventfulnus.model.person.role.Role;

/**
 * Jackson-friendly version of {@link Role}.
 */
class JsonAdaptedRole {

    private final String roleName;

    /**
     * Constructs a {@code JsonAdaptedRole} with the given {@code roleName}.
     */
    @JsonCreator
    public JsonAdaptedRole(@JsonProperty("name") String roleName) {
        this.roleName = roleName;
    }

    /**
     * Converts a given {@code Role} into this class for Jackson use.
     */
    public JsonAdaptedRole(Role source) {
        roleName = source.getRoleName();
    }

    @JsonProperty("name")
    public String getRoleName() {
        return roleName;
    }

    /**
     * Converts this Jackson-friendly adapted role object into the model's {@code Role} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted role.
     */
    public Role toModelType() throws IllegalValueException {
        if (!Role.isValidRoleName(roleName)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
        return parseRole(roleName);
    }

}
