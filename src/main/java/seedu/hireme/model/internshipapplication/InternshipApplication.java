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
    private Status status;

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
     * Constructs an {@code InternshipApplication} with the specified company, date of application, role, and status.
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
     * @return The {@code Company} object.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Returns the name of the company.
     *
     * @return The {@code Name} object of the company.
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
     * @return The {@code Date} of application.
     */
    public Date getDateOfApplication() {
        return dateOfApplication;
    }

    /**
     * Returns the role applied for in the internship.
     *
     * @return The {@code Role} object.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Returns the status of the internship.
     *
     * @return The {@code Status} enum.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Updates the status of the internship application.
     *
     * @param status The new status to be set.
     */
    public void setStatus(Status status) {
        this.status = requireNonNull(status);
    }

    /**
     * Returns true if both internship applications have the same company, date of application, and role.
     * This defines a weaker notion of equality between two internship applications.
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
                && otherInternship.getRole().equals(getRole())
                && otherInternship.getStatus().equals(getStatus());
    }

    /**
     * Returns true if both internship applications have the same identity and data fields.
     * This defines a stronger notion of equality between two internship applications.
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
                && role.equals(otherInternship.role)
                && status.equals(otherInternship.status);
    }

    /**
     * Returns the hash code of the internship application based on the company, date of application, role, and status.
     *
     * @return The hash code of the internship application.
     */
    @Override
    public int hashCode() {
        return Objects.hash(company, dateOfApplication, role, status);
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

        if (status != null) {
            tsb.add("Status", status);
        }

        return tsb.toString();
    }

    /**
     * Creates and returns a deep copy of this {@code InternshipApplication}.
     * <p>
     * The deep copy ensures that the new {@code InternshipApplication} is a completely independent
     * copy, meaning any changes to the new object will not affect the original object.
     * This method creates a new {@code Company} instance to avoid sharing mutable state, but since
     * {@code String} and {@code Status} are immutable, those are shared directly.
     * </p>
     *
     * @return A new {@code InternshipApplication} instance with the same values as this instance.
     */
    public InternshipApplication deepCopy() {
        return new InternshipApplication(
                new Company(this.company.getEmail(), this.company.getName()), // Deep copy of mutable Company
                this.dateOfApplication, // String is immutable, safe to share reference
                this.role, // String is immutable, safe to share reference
                this.status // Status is immutable (enum), safe to share reference
        );
    }
}
