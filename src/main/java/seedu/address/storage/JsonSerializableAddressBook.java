package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.Volunteer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_VOLUNTEER = "Volunteers list contains duplicate volunteer(s).";
    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate event(s).";

    private final List<JsonAdaptedVolunteer> volunteers = new ArrayList<>();
    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given volunteers.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("volunteers") List<JsonAdaptedVolunteer> volunteers,
                                    @JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.volunteers.addAll(volunteers);
        this.events.addAll(events);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        volunteers.addAll(source.getVolunteerList().stream().map(JsonAdaptedVolunteer::new).collect(Collectors.toList()));
        events.addAll(source.getEventList().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedVolunteer jsonAdaptedVolunteer : volunteers) {
            Volunteer volunteer = jsonAdaptedVolunteer.toModelType();
            if (addressBook.hasVolunteer(volunteer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_VOLUNTEER);
            }
            addressBook.addVolunteer(volunteer);
        }

        System.out.println("THERE ARE " + events.size() + " EVENTS");

        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            if (addressBook.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            addressBook.addEvent(event);
        }
        return addressBook;
    }

}
