package seedu.eventfulnus.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.eventfulnus.commons.exceptions.IllegalValueException;
import seedu.eventfulnus.model.AddressBook;
import seedu.eventfulnus.model.ReadOnlyAddressBook;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.person.Person;

@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {
    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate event(s).";
    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedEvent> events = new ArrayList<>();
    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("events") List<JsonAdaptedEvent> events,
                                       @JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        if (events != null) {
            this.events.addAll(events);
        }

        if (persons != null) {
            this.persons.addAll(persons);
        }
    }

    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        events.addAll(source.getEventList().stream()
                .map(JsonAdaptedEvent::new)
                .toList());
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).toList());
    }

    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            if (addressBook.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            addressBook.addEvent(event);
        }
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        return addressBook;
    }
}
