package seedu.hireme.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.hireme.commons.exceptions.IllegalValueException;
import seedu.hireme.logic.validator.DateValidator;
import seedu.hireme.logic.validator.EmailValidator;
import seedu.hireme.logic.validator.NameValidator;
import seedu.hireme.logic.validator.RoleValidator;
import seedu.hireme.logic.validator.StatusValidator;
import seedu.hireme.model.internshipapplication.Company;
import seedu.hireme.model.internshipapplication.Date;
import seedu.hireme.model.internshipapplication.Email;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.Name;
import seedu.hireme.model.internshipapplication.Role;
import seedu.hireme.model.internshipapplication.Status;

/**
 * Jackson-friendly version of {@link InternshipApplication}.
 */
class JsonAdaptedInternship {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Internship application's %s field is missing!";
    public static final String MISSING_STATUS_FIELD_MESSAGE = "Status field in the Json file is missing!";

    private final String companyName;
    private final String companyEmail;
    private final String role;
    private final String dateString;

    private final String statusString;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given internship application details.
     */
    @JsonCreator
    public JsonAdaptedInternship(@JsonProperty("companyName") String companyName,
                                 @JsonProperty("companyEmail") String companyEmail,
                                 @JsonProperty("role") String role,
                                 @JsonProperty("date") String date,
                                 @JsonProperty("status") String status) {
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.role = role;
        this.dateString = date;
        this.statusString = status;
    }

    /**
     * Converts a given {@code InternshipApplication} into this class for Jackson use.
     */
    public JsonAdaptedInternship(InternshipApplication source) {
        companyName = source.getCompany().getName().toString();
        companyEmail = source.getCompany().getEmail().toString();
        role = source.getRole().toString();
        dateString = source.getDateOfApplication().toString();
        statusString = source.getStatus().toString();
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

        if (statusString == null) {
            throw new IllegalValueException(MISSING_STATUS_FIELD_MESSAGE);
        }

        if (!StatusValidator.of().validate(statusString)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }


        Name name = new Name(companyName);
        Email email = new Email(companyEmail);
        Company company = new Company(email, name);
        Role role = new Role(this.role);
        Date date = new Date(this.dateString);
        Status status = Status.valueOf(statusString);

        return new InternshipApplication(company, date, role, status);
    }

}
