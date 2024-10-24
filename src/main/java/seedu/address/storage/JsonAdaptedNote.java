package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.note.Note;

/**
 * Jackson-friendly version of {@link Note}.
 */
public class JsonAdaptedNote {
    private final List<String> medication;
    private final List<String> appointments;
    private final List<String> remark;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given parameters
     * @param appointments A valid appointment
     * @param remark A valid remark
     * @param medication A valid medications
     */
    @JsonCreator
    public JsonAdaptedNote(
            @JsonProperty("medications") List<String> medication,
            @JsonProperty("appointments") List<String> appointments,
            @JsonProperty("remark") List<String> remark) {
        this.medication = medication != null ? medication : new ArrayList<>();
        this.appointments = appointments != null ? appointments : new ArrayList<>();
        this.remark = remark != null ? remark : new ArrayList<>();
    }

    /**
     * Converts a given {@code Note} into this class for Jackson use.
     */
    public JsonAdaptedNote(Note source) {
        this.medication = new ArrayList<>(source.medications);
        this.appointments = new ArrayList<>(source.previousAppointments.stream().map(Appointment::toString).toList());
        this.remark = new ArrayList<>(source.remarks);
    }
    /**
     * Converts this Jackson-friendly adapted note object into the model's {@code Note} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Note toModelType() throws IllegalValueException {
        Note note = new Note();
        boolean[] error = new boolean[(1)];
        error[0] = false;

        appointments.forEach(x -> {
            if (!Appointment.isValidAppointment(x)) {
                error[0] = true;
            }
        });

        remark.forEach(x -> {
            if (!Note.isValidString(x)) {
                error[0] = true;
            }
        });

        medication.forEach(x -> {
            if (!Note.isValidString(x)) {
                error[0] = true;
            }
        });

        if (error[0]) {
            throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
        }

        appointments.forEach(note::addAppointment);
        remark.forEach(note::addRemark);
        medication.forEach(note::addMedication);

        return note;
    }
}
