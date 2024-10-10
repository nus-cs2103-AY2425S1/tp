package seedu.address.model.role;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.role.exceptions.InvalidRoleException;


public class RoleHandlerTest {
    @Test
    public void getRole_validRole_success() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            assert (roleHandler.getRole("attendee") instanceof Attendee);

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void getRole_invalidRole_success() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            assert (roleHandler.getRole("invalid") instanceof Vendor);

        } catch (InvalidRoleException e) {
            assert (true);
        }
    }

    @Test
    public void isRole_validRole_success() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            Role role1 = roleHandler.getRole("attendee");
            Role role2 = roleHandler.getRole("attendee");
            assertEquals(role1, role2);

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

}
