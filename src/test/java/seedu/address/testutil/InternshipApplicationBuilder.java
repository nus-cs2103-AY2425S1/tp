package seedu.address.testutil;

import seedu.address.model.internshipapplication.Company;
import seedu.address.model.internshipapplication.Date;
import seedu.address.model.internshipapplication.Email;
import seedu.address.model.internshipapplication.InternshipApplication;
import seedu.address.model.internshipapplication.Name;
import seedu.address.model.internshipapplication.Role;


public class InternshipApplicationBuilder {
    public static final String DEFAULT_NAME = "Amazon";
    public static final String DEFAULT_ROLE = "Software Engineer Intern";
    public static final String DEFAULT_EMAIL = "amazon@gmail.com";
    public static final String DEFAULT_DATE_OF_APPLICATION = "12/03/24";

    private Company company;
    private Name name;
    private Email email;
    private Role role;
    private Date dateOfApplication;

    /**
     * Creates a {@code InternshipApplicationBuilder} with the default details.
     */
    public InternshipApplicationBuilder() {
        name = new Name(DEFAULT_NAME);
        email = new Email(DEFAULT_EMAIL);
        company = new Company(email, name);
        role = new Role(DEFAULT_ROLE);
        dateOfApplication = new Date(DEFAULT_DATE_OF_APPLICATION);
       
    }

    /**
     * Initializes the InternshipApplicationBuilder with the data of {@code internshipApplicationToCopy}.
     */
    public InternshipApplicationBuilder(InternshipApplication internshipApplicationToCopy) {
        company = internshipApplicationToCopy.getCompany();
        role = internshipApplicationToCopy.getRole();
        dateOfApplication = internshipApplicationToCopy.getDateOfApplication();

    }

    /**
     * Sets the {@code Name} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withName(String name) {
        this.name = new Name(name);
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
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code InternshipApplication} that we are building.
     */
    public InternshipApplicationBuilder withDate(String date) {
        this.dateOfApplication = new Date(date);
        return this;
    }

    public InternshipApplication build() {
        company = new Company(email, name);
        return new InternshipApplication(company, dateOfApplication, role);
    }
    
}
