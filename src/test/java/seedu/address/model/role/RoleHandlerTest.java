package seedu.address.model.role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

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
    public void getRole_vendor() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            assert (roleHandler.getRole("vendor") instanceof Vendor);

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void getRole_sponsor() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            assert (roleHandler.getRole("sponsor") instanceof Sponsor);

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void getRole_volunteer() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            assert (roleHandler.getRole("volunteer") instanceof Volunteer);

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

    @Test
    public void isRole_hasRole_notInRole() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            Role role = roleHandler.getRole("attendee");
            assert (!roleHandler.isRole(BOB, "attendee"));

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void isRole_hasRole_inRole() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            Role role = roleHandler.getRole("attendee");
            role.addPerson(ALICE);
            assert (roleHandler.isRole(ALICE, "attendee"));

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void isRole_hasDifferentRole_notInRole() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            Role role = roleHandler.getRole("attendee");
            role.addPerson(ALICE);
            assert (!roleHandler.isRole(ALICE, "sponsor"));

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void isRole_invalidRole_failure() {
        RoleHandler roleHandler = new RoleHandler();

        assertThrows(InvalidRoleException.class, () -> roleHandler.isRole(ALICE, "invalid"));


    }


}
