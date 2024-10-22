package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.appointment.Medicine;
import seedu.address.model.appointment.Sickness;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";
    public static final String INTEGER_CHECK_MESSAGE_FORMAT = "Person ID must be a positive integer.";
    public static final String PERSON_CHECK_MESSAGE_FORMAT = "Person with person ID exist in address book";
    public static final String DATE_TIME_CONSTRAINTS =
            "Appointment DateTime must be in the format yyyy-MM-dd'T'HH:mm:ss";
    private final Integer appointmentId;
    private final String appointmentType;
    private final String appointmentDateTime;
    private final Integer personId;
    private final String sickness;
    private final String medicine;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(
            @JsonProperty("appointmentId") int appointmentId,
            @JsonProperty("appointmentType") String appointmentType,
            @JsonProperty("appointmentDateTime") String appointmentDateTime,
            @JsonProperty("personId") int personId,
            @JsonProperty("sickness") String sickness,
            @JsonProperty("medicine") String medicine) {
        this.appointmentId = appointmentId;
        this.appointmentType = appointmentType;
        this.appointmentDateTime = appointmentDateTime;
        this.personId = personId;
        this.sickness = sickness;
        this.medicine = medicine;
    }
    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        appointmentId = source.getAppointmentId();
        appointmentType = source.getAppointmentType().value;
        appointmentDateTime = source.getAppointmentDateTime().toString();
        personId = source.getPerson().getPersonId();
        sickness = String.valueOf(source.getSickness());
        medicine = String.valueOf(source.getMedicine());
    }

    /**
     * Validates and parse the date-time
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) throws IllegalValueException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        try {
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(DATE_TIME_CONSTRAINTS);
        }
    }

    /**
     * Converts this Jackson-friendly adapted Appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Appointment toModelType(ReadOnlyAddressBook addressBook) throws IllegalValueException {
        if (appointmentType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppointmentType.class.getSimpleName()));
        }
        if (!AppointmentType.isValidAppointmentType(appointmentType)) {
            throw new IllegalValueException(AppointmentType.MESSAGE_CONSTRAINTS);
        }
        final AppointmentType modelAppointmentType = new AppointmentType(appointmentType);

        if (appointmentDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDateTime.class.getSimpleName()));
        }
        final LocalDateTime modelAppointmentDateTime = parseDateTime(appointmentDateTime);

        if (personId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Integer.class.getSimpleName()));
        }
        if (personId <= 0) {
            throw new IllegalValueException(String.format(INTEGER_CHECK_MESSAGE_FORMAT));
        }
        final int modelPersonId = personId;

        final Optional<Person> modelPersonOptional = addressBook.findPerson(modelPersonId);
        if (!modelPersonOptional.isPresent()) {
            throw new IllegalValueException(String.format(PERSON_CHECK_MESSAGE_FORMAT));
        }
        Person modelPerson = modelPersonOptional.get();

        if (!Sickness.isValidSickness(sickness)) {
            throw new IllegalValueException(Sickness.MESSAGE_CONSTRAINTS);
        }
        final Sickness modelSickness = new Sickness(sickness);

        if (!Medicine.isValidMedicine(medicine)) {
            throw new IllegalValueException(Medicine.MESSAGE_CONSTRAINTS);
        }
        final Medicine modelMedicine = new Medicine(medicine);

        return new Appointment(modelAppointmentType, modelAppointmentDateTime, modelPerson,
                modelSickness, modelMedicine, appointmentId);
    }


}
