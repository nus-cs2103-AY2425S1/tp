package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Role;

public class JsonAdaptedRoleTest {

    private static final String VALID_ROLE = "PATIENT";
    private static final String INVALID_ROLE = "NOT_A_ROLE";

    @Test
    public void toModelType_validRole_returnsRole() throws Exception {
        JsonAdaptedRole role = new JsonAdaptedRole(VALID_ROLE);
        assertEquals(Role.PATIENT, role.toModelType());
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedRole role = new JsonAdaptedRole(INVALID_ROLE);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, role::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedRole role = new JsonAdaptedRole((String) null);
        String expectedMessage = String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, role::toModelType);
    }
}
