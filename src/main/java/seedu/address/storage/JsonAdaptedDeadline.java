package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.model.task.Deadline;

/**
 * Jackson-friendly version of {@link Deadline}.
 */
public class JsonAdaptedDeadline extends JsonAdaptedTask {

    private final String by;

    /**
     * Constructs a {@code JsonAdaptedDeadline} with the given {@code description} and {@code by}.
     */
    @JsonCreator
    public JsonAdaptedDeadline(@JsonProperty("description") String description,
                               @JsonProperty("isDone") Boolean isDone,
                               @JsonProperty("by") String by) {
        super(description, isDone);
        this.by = by;
    }

    /**
     * Converts a given {@code Deadline} into this class for Jackson use.
     */
    public JsonAdaptedDeadline(Deadline source) {
        super(source.getDescription(), source.getIsDone());
        by = source.getBy().toString();
    }

    /**
     * Converts this Jackson-friendly adapted Deadline object into the model's {@code Deadline} object.
     */
    public Deadline toModelType() {
        return new Deadline(description, by);
    }
}

