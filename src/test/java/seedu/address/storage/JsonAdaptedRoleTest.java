package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.role.Role;
import seedu.address.model.role.RoleHandler;




public class JsonAdaptedRoleTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TELE = "c";
    private static final String INVALID_ROLE = "invalidRole";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();


    private static final String VALID_ROLE_ATTENDEE = "attendee";
    private static final String VALID_ROLE_SPONSOR = "sponsor";

    @Test
    public void jsonAdaptedRole_validRoleDetails_returnsRole() {
        assertDoesNotThrow(() -> new JsonAdaptedRole(VALID_ROLE_ATTENDEE));
    }

    //    @Test
    //    public void jsonAdaptedRole_invalidRole_throwsIllegalValueException() {
    //        assertThrows(IllegalArgumentException.class, () -> new JsonAdaptedRole(INVALID_ROLE));
    //
    //    }



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
