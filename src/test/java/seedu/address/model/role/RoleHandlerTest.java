package seedu.address.model.role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_ATTENDEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_SPONSOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_VENDOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_VOLUNTEER;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.role.exceptions.InvalidRoleException;


public class RoleHandlerTest {
    @Test
    public void getRole_validRole_success() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            assert (RoleHandler.getRole(VALID_ROLE_ATTENDEE) instanceof Attendee);

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void getRole_vendor() {

        try {
            assert (RoleHandler.getRole(VALID_ROLE_VENDOR) instanceof Vendor);

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void getRole_sponsor() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            assert (RoleHandler.getRole(VALID_ROLE_SPONSOR) instanceof Sponsor);

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void getRole_volunteer() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            assert (RoleHandler.getRole(VALID_ROLE_VOLUNTEER) instanceof Volunteer);

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }
    @Test
    public void getRole_invalidRole_success() {
        try {
            assert (RoleHandler.getRole(INVALID_ROLE) instanceof Vendor);

        } catch (InvalidRoleException e) {
            assert (true);
        }
    }

    @Test
    public void isRole_validRole_success() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            Role role1 = RoleHandler.getRole(VALID_ROLE_ATTENDEE);
            Role role2 = RoleHandler.getRole(VALID_ROLE_ATTENDEE);
            assertEquals(role1, role2);

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void isRole_hasRole_notInRole() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            Role role = RoleHandler.getRole(VALID_ROLE_ATTENDEE);
            assert (!new RoleHandler().isRole(BOB, VALID_ROLE_ATTENDEE));

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void isRole_hasRole_inRole() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            Role role = RoleHandler.getRole(VALID_ROLE_ATTENDEE);
            role.addPerson(ALICE);
            assert (new RoleHandler().isRole(ALICE, VALID_ROLE_ATTENDEE));

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void isRole_hasDifferentRole_notInRole() {
        RoleHandler roleHandler = new RoleHandler();
        try {
            Role role = RoleHandler.getRole(VALID_ROLE_ATTENDEE);
            role.addPerson(ALICE);
            assert (!new RoleHandler().isRole(ALICE, VALID_ROLE_SPONSOR));

        } catch (InvalidRoleException e) {
            assert (false);
        }
    }

    @Test
    public void isRole_invalidRole_failure() {
        RoleHandler roleHandler = new RoleHandler();

        assertThrows(InvalidRoleException.class, () -> new RoleHandler().isRole(ALICE, INVALID_ROLE));


    }

    @Test
    public void isValidRoleName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> RoleHandler.isValidRoleName(null));
    }

    @Test
    public void isValidRoleName_invalidValue_returnsFalse() {
        assertFalse(RoleHandler.isValidRoleName("InvalidRole"));
        assertFalse(RoleHandler.isValidRoleName("Random"));
        assertFalse(RoleHandler.isValidRoleName(""));
    }

    @Test
    public void isValidRoleName_validValue_returnsTrue() {
        assertTrue(RoleHandler.isValidRoleName(Attendee.ROLE_WORD));
        assertTrue(RoleHandler.isValidRoleName(Sponsor.ROLE_WORD));
        assertTrue(RoleHandler.isValidRoleName(Vendor.ROLE_WORD));
        assertTrue(RoleHandler.isValidRoleName(Volunteer.ROLE_WORD));
    }

    @Test
    public void isValidRoleName_validValueWithWhitespace_returnsFalse() {
        assertTrue(RoleHandler.isValidRoleName(" " + Attendee.ROLE_WORD + " "));
        assertTrue(RoleHandler.isValidRoleName(" " + Sponsor.ROLE_WORD + " "));
    }

    @Test
    public void isValidRoleName_caseInsensitive_returnsFalse() {
        assertTrue(RoleHandler.isValidRoleName(Attendee.ROLE_WORD.toLowerCase()));
        assertTrue(RoleHandler.isValidRoleName(Sponsor.ROLE_WORD.toUpperCase()));
    }

}
