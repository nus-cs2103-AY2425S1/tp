package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.model.volunteer.VolunteerDates;

/**
 * Jackson-friendly version of {@link Volunteer}.
 */
class JsonAdaptedVolunteer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Volunteer's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String availableDate;
    private final List<String> involvedIn;

    /**
     * Constructs a {@code JsonAdaptedVolunteer} with the given volunteer details.
     */
    @JsonCreator
    public JsonAdaptedVolunteer(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                                @JsonProperty("email") String email,
                                @JsonProperty("availableDate") String availableDate,
                                @JsonProperty("involvedIn") List<String> involvedIn) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.availableDate = availableDate;
        this.involvedIn = involvedIn != null ? involvedIn : new ArrayList<>();
    }

    /**
     * Converts a given {@code Volunteer} into this class for Jackson use.
     */
    public JsonAdaptedVolunteer(Volunteer source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        availableDate = source.getAvailableDates().toParsableString();
        involvedIn = source.getEvents();
    }

    /**
     * Converts this Jackson-friendly adapted volunteer object into the model's {@code volunteer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted volunteer.
     */
    public Volunteer toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (availableDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    VolunteerDates.class.getSimpleName()));
        }
        if (!VolunteerDates.isValidListOfDates(availableDate)) {
            throw new IllegalValueException(VolunteerDates.MESSAGE_CONSTRAINTS);
        }
        final VolunteerDates modelAvailableDate = new VolunteerDates(availableDate);

        if (involvedIn == null) {
            throw new IllegalValueException("Involved events list is missing!");
        }

        return new Volunteer(modelName, modelPhone, modelEmail, modelAvailableDate, involvedIn);
    }

}
