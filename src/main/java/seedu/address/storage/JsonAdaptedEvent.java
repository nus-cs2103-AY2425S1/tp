package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.task.Event;

/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent extends JsonAdaptedTask {
    private final String from;
    private final String to;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given {@code description} and {@code from} and {@code to}.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("description") String description,
                            @JsonProperty("isDone") boolean isDone,
                            @JsonProperty("from") String from,
                            @JsonProperty("to") String to) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        super(source.getDescription(), source.getIsDone());
        from = source.getFrom().toString();
        to = source.getTo().toString();
    }

    /**
     * Converts this Jackson-friendly adapted Event object into the model's {@code Event} object.
     */
    public Event toModelType() {
        return new Event(description, from, to);
    }
}

