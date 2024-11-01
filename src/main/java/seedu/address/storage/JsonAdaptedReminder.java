package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDescription;

/**
 * Jackson-friendly version of {@link Reminder}.
 */
class JsonAdaptedReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    private final String personName;
    private final String dateTime;
    private final String reminderDescription;

    /**
     * Constructs a {@code JsonAdaptedReminder} with the given reminder details.
     */
    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("personName") String personName, @JsonProperty("dateTime") String dateTime,
                               @JsonProperty("reminderDescription") String reminderDescription) {
        this.personName = personName;
        this.dateTime = dateTime;
        this.reminderDescription = reminderDescription;
    }

    /**
     * Converts a given {@code Reminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder source) {
        personName = source.getPersonName();
        dateTime = source.getDateTime().format(formatter);
        reminderDescription = source.getDescription().description;
    }

    /**
     * Converts this Jackson-friendly adapted reminder object into the model's {@code Reminder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reminder.
     */
    public Reminder toModelType() throws IllegalValueException {
        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "DateTime"));
        }

        LocalDateTime modelDateTime;
        try {
            modelDateTime = LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("DateTime must be in format: " + DATE_TIME_FORMAT);
        }

        if (reminderDescription == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "ReminderDescription"));
        }
        if (!ReminderDescription.isValidReminderDescription(reminderDescription)) {
            throw new IllegalValueException(ReminderDescription.MESSAGE_CONSTRAINTS);
        }

        return new Reminder(personName, modelDateTime, new ReminderDescription(reminderDescription));
    }
}
