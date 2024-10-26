package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Reminder;

/**
 * Jackson-friendly version of {@link Reminder}.
 */
public class JsonAdaptedReminder {
    private final String person;
    private final String date;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedReminder} with the given {@code Reminder}.
     */
    @JsonCreator
    public JsonAdaptedReminder(Reminder reminder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        person = reminder.getPersonToMeet().fullName;
        date = reminder.getReminderDateAsString();
        description = reminder.getReminderDescription();
    }

    /**
     * Converts this Jackson-friendly adapted reminder object into the model's {@code Reminder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Reminder toModelType() throws IllegalValueException {
        if (!Reminder.isValidDescription(description)) {
            throw new IllegalValueException(Reminder.MESSAGE_CONSTRAINTS_DESCRIPTION);
        }
        if (!Reminder.isValidDate(date)) {
            throw new IllegalValueException(Reminder.MESSAGE_CONSTRAINTS_DATE);
        }
        if (person == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(person)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Reminder(date, description, new Name(person));
    }
}
