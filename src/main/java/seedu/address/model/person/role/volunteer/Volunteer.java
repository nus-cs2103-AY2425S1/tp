package seedu.address.model.person.role.volunteer;

import seedu.address.model.person.role.Role;

public class Volunteer extends Role {
    private VolunteerRole volunteerRole;

    public Volunteer(VolunteerRole volunteerRole) {
        super("Volunteer - " + volunteerRole);
        this.volunteerRole = volunteerRole;
    }
}
