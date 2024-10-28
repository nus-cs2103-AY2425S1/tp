package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonAdaptedTime {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Time's %s field is missing!";
    public final String startTime;
    public final String endTime;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedTime(@JsonProperty("startTime") String startTime,
                            @JsonProperty("endTime") String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedTime(Time source) {
        startTime = source.getStartTime();
        endTime = source.getEndTime();
    }

    /**
     * Converts this Jackson-friendly adapted time object into the model's {@code Time} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Time toModelType() throws IllegalValueException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (startTime == null || endTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName())
            );
        }

        return new Time(LocalDateTime.parse(startTime, formatter), LocalDateTime.parse(endTime, formatter));
    }

}
