package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;
    private final String date;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name, @JsonProperty("date") String date,
                            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.date = date;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getName().fullName;
        date = source.getDate().toString();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .toList());
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's
     * {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted vendor.
     */
    public Event toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modeldate = new Date(date);

        final List<Tag> eventTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            eventTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(eventTags);

        return new Event(modelName, modeldate, modelTags);
    }
}
