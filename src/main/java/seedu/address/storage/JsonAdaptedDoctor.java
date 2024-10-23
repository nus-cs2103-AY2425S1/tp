package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.DoctorName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;

/**
 * Constructs a {@code JsonAdaptedDoctor} with the given person details.
 */
public class JsonAdaptedDoctor {
    private final String doctorName;
    private final String doctorPhone;
    private final String doctorEmail;

    /**
     * Constructs a {@code JsonAdaptedDoctor} with the given {@code doctorName},
     * {@code doctorPhone} and {@code doctorEmail}.
     */
    @JsonCreator
    public JsonAdaptedDoctor(@JsonProperty("doctorName") String doctorName,
                                       @JsonProperty("doctorPhone") String doctorPhone,
                                       @JsonProperty("doctorEmail") String doctorEmail) {
        this.doctorName = doctorName;
        this.doctorPhone = doctorPhone;
        this.doctorEmail = doctorEmail;
    }

    /**
     * Converts a given {@code Doctor} into this class for Jackson use.
     */
    public JsonAdaptedDoctor(Doctor source) {
        doctorName = source.getName().fullName;
        doctorPhone = source.getPhone().value;
        doctorEmail = source.getEmail().value;
    }

    /**
     * Converts this Jackson-friendly adapted Doctor object into the model's {@code Doctor} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Doctor toModelType() throws IllegalValueException {
        if (doctorName == null) {
            throw new IllegalValueException(String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                    DoctorName.class.getSimpleName()));
        }
        if (!DoctorName.isValidName(doctorName)) {
            throw new IllegalValueException(DoctorName.MESSAGE_CONSTRAINTS);
        }
        final DoctorName modelDoctorName = new DoctorName(doctorName);

        if (doctorPhone == null) {
            throw new IllegalValueException(String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                    Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(doctorPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelDoctorPhone = new Phone(doctorPhone);

        if (doctorEmail == null) {
            throw new IllegalValueException(String.format(JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT,
                    Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(doctorEmail)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }

        final Email modelDoctorEmail = new Email(doctorEmail);

        return new Doctor(modelDoctorName, modelDoctorPhone, modelDoctorEmail);
    }
}
