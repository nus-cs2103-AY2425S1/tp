package seedu.hireme.model.internshipapplication;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.hireme.commons.util.ToStringBuilder;
import seedu.hireme.model.HireMeComparable;

/**
 * Represents an InternshipApplication in the system.
 * Guarantees: details are present and not null, field values are validated, and the object is immutable.
 */
public class InternshipApplication implements HireMeComparable<InternshipApplication> {

    // Identity fields
    private final Company company;
    private final Date dateOfApplication;
    private final Role role;
    private final Status status;

    /**
     * Constructs an {@code InternshipApplication} with the specified company, date of application, and role.
     * All fields must be present and not null.
     *
     * @param company The company offering the internship.
     * @param dateOfApplication The date of application for the internship.
     * @param role The role applied for in the internship.
     * @throws NullPointerException if any of the provided arguments are null.
     */
    public InternshipApplication(Company company, Date dateOfApplication, Role role) {
        requireNonNull(company);
        requireNonNull(dateOfApplication);
        requireNonNull(role);
        this.company = company;
        this.dateOfApplication = dateOfApplication;
        this.role = role;
        this.status = Status.PENDING;
    }

    /**
     * Constructs an {@code InternshipApplication} with the specified company, date of application, and role.
     * All fields must be present and not null.
     *
     * @param company The company offering the internship.
     * @param dateOfApplication The date of application for the internship.
     * @param role The role applied for in the internship.
     * @param status The current status of the internship application.
     * @throws NullPointerException if any of the provided arguments are null.
     */
    public InternshipApplication(Company company, Date dateOfApplication, Role role, Status status) {
        requireNonNull(company);
        requireNonNull(dateOfApplication);
        requireNonNull(role);
        this.company = company;
        this.dateOfApplication = dateOfApplication;
        this.role = role;
        this.status = status;
    }

    /**
     * Returns the company associated with the internship application.
     *
     * @return The company object.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Returns the name of the company.
     *
     * @return The name object of the company.
     */
    public Name getCompanyName() {
        return company.getName();
    }

    /**
     * Returns the string value of the company's name.
     *
     * @return The company name as a string.
     */
    public String getCompanyNameValue() {
        return company.getNameValue();
    }

    /**
     * Returns the date of application for the internship.
     *
     * @return The date of application.
     */
    public Date getDateOfApplication() {
        return dateOfApplication;
    }

    /**
     * Returns the role applied for in the internship.
     *
     * @return The role object.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Returns the status ofthe internship.
     *
     * @return The status enum.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Returns true if both internship applications have the same company, date of application, and role.
     * Defines a weaker notion of equality between two internship applications.
     *
     * @param otherInternship The other internship application to compare.
     * @return True if the specified internship application is the same as the current one, false otherwise.
     */
    @Override
    public boolean isSame(InternshipApplication otherInternship) {
        if (otherInternship == this) {
            return true;
        }

        return otherInternship != null
                && otherInternship.getCompany().equals(getCompany())
                && otherInternship.getDateOfApplication().equals(getDateOfApplication())
                && otherInternship.getRole().equals(getRole());
    }

    /**
     * Returns true if both internship applications have the same identity and data fields.
     * Defines a stronger notion of equality between two internship applications.
     *
     * @param other The other object to compare.
     * @return True if the specified object is equal to the current internship application, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof InternshipApplication)) {
            return false;
        }

        InternshipApplication otherInternship = (InternshipApplication) other;
        return company.equals(otherInternship.company)
                && dateOfApplication.equals(otherInternship.dateOfApplication)
                && role.equals(otherInternship.role);
    }

    /**
     * Returns the hash code of the internship application based on the company, date of application, and role.
     *
     * @return The hash code of the internship application.
     */
    @Override
    public int hashCode() {
        return Objects.hash(company, dateOfApplication, role);
    }

    /**
     * Returns a string representation of the internship application, including conditional checks for null fields.
     *
     * @return A formatted string representing the internship application.
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
