package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.internshipapplication.*;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedInternship {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String companyName;
    private final String companyEmail;
    private final String role;
    private final LocalDate date;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedInternship(@JsonProperty("companyName") String companyName, @JsonProperty("companyEmail") String companyEmail,
            @JsonProperty("role") String role, @JsonProperty("date") String date ) {
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.role = role;
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedInternship(InternshipApplication source) {
        companyName = source.getCompany().getName().getValue();
        companyEmail = source.getCompany().getEmail().getValue();
        role = source.getRole().getValue();
        date = source.getDateOfApplication().getValue();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public InternshipApplication toModelType() throws IllegalValueException {
        Name name = new Name(companyName);
        Email email = new Email(companyEmail);
        Company company = new Company(email, name);
        Role role = new Role(this.role);
        Date date = new Date(this.date);
        return new InternshipApplication(company, date, role);
    }

}
