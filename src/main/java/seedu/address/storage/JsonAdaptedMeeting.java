package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.schedule.Meeting;

/**
 * Jackson-friendly version of {@link Meeting}.
 */
public class JsonAdaptedMeeting {
    public static final String UIDREGEX = "\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}"
            + "-\\p{XDigit}{4}-\\p{XDigit}{12}(,\\p{XDigit}{8}-\\p{XDigit}{4}-"
            + "\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12})*";

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    public static final String MESSAGE_CONSTRAINTS = "Error Loading the Contact Indexes, not in 1,2,3... format";
    public static final String DATE_TIME_FORMAT_ERROR = "Date Time Format Stored is not YYYY-MM-DD";
    public static final String UID_FORMAT_ERROR = "UID format stored is not valid";

    private final String contactIndexes;
    private final String meetingName;
    private final String meetingDate;
    private final String meetingTime;

    /**
     * Constructs a {@code JsonAdaptedMeeting} with the given meeting details.
     *
     * @param contactIndexes A string representing the contact indexes.
     * @param meetingName    A string representing the meeting name.
     * @param meetingDate    A string representing the meeting date in YYYY-MM-DD format.
     * @param meetingTime    A string representing the meeting time in HH:mm:ss format.
     */
    @JsonCreator
    public JsonAdaptedMeeting(@JsonProperty("contactIndexes") String contactIndexes,
                              @JsonProperty("meetingName") String meetingName,
                              @JsonProperty("meetingDate") String meetingDate,
                              @JsonProperty("meetingTime") String meetingTime) {
        this.contactIndexes = contactIndexes;
        this.meetingName = meetingName;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
    }

    /**
     * Converts a given {@code Meeting} into this class for Jackson use.
     *
     * @param source The meeting object to convert.
     */
    public JsonAdaptedMeeting(Meeting source) {
        contactIndexes = source.convertContactUidsToString();
        meetingName = source.getMeetingName();
        meetingDate = source.getMeetingDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        meetingTime = source.getMeetingTime().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    /**
     * Converts this Jackson-friendly adapted meeting object into the model's {@code Meeting} object.
     *
     * @return A {@code Meeting} object representing the meeting.
     * @throws IllegalValueException if there were any data constraints violated in the adapted meeting.
     */
    public Meeting toModelType() throws IllegalValueException {
        if (contactIndexes == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "contact indexes"));
        }
        if (!contactIndexes.matches(UIDREGEX)) {
            throw new IllegalValueException(UID_FORMAT_ERROR);
        }
        final List<UUID> modelContactIndexes = Arrays.stream(contactIndexes.split(","))
                .map(String::trim)
                .map(UUID::fromString)
                .collect(Collectors.toList());

        if (meetingName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "meeting name"));
        }
        final String modelMeetingName = meetingName;

        if (meetingDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "meeting date"));
        }
        if (!meetingDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            throw new IllegalValueException(DATE_TIME_FORMAT_ERROR);
        }
        final LocalDate modelMeetingDate = LocalDate.parse(meetingDate);

        if (meetingTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "meeting time"));
        }
        if (!meetingTime.matches("^([01]\\d|2[0-3]):[0-5]\\d(:[0-5]\\d(\\.\\d{1,9})?)?$")) {
            throw new IllegalValueException(DATE_TIME_FORMAT_ERROR);
        }
        final LocalTime modelMeetingTime = LocalTime.parse(meetingTime);

        return new Meeting(modelContactIndexes, modelMeetingName, modelMeetingDate, modelMeetingTime);
    }
}
