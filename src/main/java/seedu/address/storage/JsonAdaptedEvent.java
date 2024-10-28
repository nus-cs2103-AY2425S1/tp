package seedu.address.storage;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Date;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.Event;
import seedu.address.model.task.Task;

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
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there are any data constraints violated in the adapted event.
     */
    @Override
    public Task toModelType() throws IllegalValueException {
        Description modelDescription = toModelDescription(); // Convert string to Description
        if (from == null || to == null || from.isEmpty() || to.isEmpty()) {
            throw new IllegalValueException(String.format(JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT, "Event dates"));
        }

        try {
            LocalDate.parse(from, Deadline.FORMATTER);
            LocalDate.parse(to, Deadline.FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Event(modelDescription.toString(), from, to, isDone);
    }
}

