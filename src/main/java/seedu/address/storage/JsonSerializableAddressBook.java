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
import seedu.address.model.event.Event;
import seedu.address.model.id.counter.list.IdCounterList;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "clubconnect")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate event(s).";
    public static final String MESSAGE_DUPLICATE_PERSON_ID = "Persons list contains duplicate IDs";
    public static final String MESSAGE_DUPLICATE_EVENT_ID = "Events list contains duplicate IDs";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedEvent> events = new ArrayList<>();
    private final JsonAdaptedIdCounterList idCounterList;

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons, events and ID counters.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("events") List<JsonAdaptedEvent> events,
                                       @JsonProperty("idCounterList") JsonAdaptedIdCounterList idCounterList) {
        this.persons.addAll(persons);
        this.events.addAll(events);
        this.idCounterList = idCounterList;
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        events.addAll(source.getEventList().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
        idCounterList = new JsonAdaptedIdCounterList(source.getIdCounterList());
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        List<Integer> personIds = new ArrayList<>();
        List<Integer> eventIds = new ArrayList<>();
        int largestPersonId = 0;
        int largestEventId = 0;
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);

            int personId = person.getId();
            if (personId > largestPersonId) {
                largestPersonId = personId;
            }
            if (personIds.contains(personId)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON_ID);
            }
            personIds.add(personId);
        }
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            if (addressBook.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            addressBook.addEvent(event);

            int eventId = event.getEventId();
            if (eventId > largestEventId) {
                largestEventId = eventId;
            }
            if (eventIds.contains(eventId)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT_ID);
            }
            eventIds.add(eventId);
        }
        IdCounterList idCounterList = this.idCounterList.toModelType();
        if (!idCounterList.isValidPersonIdCounter(largestPersonId)) {
            idCounterList.setPersonIdCounter(largestPersonId);
        }
        if (!idCounterList.isValidEventIdCounter(largestEventId)) {
            idCounterList.setEventIdCounter(largestEventId);
        }
        addressBook.setIdCounterList(idCounterList);

        return addressBook;
    }

}
