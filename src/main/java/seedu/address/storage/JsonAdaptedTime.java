package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Time;

/**
 * Jackson-friendly version of {@link Time}.
 */
public class JsonAdaptedTime {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Time field is missing!";
    public final String startTime;
    public final String endTime;

    /**
     * Constructs a {@code JsonAdaptedTime} with the given time details.
     */
    @JsonCreator
    public JsonAdaptedTime(@JsonProperty("startTime") String startTime,
                            @JsonProperty("endTime") String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given {@code Time} into this class for Jackson use.
     */
    public JsonAdaptedTime(Time source) {
        startTime = source.getStartTime();
        endTime = source.getEndTime();
    }

    /**
     * Converts this Jackson-friendly adapted time object into the model's {@code Time} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted time.
     */
    public Time toModelType() throws IllegalValueException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (startTime == null || endTime == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }

        Time time;
        try {
            time = new Time(LocalDateTime.parse(startTime, formatter.withResolverStyle(ResolverStyle.STRICT)),
                    LocalDateTime.parse(endTime, formatter.withResolverStyle(ResolverStyle.STRICT)));
        } catch (DateTimeParseException e) {
            throw new ParseException(Messages.MESSAGE_INVALID_TIME_FORMAT);
        }

        if (!time.isValidTime()) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }
        return time;
    }

}
