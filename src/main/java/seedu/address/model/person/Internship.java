package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Internship in the network book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Internship {

    // Identity fields
    private final Company company;
    private final Date dateOfApplication;
    private final Role role;

    /**
     * Constructs an {@code Internship} with a company, date of application, and role.
     * Company, date of application, and role must be present and not null.
     */
    public Internship(Company company, Date dateOfApplication, Role role) {
        requireNonNull(company);
        requireNonNull(dateOfApplication);
        requireNonNull(role);
        this.company = company;
        this.dateOfApplication = dateOfApplication;
        this.role = role;
    }

    /**
     * Returns the company.
     *
     * @return the company object.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Returns the date of application.
     *
     * @return the date of application.
     */
    public Date getDateOfApplication() {
        return dateOfApplication;
    }

    /**
     * Returns the role.
     *
     * @return the role object.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Returns true if both internships have the same company, date of application, and role.
     * This defines a weaker notion of equality between two internships.
     */
    public boolean isSame(Internship otherInternship) {
        if (otherInternship == this) {
            return true;
        }

        return otherInternship != null
                && otherInternship.getCompany().equals(getCompany())
                && otherInternship.getDateOfApplication().equals(getDateOfApplication())
                && otherInternship.getRole().equals(getRole());
    }

    /**
     * Returns true if both internships have the same identity and data fields.
     * This defines a stronger notion of equality between two internships.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Internship)) {
            return false;
        }

        Internship otherInternship = (Internship) other;
        return company.equals(otherInternship.company)
                && dateOfApplication.equals(otherInternship.dateOfApplication)
                && role.equals(otherInternship.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, dateOfApplication, role);
    }

    /**
     * Returns a string representation of the internship, including conditional checks for null or empty fields.
     *
     * @return a string representing the internship.
     */
    @Override
    public String toString() {
        ToStringBuilder tsb = new ToStringBuilder(this)
                .add("Company", company);

        if (dateOfApplication != null) {
            tsb.add("Date of Application", dateOfApplication);
        }

        if (role != null) {
            tsb.add("Role", role);
        }

        return tsb.toString();
    }
}
