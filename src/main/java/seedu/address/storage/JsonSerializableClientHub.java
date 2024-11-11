package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ClientHub;
import seedu.address.model.ReadOnlyClientHub;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * An Immutable ClientHub that is serializable to JSON format.
 */
@JsonRootName(value = "clienthub")
class JsonSerializableClientHub {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_REMINDER = "Reminders list contains duplicate reminder(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedReminder> reminders = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableClientHub} with the given persons and reminders.
     */
    @JsonCreator
    public JsonSerializableClientHub(
            @JsonProperty("persons") List<JsonAdaptedPerson> persons,
            @JsonProperty("reminders") List<JsonAdaptedReminder> reminders) {
        if (persons != null) {
            this.persons.addAll(persons);
        }
        if (reminders != null) {
            this.reminders.addAll(reminders);
        }
    }

    /**
     * Converts a given {@code ReadOnlyClientHub} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableClientHub}.
     */
    public JsonSerializableClientHub(ReadOnlyClientHub source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        reminders.addAll(source.getReminderList().stream().map(JsonAdaptedReminder::new).collect(Collectors.toList()));
    }

    /**
     * Converts this client hub into the model's {@code ClientHub} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ClientHub toModelType() throws IllegalValueException {
        ClientHub clientHub = new ClientHub();

        // Add persons to the ClientHub, checking for duplicates
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (clientHub.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            clientHub.addPerson(person);
        }

        // Add reminders to the ClientHub, checking for duplicates
        for (JsonAdaptedReminder jsonAdaptedReminder : reminders) {
            Reminder reminder = jsonAdaptedReminder.toModelType();
            if (clientHub.hasReminder(reminder)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_REMINDER);
            }
            clientHub.addReminder(reminder);
        }

        return clientHub;
    }
}
