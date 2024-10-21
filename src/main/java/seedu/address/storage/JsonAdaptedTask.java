package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.model.task.Task;

public abstract class JsonAdaptedTask {

    protected final String description;
    protected final boolean isDone;

    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("description") String description,
                           @JsonProperty("isDone") boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public abstract Task toModelType();
}