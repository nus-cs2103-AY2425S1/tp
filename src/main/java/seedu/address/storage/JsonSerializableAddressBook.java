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
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.Volunteer;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_VOLUNTEER = "Volunteers list contains duplicate volunteer(s).";
    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate event(s).";
    private static final Logger logger = LogsCenter.getLogger(JsonSerializableAddressBook.class);

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
        volunteers.addAll(source.getVolunteerList().stream().map(JsonAdaptedVolunteer::new)
                .collect(Collectors.toList()));
        events.addAll(source.getEventList().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     * If there are any corrupted data (e.g. missing fields, duplicate entries), they will be logged and ignored.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() {
        AddressBook addressBook = new AddressBook();
        int corruptedVolunteers = 0;
        for (JsonAdaptedVolunteer jsonAdaptedVolunteer : volunteers) {
            try {
                Volunteer volunteer = jsonAdaptedVolunteer.toModelType();
                if (addressBook.hasVolunteer(volunteer)) {
                    throw new IllegalValueException(MESSAGE_DUPLICATE_VOLUNTEER);
                }
                addressBook.addVolunteer(volunteer);
            } catch (IllegalValueException e) {
                corruptedVolunteers++;
            }
        }

        if (corruptedVolunteers > 0) {
            logger.warning(corruptedVolunteers + " corrupted / duplicate volunteer entries found in the data file."
                    + " They will not be imported.");
        }

        int corruptedEvents = 0;
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            try {
                Event event = jsonAdaptedEvent.toModelType();
                if (addressBook.hasEvent(event)) {
                    throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
                }
                addressBook.addEvent(event);
            } catch (IllegalValueException e) {
                corruptedEvents++;
            }
        }

        if (corruptedEvents > 0) {
            logger.warning(corruptedEvents + " corrupted / duplicate event entries found in the data file."
                    + " They will not be imported.");
        }

        return addressBook;
    }

}
