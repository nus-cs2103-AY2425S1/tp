package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    private static final Logger logger = LogsCenter.getLogger(JsonSerializableAddressBook.class);

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons and tasks.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("tasks") List<JsonAdaptedTask> tasks) {

        this.persons.addAll(persons);
        this.tasks.addAll(tasks);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            try {
                Person person = jsonAdaptedPerson.toModelType();
                if (addressBook.hasPerson(person)) {
                    logger.warning("Duplicate person found and ignored: " + person);
                    continue;
                }
                addressBook.addPerson(person);
            } catch (IllegalValueException ive) {
                logger.warning("Illegal value found in JSON data for person and ignored: " + jsonAdaptedPerson
                        + ", error: " + ive.getMessage());
            }
        }

        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            try {
                Task task = jsonAdaptedTask.toModelType();
                if (addressBook.hasTask(task)) {
                    logger.warning("Duplicate task found and ignored: " + task);
                } else {
                    addressBook.addTask(task);
                }
            } catch (IllegalValueException ive) {
                // Log and ignore the invalid entry
                logger.warning("Illegal value found in JSON data for task and ignored: " + jsonAdaptedTask
                        + ", error: " + ive.getMessage());
            }
        }

        return addressBook;
    }
}
