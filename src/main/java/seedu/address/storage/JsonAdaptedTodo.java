package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Description;
import seedu.address.model.task.Task;
import seedu.address.model.task.Todo;

/**
 * Jackson-friendly version of {@link Todo}.
 */
public class JsonAdaptedTodo extends JsonAdaptedTask {

    /**
     * Constructs a {@code JsonAdaptedTodo} with the given {@code description}.
     */
    @JsonCreator
    public JsonAdaptedTodo(@JsonProperty("description") String description,
                           @JsonProperty("isDone") boolean isDone) {
        super(description, isDone);
    }

    /**
     * Converts a given {@code Todo} into this class for Jackson use.
     */
    public JsonAdaptedTodo(Todo source) {
        super(source.getDescription(), source.getIsDone());
    }



    /**
     * Converts this Jackson-friendly adapted todo object into the model's {@code Todo} object.
     *
     * @throws IllegalValueException if there are any data constraints violated in the adapted todo.
     */
    @Override
    public Task toModelType() throws IllegalValueException {
        Description modelDescription = toModelDescription();
        return new Todo(modelDescription.toString(), isDone);
    }
}

