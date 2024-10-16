package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Role;

/**
 * Jackson-friendly version of {@link Role}.
 */
class JsonAdaptedRole {

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
        roleName = source.name();
    }

    @JsonValue
    public String getRoleName() {
        return roleName;
    }

    /**
     * Converts this Jackson-friendly adapted role object into the model's
     * {@code Role} object.
     *
     * @throws IllegalValueException if the given role name is invalid.
     */
    public Role toModelType() throws IllegalValueException {
        try {
            return Role.valueOf(roleName.toUpperCase());
        } catch (NullPointerException e) {
            throw new IllegalValueException(String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT, "Role"));
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
    }

}
