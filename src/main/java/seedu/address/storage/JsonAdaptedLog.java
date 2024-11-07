package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.log.AppointmentDate;
import seedu.address.model.log.Log;
import seedu.address.model.log.LogEntry;

/**
 * Jackson-friendly version of {@link Log}.
 */
public class JsonAdaptedLog {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Log's %s field is missing!";

    private final String entry;
    private final String appointmentDate; // Store the date as a string

    /**
     * Constructs a {@code JsonAdaptedLog} with the given log details.
     */
    @JsonCreator
    public JsonAdaptedLog(@JsonProperty("appointmentDate") String appointmentDate,
                          @JsonProperty("entry") String entry) {
        this.entry = entry;
        this.appointmentDate = appointmentDate;
    }

    /**
     * Converts a given {@code Log} into this class for Jackson use.
     */
    public JsonAdaptedLog(Log source) {
        entry = source.getEntry();
        // Convert AppointmentDate to string
        appointmentDate = source.getAppointmentDate().toString();
    }

    /**
     * Converts this Jackson-friendly adapted log object into the model's {@code Log} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted log.
     */
    public Log toModelType() throws IllegalValueException {
        if (entry == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "entry"));
        }

        if (!LogEntry.isValidEntry(entry)) {
            throw new IllegalValueException(LogEntry.MESSAGE_CONSTRAINTS);
        }

        // Parse the appointment date from the string
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(appointmentDate, DateTimeFormatter.ofPattern("dd MMM yyyy"));
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Invalid date format for appointment date!");
        }

        AppointmentDate modelAppointmentDate = new AppointmentDate(parsedDate);
        LogEntry logEntry = new LogEntry(entry);

        return new Log(modelAppointmentDate, logEntry);
    }
}
