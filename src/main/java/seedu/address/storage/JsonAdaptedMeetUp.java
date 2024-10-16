package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.MeetUpFrom;
import seedu.address.model.meetup.MeetUpInfo;
import seedu.address.model.meetup.MeetUpName;
import seedu.address.model.meetup.MeetUpTo;

/**
 * Jackson-friendly version of {@link MeetUp}.
 */
class JsonAdaptedMeetUp {
    // Formatter for the stored format (with space)
    private static final DateTimeFormatter STORED_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meet up's %s field is missing!";

    private final String name;
    private final String info;
    private final String from;
    private final String to;

    /**
     * Constructs a {@code JsonAdaptedMeetUp} with the given meet up details.
     */
    @JsonCreator
    public JsonAdaptedMeetUp(@JsonProperty("name") String name, @JsonProperty("info") String info,
            @JsonProperty("from") String from, @JsonProperty("to") String to) {
        this.name = name;
        this.info = info;
        this.from = from;
        this.to = to;
    }

    /**
     * Converts a given {@code MeetUp} into this class for Jackson use.
     */
    public JsonAdaptedMeetUp(MeetUp source) {
        name = source.getName().toString();
        info = source.getInfo().toString();
        from = source.getFrom().toString();
        to = source.getTo().toString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code MeetUp} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meet up.
     */
    public MeetUp toModelType() throws IllegalValueException {
        // This can only be implemented after model is refactored
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MeetUpName.class.getSimpleName()));
        }
        if (!MeetUpName.isValidMeetUpName(name)) {
            throw new IllegalValueException(MeetUpName.MESSAGE_CONSTRAINTS);
        }
        final MeetUpName modelName = new MeetUpName(name);

        if (info == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MeetUpInfo.class.getSimpleName()));
        }
        if (!MeetUpInfo.isValidMeetUpInfo(info)) {
            throw new IllegalValueException(MeetUpInfo.MESSAGE_CONSTRAINTS);
        }
        final MeetUpInfo modelInfo = new MeetUpInfo(info);

        if (from == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MeetUpFrom.class.getSimpleName()));
        }
        if (!MeetUpFrom.isValidMeetUpFromTime(from)) {
            throw new IllegalValueException(MeetUpFrom.MESSAGE_CONSTRAINTS);
        }
        final MeetUpFrom modelFrom = new MeetUpFrom(LocalDateTime.parse(from, STORED_FORMATTER));

        if (to == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MeetUpTo.class.getSimpleName()));
        }
        if (!MeetUpTo.isValidMeetUpToTime(to)) {
            throw new IllegalValueException(MeetUpTo.MESSAGE_CONSTRAINTS);
        }
        final MeetUpTo modelTo = new MeetUpTo(LocalDateTime.parse(to, STORED_FORMATTER));

        return new MeetUp(modelName, modelInfo, modelFrom, modelTo);
    }

}
