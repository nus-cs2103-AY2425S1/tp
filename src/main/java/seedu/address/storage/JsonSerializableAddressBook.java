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
import seedu.address.model.types.common.LinkedPersonsEntry;
import seedu.address.model.types.event.Event;
import seedu.address.model.types.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate event(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedEvent> events = new ArrayList<>();
    private final List<JsonAdaptedLinkedPersonsEntry> linkedPersonsEntries = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons, events, and linked persons entries.
     */
    @JsonCreator
    public JsonSerializableAddressBook(
            @JsonProperty("persons") List<JsonAdaptedPerson> persons,
            @JsonProperty("events") List<JsonAdaptedEvent> events,
            @JsonProperty("linkedPersonEntries") List<JsonAdaptedLinkedPersonsEntry> linkedPersonsEntries) {
        if (persons != null) {
            this.persons.addAll(persons);
        }

        if (events != null) {
            this.events.addAll(events);
        }

        if (linkedPersonsEntries != null) {
            this.linkedPersonsEntries.addAll(linkedPersonsEntries);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        events.addAll(source.getEventList().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
        linkedPersonsEntries.addAll(source.getPersonEventManager().getLinkedPersonsEntryList().stream()
                .map(entry -> new JsonAdaptedLinkedPersonsEntry(entry))
                .collect(Collectors.toList()));
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

        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            if (addressBook.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            addressBook.addEvent(event);
        }

        for (JsonAdaptedLinkedPersonsEntry jsonAdaptedLinkedPersonsEntry : linkedPersonsEntries) {
            LinkedPersonsEntry linkedPersonsEntry = jsonAdaptedLinkedPersonsEntry.toModelType();
            addressBook.addLinkedPersonsEntry(linkedPersonsEntry);
        }
        return addressBook;
    }

}
