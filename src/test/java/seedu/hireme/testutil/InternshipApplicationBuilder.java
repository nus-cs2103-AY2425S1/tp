package seedu.hireme.testutil;

import seedu.hireme.model.internshipapplication.Company;
import seedu.hireme.model.internshipapplication.Date;
import seedu.hireme.model.internshipapplication.Email;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.Name;
import seedu.hireme.model.internshipapplication.Role;
import seedu.hireme.model.internshipapplication.Status;

/**
 * A utility class to help with building InternshipApplication objects.
 */
public class InternshipApplicationBuilder {

    public static final String DEFAULT_COMPANY_NAME = "Google";
    public static final String DEFAULT_EMAIL = "google@gmail.com";
    public static final String DEFAULT_DATE = "01/01/24";
    public static final String DEFAULT_ROLE = "SWE";
    public static final String DEFAULT_STATUS = "PENDING";

    private Company company;
    private Date date;
    private Role role;
    private Status status;

    /**
     * Creates a {@code InternshipApplicationBuilder} with the default details.
     */
    public InternshipApplicationBuilder() {
        Name name = new Name(DEFAULT_COMPANY_NAME);
        Email email = new Email(DEFAULT_EMAIL);
        company = new Company(email, name);
        date = new Date(DEFAULT_DATE);
        role = new Role(DEFAULT_ROLE);
        status = Status.valueOf(DEFAULT_STATUS);
    }

    /**
     * Initializes the InternshipApplicationBuilder with the data of {@code applicationToCopy}.
     */
    public InternshipApplicationBuilder(InternshipApplication applicationToCopy) {
        company = applicationToCopy.getCompany();
        date = applicationToCopy.getDateOfApplication();
        role = applicationToCopy.getRole();
    }

    /**
     * Sets the {@code Company} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withCompany(Company company) {
        this.company = new Company(company.getEmail(), company.getName());
        return this;
    }

    /**
     * Sets the {@code dateOfApplication} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withEmail(String email) {
        this.company = new Company(new Email(email), this.company.getName());
        return this;
    }

    /**
     * Sets the {@code CompanyName} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withName(String name) {
        this.company = new Company(this.company.getEmail(), new Name(name.trim()));
        return this;
    }

    /**
     * Sets the {@code status} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    public InternshipApplication build() {
        return new InternshipApplication(company, date, role);
    }
}
