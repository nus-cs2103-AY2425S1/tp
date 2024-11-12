package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Assignment;

/**
 * Jackson-friendly version of {@link Assignment}.
 */
public class JsonAdaptedAssignment {

    private final String title;
    private final String dueDate;
    private final HashMap<String, Boolean> indexToStatusMap;

    /**
     * Constructs a {@code JsonAdaptedAssignment} with the given assignment details.
     */
    @JsonCreator
    public JsonAdaptedAssignment(
            @JsonProperty("title") String title,
            @JsonProperty("dueDate") String dueDate,
            @JsonProperty("indexToStatusMap") HashMap<String, Boolean> indexToStatusMap) {
        this.title = title;
        this.dueDate = dueDate;
        this.indexToStatusMap = new HashMap<>(indexToStatusMap);
    }

    /**
     * Converts a given {@code Assignment} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        this.title = source.getTitle();
        this.dueDate = source.getDueDate().toString();
        this.indexToStatusMap = new HashMap<>(source.getStatuses());
    }

    /**
     * Converts this Jackson-friendly adapted assignment object into the model's {@code Assignment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assignment.
     */
    public Assignment toModelType() throws IllegalValueException {
        if (title == null || dueDate == null) {
            throw new IllegalValueException("Title or due date cannot be null.");
        }

        LocalDateTime parsedDueDate;
        try {
            parsedDueDate = LocalDateTime.parse(dueDate);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Invalid due date format.");
        }

        return new Assignment(title, parsedDueDate, indexToStatusMap);
    }

    public String getDueDate() {
        return dueDate;
    }

    public HashMap<String, Boolean> getIndexToStatusMap() {
        return indexToStatusMap;
    }

    public String getTitle() {
        return title;
    }
}
