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
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON =
            "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_CONCERT =
            "Concerts list contains duplicate concert(s).";
    public static final String MESSAGE_DUPLICATE_CONCERT_CONTACT =
            "ConcertContacts list contains duplicate association(s).";
    public static final String MESSAGE_INVALID_CONCERT_CONTACT =
            "Invalid concert contact person and/ or concert does not exist.";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedConcert> concerts = new ArrayList<>();
    private final List<JsonAdaptedConcertContact> concertContacts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
            @JsonProperty("concerts") List<JsonAdaptedConcert> concerts,
            @JsonProperty("concertContacts") List<JsonAdaptedConcertContact> concertContacts) {
        this.persons.addAll(persons);
        this.concerts.addAll(concerts);
        this.concertContacts.addAll(concertContacts);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created
     *        {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(
                Collectors.toList()));
        concerts.addAll(source.getConcertList().stream().map(JsonAdaptedConcert::new).collect(
                Collectors.toList()));
        concertContacts.addAll(source.getConcertContactList().stream().map(
                JsonAdaptedConcertContact::new).collect(Collectors.toList()));
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
        for (JsonAdaptedConcert jsonAdaptedConcert : concerts) {
            Concert concert = jsonAdaptedConcert.toModelType();
            if (addressBook.hasConcert(concert)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CONCERT);
            }
            addressBook.addConcert(concert);
        }
        for (JsonAdaptedConcertContact jsonAdaptedConcertContact : concertContacts) {
            ConcertContact concertContact = jsonAdaptedConcertContact.toModelType();
            if (!addressBook.hasConcert(concertContact.getConcert())
                    || !addressBook.hasPerson(concertContact.getPerson())) {
                throw new IllegalValueException(MESSAGE_INVALID_CONCERT_CONTACT);
            }
            if (addressBook.hasConcertContact(concertContact)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CONCERT_CONTACT);
            }
            addressBook.addConcertContact(concertContact);
        }
        return addressBook;
    }

}
