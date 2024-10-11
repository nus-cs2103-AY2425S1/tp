package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.role.Role;
import seedu.address.model.role.RoleHandler;

public class JsonAdaptedRoleTest {
    private static final String VALID_ROLE_ATTENDEE = "attendee";
    private static final String VALID_ROLE_SPONSOR = "sponsor";
    private static final String INVALID_ROLE = "invalidRole";

    @Test
    public void toModelType_validRole_returnsRole() throws Exception {
        JsonAdaptedRole adaptedRole = new JsonAdaptedRole(VALID_ROLE_ATTENDEE);
        Role expectedRole = new RoleHandler().getRole(VALID_ROLE_ATTENDEE);
        assertEquals(expectedRole, adaptedRole.toModelType());
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedRole adaptedRole = new JsonAdaptedRole(INVALID_ROLE);
        String expectedMessage = RoleHandler.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, adaptedRole::toModelType);
    }
}