package seedu.hireme.model.internshipapplication;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.hireme.commons.util.ToStringBuilder;

/**
 * Represents an internship application.
 * Each instance of {@code InternshipApplication} contains details about the company, date of application,
 * role applied for, and the current status of the application. This class is immutable except for the status field.
 */
public class InternshipApplication {

    // Identity fields
    private final Company company;
    private final Date dateOfApplication;
    private final Role role;
    private Status status;

    /**
     * Constructs an {@code InternshipApplication} with the specified company, date of application, and role.
     * The initial status is set to PENDING by default.
     *
     * @param company The company offering the internship.
     * @param dateOfApplication The date when the application was submitted.
     * @param role The position applied for in the internship.
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
     *
     * @param company The company offering the internship.
     * @param dateOfApplication The date when the application was submitted.
     * @param role The position applied for in the internship.
     * @param status The current status of the internship application.
     * @throws NullPointerException if any of the provided arguments are null.
     */
    public InternshipApplication(Company company, Date dateOfApplication, Role role, Status status) {
        requireNonNull(company);
        requireNonNull(dateOfApplication);
        requireNonNull(role);
        requireNonNull(status);

        this.company = company;
        this.dateOfApplication = dateOfApplication;
        this.role = role;
        this.status = status;
    }

    /**
     * Returns the company associated with the internship application.
     *
     * @return The {@code Company} object representing the internship provider.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Returns the name of the company as a {@code Name} object.
     *
     * @return The company's name.
     */
    public Name getCompanyName() {
        return company.getName();
    }

    /**
     * Returns the string representation of the company's name.
     *
     * @return The name of the company as a string.
     */
    public String getCompanyNameValue() {
        return company.getNameValue();
    }

    /**
     * Returns the date when the application was submitted.
     *
     * @return The {@code Date} object representing the application date.
     */
    public Date getDateOfApplication() {
        return dateOfApplication;
    }

    /**
     * Returns the position applied for in the internship.
     *
     * @return The {@code Role} object representing the applied position.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Returns the current status of the internship application.
     *
     * @return The {@code Status} enum representing the application status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Updates the status of the internship application.
     *
     * @param status The new status to set.
     */
    public void setStatus(Status status) {
        requireNonNull(status);
        this.status = status;
    }

    /**
     * Checks if this application is the same as another application by comparing company, date, and role.
     *
     * @param otherInternship The other internship application to compare.
     * @return True if the specified internship application matches the company, date, and role; false otherwise.
     */
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
     * Checks if this application is equal to another object based on company, date, role, and status.
     *
     * @param other The other object to compare.
     * @return True if both internship applications have the same fields, false otherwise.
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
     * Returns the hash code for this internship application, based on its fields.
     *
     * @return The hash code of this internship application.
     */
    @Override
    public int hashCode() {
        return Objects.hash(company, dateOfApplication, role, status);
    }

    /**
     * Returns a string representation of the internship application, including company, date, role, and status.
     *
     * @return A formatted string describing this internship application.
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
     * Creates a deep copy of this {@code InternshipApplication}.
     * The new instance is independent of the original, meaning modifications to it do not affect the original.
     *
     * @return A new {@code InternshipApplication} with the same values as this instance.
     */
    public InternshipApplication deepCopy() {
        return new InternshipApplication(
                new Company(this.company.getEmail(), this.company.getName()), // Deep copy of mutable Company
                this.dateOfApplication,
                this.role,
                this.status
        );
    }
}
