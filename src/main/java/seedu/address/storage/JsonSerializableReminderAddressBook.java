package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyReminderAddressBook;
import seedu.address.model.ReminderAddressBook;
import seedu.address.model.person.Reminder;

/**
 * An Immutable ReminderAddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "reminderaddressbook")
class JsonSerializableReminderAddressBook {

    public static final String MESSAGE_DUPLICATE_REMINDER = "Reminders list contains duplicate reminder(s).";

    private final List<JsonAdaptedReminder> reminders = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableReminderAddressBook} with the given reminders.
     */
    @JsonCreator
    public JsonSerializableReminderAddressBook(@JsonProperty("reminders") List<JsonAdaptedReminder> reminders) {
        this.reminders.addAll(reminders);
    }

    /**
     * Converts a given {@code ReadOnlyReminderAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableReminderAddressBook}.
     */
    public JsonSerializableReminderAddressBook(ReadOnlyReminderAddressBook source) {
        reminders.addAll(source.getReminderList().stream().map(JsonAdaptedReminder::new).collect(Collectors.toList()));
    }

    /**
     * Converts this reminder address book into the model's {@code ReminderAddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ReminderAddressBook toModelType() throws IllegalValueException {
        ReminderAddressBook reminderAddressBook = new ReminderAddressBook();
        for (JsonAdaptedReminder jsonAdaptedReminder : reminders) {
            Reminder reminder = jsonAdaptedReminder.toModelType();
            if (reminderAddressBook.hasReminder(reminder)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_REMINDER);
            }
            reminderAddressBook.addReminder(reminder);
        }
        return reminderAddressBook;
    }

}
