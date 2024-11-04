package seedu.address.model.person;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Utility class for handling role-specific information for different types of Person instances.
 */
public class PersonUtil {


    /**
     * Retrieves role-specific information based on the role of the given {@code Person}.
     * <p>
     * This method determines the role of the {@code Person} instance and extracts
     * specific information related to that role:
     * <ul>
     *     <li>If the person is a {@code Volunteer}, it retrieves the volunteer's hours as a string.</li>
     *     <li>If the person is a {@code Donor}, it retrieves the donor's donated amount as a string.</li>
     *     <li>If the person is a {@code Partner}, it retrieves the partner's partnership end date as a string.</li>
     * </ul>
     * </p>
     *
     * @param person the {@code Person} instance whose role-specific information is to be retrieved.
     * @return a {@code String} containing the role-specific information.
     * @throws CommandException if the {@code Person} has an unknown role or if the role is unsupported.
     */
    public static String getRoleSpecificInfoString(Person person) throws CommandException {
        Role role = person.getRole();
        switch (role) {
        case VOLUNTEER:
            Volunteer volunteer = (Volunteer) person;
            String hoursString = volunteer.getHours().toString();
            return "Hours: " + hoursString;
        case DONOR:
            Donor donor = (Donor) person;
            String donatedAmountString = donor.getDonatedAmount().toString();
            return "Donated Amount: " + donatedAmountString;
        case PARTNER:
            Partner partner = (Partner) person;
            String partnershipEndDateString = partner.getEndDate().toString();
            return "Partnership End Date: " + partnershipEndDateString;
        default:
            throw new CommandException("Unknown role: " + role);
        }
    }
}
