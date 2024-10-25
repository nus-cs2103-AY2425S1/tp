package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

/**
 * An immutable Appointment that is serializable to JSON format.
 */
public class JsonSerializableAppointment {

    public static final String MESSAGE_CONFLICTING_APPOINTMENT =
            "Appointment list contains conflicting appointment(s).";

    private final List<JsonAdaptedAppointment> appointments;

    /**
     * Constructs a {@code JsonSerializableAppointment} with the given appointments.
     */
    @JsonCreator
    public JsonSerializableAppointment(@JsonProperty("appointments") List<JsonAdaptedAppointment> appointments) {
        this.appointments = new ArrayList<>(appointments);
    }

    /**
     * Converts this appointment list into the model's {@code List<Appointment>} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public List<Appointment> toModelType() throws IllegalValueException {
        List<Appointment> appointmentList = new ArrayList<>();
        for (JsonAdaptedAppointment jsonAdaptedAppointment : appointments) {
            Appointment appointment = jsonAdaptedAppointment.toModelType();
            if (appointmentList.stream().anyMatch(appointment::hasConflictWith)) {
                throw new IllegalValueException(MESSAGE_CONFLICTING_APPOINTMENT);
            }
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
}
