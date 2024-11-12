package seedu.eventfulnus.model.person.role.volunteer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eventfulnus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class VolunteerRoleStringTest {
    @Test
    void getVolunteerRoleString_validVolunteerRole_returnsCorrectString() {
        assertEquals("Photographer", VolunteerRoleString.getVolunteerRoleString(VolunteerRole.PHOTOGRAPHER));
        assertEquals("Emcee", VolunteerRoleString.getVolunteerRoleString(VolunteerRole.EMCEE));
        assertEquals("Usher", VolunteerRoleString.getVolunteerRoleString(VolunteerRole.USHER));
        assertEquals("Logistics", VolunteerRoleString.getVolunteerRoleString(VolunteerRole.LOGISTICS));
        assertEquals("First Aid", VolunteerRoleString.getVolunteerRoleString(VolunteerRole.FIRST_AID));
        assertEquals("Booth Manner", VolunteerRoleString.getVolunteerRoleString(VolunteerRole.BOOTH_MANNER));
    }

    @Test
    void getVolunteerRoleString_nullVolunteerRole_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> VolunteerRoleString.getVolunteerRoleString(null));
    }
}
