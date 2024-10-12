package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.appointment.Medicine;
import seedu.address.model.appointment.Sickness;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    private final String appointmentType;
    private final String appointmentDateTime;
    private final String personId;
    private final String sickness;
    private final String medicine;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("appointmentType") String appointmentType,
                                  @JsonProperty("appointmentDateTime") String appointmentDateTime,
                             @JsonProperty("personId") String personId, @JsonProperty("sickness") String sickness,
                                  @JsonProperty("medicine") String medicine) {
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
        appointmentType = source.getAppointmentType().value;
        appointmentDateTime = source.getAppointmentDateTime().toString();
        personId = String.valueOf(source.getPersonId());
        sickness = source.getSickness().value;
        medicine = source.getMedicine().value;
    }

    /**
     * Converts this Jackson-friendly adapted Appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Appointment toModelType() throws IllegalValueException {
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
        if (!LocalDateTime.isValidPhone(appointmentDateTime)) {
            throw new IllegalValueException(LocalDateTime.MESSAGE_CONSTRAINTS);
        }
        final LocalDateTime modelAppointmentDateTime = new LocalDateTime(appointmentDateTime);

        if (personId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PersonId.class.getSimpleName()));
        }
        if (!Email.isValidEmail(personId)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final int modelPersonId = personId;

        if (sickness == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Sickness.class.getSimpleName()));
        }
        if (!Sickness.isValidSickness(sickness)) {
            throw new IllegalValueException(Sickness.MESSAGE_CONSTRAINTS);
        }
        final Sickness modelSickness = new Sickness(sickness);

        if (medicine == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Medicine.class.getSimpleName()));
        }
        if (!Medicine.isValidMedicine(medicine)) {
            throw new IllegalValueException(Sickness.MESSAGE_CONSTRAINTS);
        }
        final Medicine modelMedicine = new Medicine(medicine);

        return new Appointment(modelAppointmentType, modelAppointmentDateTime, modelEmail,
                modelSickness, modelMedicine);
    }


}
