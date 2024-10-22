package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import seedu.address.model.task.Task;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, // Use the name of the class to differentiate types
        include = JsonTypeInfo.As.PROPERTY, // Include type info as a property in the JSON
        property = "type" // The name of the property in the JSON that will hold the type information
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = JsonAdaptedTodo.class, name = "todo"),
        @JsonSubTypes.Type(value = JsonAdaptedDeadline.class, name = "deadline"),
        @JsonSubTypes.Type(value = JsonAdaptedEvent.class, name = "event")
})
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