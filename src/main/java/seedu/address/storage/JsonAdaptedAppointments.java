package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.appointment.Appointment;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
class JsonAdaptedAppointment {

    private final String appointmentSerialised;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given {@code appointmentSerialised}.
     */
    @JsonCreator
    public JsonAdaptedAppointment(String appointmentSerialised) {
        this.appointmentSerialised = appointmentSerialised;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        appointmentSerialised = source.getAppointmentName()
                                + ":" + source.getAppointmentDate()
                                + ":" + source.getAppointmentTimePeriod();
    }

    @JsonValue
    public String getAppointmentSerialised() {
        return appointmentSerialised;
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment toModelType() throws IllegalValueException {
        String[] appointmentDeserialised = appointmentSerialised.split(":");
        if (appointmentDeserialised.length < 3) {
            throw new IllegalValueException(Appointment.MESSAGE_CONSTRAINTS);
        }

        if (Appointment.isValidAppointmentName(appointmentDeserialised[0])) {
            throw new IllegalValueException(Appointment.MESSAGE_CONSTRAINTS);
        }

        if (DateUtil.isValidDate(appointmentDeserialised[1])) {
            throw new IllegalValueException(Appointment.MESSAGE_CONSTRAINTS_APPT_DATE_WRONG_FORMAT);
        }

        Appointment.checkIsTimePeriodValid(appointmentDeserialised[2]);

        return new Appointment(appointmentDeserialised[0], appointmentDeserialised[1], appointmentDeserialised[2]);
    }

}
