package seedu.eventfulnus.model.person.role.volunteer;

/**
 * Represents the String form of a VolunteerRole shown to the user.
 */
public class VolunteerRoleString {
    public static String getVolunteerRoleString(VolunteerRole volunteerRole) {
        return switch(volunteerRole) {
        case PHOTOGRAPHER -> "Photographer";
        case EMCEE -> "Emcee";
        case USHER -> "Usher";
        case LOGISTICS -> "Logistics";
        case FIRST_AID -> "First Aid";
        case BOOTH_MANNER -> "Booth Manner";
        };
    }
}
