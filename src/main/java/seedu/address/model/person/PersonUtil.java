package seedu.address.model.person;


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
     */
    public static String getRoleSpecificInfoString(Person person) {
        Role role = person.getRole();
        switch (role) {
        case VOLUNTEER:
            Volunteer volunteer = (Volunteer) person;
            return "; Hours: " + volunteer.getHours();
        case DONOR:
            Donor donor = (Donor) person;
            return "; Donated Amount: " + donor.getDonatedAmount();
        case PARTNER:
            Partner partner = (Partner) person;
            return "; Partnership End Date: " + partner.getEndDate();
        default:
            return ""; //return an empty string by default
        }
    }
}
