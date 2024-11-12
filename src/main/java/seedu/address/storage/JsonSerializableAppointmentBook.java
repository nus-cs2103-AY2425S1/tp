package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AppointmentBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.appointment.Appointment;

/**
 * An Immutable AppointmentBook that is serializable to JSON format.
 */
@JsonRootName(value = "appointmentbook")
public class JsonSerializableAppointmentBook {
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "Appointment list contains duplicate appointment(s).";

    private final List<JsonAdaptedAppointment> appointments = new ArrayList<>();
    private final int nextAppointmentId;

    /**
     * Constructs a {@code JsonSerializableAppointmentBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAppointmentBook(@JsonProperty("appointments") List<JsonAdaptedAppointment> appointments,
                                           @JsonProperty("nextAppointmentId") int nextAppointmentId) {
        this.appointments.addAll(appointments);
        this.nextAppointmentId = nextAppointmentId;
    }

    /**
     * Converts a given {@code ReadOnlyAppointmentBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAppointmentBook}.
     */
    public JsonSerializableAppointmentBook(ReadOnlyAppointmentBook source) {
        appointments.addAll(source.getAppointmentList().stream().map(JsonAdaptedAppointment::new)
                .collect(Collectors.toList()));
        nextAppointmentId = source.getNextAppointmentId();
    }

    /**
     * Converts this address book into the model's {@code AppointmentBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AppointmentBook toModelType(ReadOnlyAddressBook addressBook) throws IllegalValueException {
        AppointmentBook appointmentBook = new AppointmentBook();
        for (JsonAdaptedAppointment jsonAdaptedAppointment : appointments) {
            Appointment appointment = jsonAdaptedAppointment.toModelType(addressBook);
            if (appointmentBook.hasAppointment(appointment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_APPOINTMENT);
            }
            appointmentBook.addAppointment(appointment);
        }
        appointmentBook.setNextAppointmentId(nextAppointmentId);
        return appointmentBook;
    }

}
