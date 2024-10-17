package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.log.AppointmentDate;
import seedu.address.model.log.Log;

/**
 * Jackson-friendly version of {@link Log}.
 */
class JsonAdaptedLog {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Log's %s field is missing!";

    private final String storageEntry;

    /**
     * Constructs a {@code JsonAdaptedLog} with the given log details.
     */
    @JsonCreator
    public JsonAdaptedLog(String storageEntry) {
        this.storageEntry = storageEntry;
    }


    public JsonAdaptedLog(Log log) {
        this.storageEntry = log.getAppointmentDate() + "|" + log.getEntry(); // Formatting as "date|entry"
    }

    /**
     * Converts this Jackson-friendly adapted log object into the model's Log object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted log.
     */
    public Log toModelType() {
        if (storageEntry == null || !storageEntry.contains("|")) {
            throw new IllegalArgumentException("Invalid log entry format.");
        }
        String[] parts = storageEntry.split("\\|");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Log entry must contain date and details separated by '|'.");
        }
        AppointmentDate appointmentDate = new AppointmentDate(parts[0]); // Parse the date from the first part
        String details = parts[1];

        return new Log(appointmentDate, details); // Assuming details validation is handled in Log
    }
}
