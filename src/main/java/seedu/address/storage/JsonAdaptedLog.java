package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

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
    public Log toModelType() throws IllegalValueException {
        String[] logParts = storageEntry.split("\\|", 2);

        if (logParts.length < 2) {
            throw new IllegalValueException("Log format is invalid: " + storageEntry);
        }

        AppointmentDate appointmentDate;
        String details = logParts[1].trim();

        try {
            appointmentDate = new AppointmentDate(logParts[0].trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(AppointmentDate.MESSAGE_CONSTRAINTS);
        }

        // Check if details are empty
        if (details.isEmpty()) {
            throw new IllegalValueException("Log description cannot be empty.");
        }

        // Return a new Log object
        return new Log(appointmentDate, details);
    }
}
