package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Date;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.Task;

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
     * Converts this Jackson-friendly adapted deadline object into the model's {@code Deadline} object.
     *
     * @throws IllegalValueException if there are any data constraints violated in the adapted deadline.
     */
    @Override
    public Task toModelType() throws IllegalValueException {
        Description modelDescription = toModelDescription(); // Convert string to Description
        if (by == null || by.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Deadline"));
        }

        try {
            LocalDate.parse(by, Deadline.FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }

        return new Deadline(modelDescription.toString(), by, isDone);
    }
}

