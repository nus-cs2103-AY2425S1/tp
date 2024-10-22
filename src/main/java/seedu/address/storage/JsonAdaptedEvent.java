package seedu.address.storage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;

    private final List<JsonAdaptedPerson> attendees;
    private final List<JsonAdaptedPerson> vendors;
    private final List<JsonAdaptedPerson> sponsors;
    private final List<JsonAdaptedPerson> volunteers;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name,
                            @JsonProperty("attendees") List<JsonAdaptedPerson> attendees,
                            @JsonProperty("vendors") List<JsonAdaptedPerson> vendors,
                            @JsonProperty("sponsors") List<JsonAdaptedPerson> sponsors,
                            @JsonProperty("volunteers") List<JsonAdaptedPerson> volunteers) {
        this.name = name;
        this.attendees = attendees != null ? attendees : List.of();
        this.vendors = vendors != null ? vendors : List.of();
        this.sponsors = sponsors != null ? sponsors : List.of();
        this.volunteers = volunteers != null ? volunteers : List.of();
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        this.name = source.getName();

        this.attendees = source.getAttendees().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList());

        this.vendors = source.getVendors().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList());

        this.sponsors = source.getSponsors().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList());

        this.volunteers = source.getVolunteers().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name"));
        }
        final String modelName = name;

        final Set<Person> modelAttendees = new HashSet<>();
        for (JsonAdaptedPerson attendee : attendees) {
            modelAttendees.add(attendee.toModelType());
        }

        final Set<Person> modelVendors = new HashSet<>();
        for (JsonAdaptedPerson vendor : vendors) {
            modelAttendees.add(vendor.toModelType());
        }

        final Set<Person> modelSponsors = new HashSet<>();
        for (JsonAdaptedPerson sponsor : sponsors) {
            modelAttendees.add(sponsor.toModelType());
        }

        final Set<Person> modelVolunteers = new HashSet<>();
        for (JsonAdaptedPerson volunteer : volunteers) {
            modelAttendees.add(volunteer.toModelType());
        }
        return new Event(modelName, modelAttendees,
                        modelVendors, modelSponsors, modelVolunteers);
    }
}
