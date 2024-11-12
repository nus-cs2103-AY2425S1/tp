package bizbook.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import bizbook.commons.exceptions.IllegalValueException;
import bizbook.model.AddressBook;
import bizbook.model.ReadOnlyAddressBook;
import bizbook.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    // used to ensure that manually edited data file do not contain unknown person
    public static final String MESSAGE_DUPLICATE_PIN_PERSON = "Pinned persons list contains duplicate person(s).";
    public static final String MESSAGE_UNKNOWN_PERSON = "Pinned persons list contains unknown person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    private final List<JsonAdaptedPerson> pinnedPersons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        pinnedPersons.addAll(source.getPinnedPersonList().stream()
                .map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        for (JsonAdaptedPerson jsonAdaptedPerson : pinnedPersons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (!addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_UNKNOWN_PERSON);
            }
            if (addressBook.isPinned(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PIN_PERSON);
            }
            addressBook.addPinnedPerson(person);
        }
        return addressBook;
    }

}
