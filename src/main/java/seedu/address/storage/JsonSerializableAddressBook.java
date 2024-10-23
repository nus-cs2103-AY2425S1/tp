package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_WEDDING = "Weddings list contains duplicate wedding(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedWedding> weddings = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("weddings") List<JsonAdaptedWedding> weddings) {
        this.persons.addAll(persons);
        this.weddings.addAll(weddings);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        weddings.addAll(source.getWeddingList().stream().map(JsonAdaptedWedding::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        //Wedding has to be added first, since person has a reference to wedding
        for (JsonAdaptedWedding jsonAdaptedWedding : weddings) {
            Wedding wedding = jsonAdaptedWedding.toModelType();
            if (addressBook.hasWedding(wedding)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_WEDDING);
            }
            addressBook.addWedding(wedding);
        }

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            //addressBook is passed to person to get the wedding reference
            Person person = jsonAdaptedPerson.toModelType(addressBook.getWeddingList());
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        //Add wedding clients to the wedding
        for (Wedding wedding : addressBook.getWeddingList()) {
            for (Person person : addressBook.getPersonList()) {
                if (person.getOwnWedding() != null && person.getOwnWedding().equals(wedding)) {
                    wedding.addClient(person);
                }
            }
        }

        return addressBook;
    }

}
