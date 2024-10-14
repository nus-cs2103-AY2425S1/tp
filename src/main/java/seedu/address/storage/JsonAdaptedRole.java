package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.role.Role;
import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.exceptions.InvalidRoleException;

/**
 * Jackson-friendly version of {@link Role}.
 */
public class JsonAdaptedRole {
    private final String roleName;

    /**
     * Constructs a {@code JsonAdaptedRole} with the given {@code roleName}.
     */
    @JsonCreator
    public JsonAdaptedRole(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Converts a given {@code Role} into this class for Jackson use.
     */
    public JsonAdaptedRole(Role source) {
        roleName = source.getRoleName();
    }

    @JsonValue
    public String getRoleName() {
        return roleName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Role} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Role toModelType() throws IllegalValueException {
        RoleHandler rh = new RoleHandler();
        try {
            return rh.getRole(roleName);
        } catch (InvalidRoleException e) {
            throw new IllegalValueException(RoleHandler.MESSAGE_CONSTRAINTS);
        }
    }

}
