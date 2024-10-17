package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.validator.DateValidator;
import seedu.address.logic.validator.EmailValidator;
import seedu.address.logic.validator.NameValidator;
import seedu.address.logic.validator.RoleValidator;
import seedu.address.model.internshipapplication.Company;
import seedu.address.model.internshipapplication.Date;
import seedu.address.model.internshipapplication.Email;
import seedu.address.model.internshipapplication.InternshipApplication;
import seedu.address.model.internshipapplication.Name;
import seedu.address.model.internshipapplication.Role;

/**
 * Jackson-friendly version of {@link InternshipApplication}.
 */
class JsonAdaptedInternship {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Internship application's %s field is missing!";

    private final String companyName;
    private final String companyEmail;
    private final String role;
    private final String dateString;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given internship application details.
     */
    @JsonCreator
    public JsonAdaptedInternship(@JsonProperty("companyName") String companyName,
                                 @JsonProperty("companyEmail") String companyEmail,
            @JsonProperty("role") String role, @JsonProperty("date") String date) {
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.role = role;
        //  this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yy"));
        this.dateString = date;
    }

    /**
     * Converts a given {@code InternshipApplication} into this class for Jackson use.
     */
    public JsonAdaptedInternship(InternshipApplication source) {
        companyName = source.getCompany().getName().getValue();
        companyEmail = source.getCompany().getEmail().getValue();
        role = source.getRole().getValue();
        dateString = source.getDateOfApplication().getValue().format(DateValidator.FORMATTER);
    }

    /**
     * Converts this Jackson-friendly adapted internship application object into the model's
     * {@code InternshipApplication} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted internship application.
     */
    public InternshipApplication toModelType() throws IllegalValueException {
        if (companyName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        if (!NameValidator.of().validate(companyName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        if (companyEmail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }

        if (!EmailValidator.of().validate(companyEmail)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }

        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        }

        if (!RoleValidator.of().validate(role)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }

        if (dateString == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        if (!DateValidator.of().validate(dateString)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }

        Name name = new Name(companyName);
        Email email = new Email(companyEmail);
        Company company = new Company(email, name);
        Role role = new Role(this.role);
        Date date = new Date(this.dateString);

        return new InternshipApplication(company, date, role);
    }

}
