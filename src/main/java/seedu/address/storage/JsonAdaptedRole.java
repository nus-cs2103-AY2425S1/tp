package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.role.Role;
import seedu.address.model.role.RoleHandler;

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
     * Converts this Jackson-friendly adapted role object into the model's {@code Role} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted role.
     */
    public Role toModelType() throws IllegalValueException {
        // Throws illegal value exception instead of invalid role because of jsonadaptedRole
        try {
            return RoleHandler.getRole(roleName);
        } catch (Exception e) {
            throw new IllegalValueException(RoleHandler.MESSAGE_CONSTRAINTS);

        }
    }

}
