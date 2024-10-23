package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {
    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        logger.info("Creating JsonSerializableAddressBook with "
            + (persons != null ? persons.size() : 0) + " persons");
        if (persons != null) {
            this.persons.addAll(persons);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        logger.info("Converting AddressBook to JSON with "
            + source.getPersonList().size() + " persons");
        persons.addAll(source.getPersonList().stream()
            .map(person -> {
                logger.info("Converting person: " + person.getName());
                return new JsonAdaptedPerson(person);
            })
            .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        logger.info("Converting JSON to AddressBook with "
            + persons.size() + " persons");
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            try {
                Person person = jsonAdaptedPerson.toModelType();
                logger.info("Successfully converted person: "
                    + person.getName());
                if (addressBook.hasPerson(person)) {
                    throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
                }
                addressBook.addPerson(person);
            } catch (IllegalValueException e) {
                logger.info("Error converting person: " + e.getMessage()); // Debug
                throw e;
            }
        }
        return addressBook;
    }

}
