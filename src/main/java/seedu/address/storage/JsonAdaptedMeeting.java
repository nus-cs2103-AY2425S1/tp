package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.*;
import seedu.address.model.schedule.Meeting;
import seedu.address.model.tag.Tag;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class JsonAdaptedMeeting {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    
    public static final String MESSAGE_CONSTRAINTS =
            "Error Loading the Contact Indexes, not in 1,2,3... format";
    public static final String DATE_TIME_FORMAT_ERROR =
            "Date Time Format Stored is not YYYY-MM-DD";
    
    private final String contactIndexes;
    private final String meetingName;
    private final String meetingDate;
    private final String meetingTime;
    
    /**
     * Constructs a {@code JsonAdaptedMeeting} with the given person details.
     */
    
    @JsonCreator
    public JsonAdaptedMeeting(@JsonProperty("contactIndexes") String contactIndexes, @JsonProperty("meetingName") String meetingName,
                             @JsonProperty("meetingDate") String meetingDate, @JsonProperty("meetingTime") String meetingTime) {
        this.contactIndexes = contactIndexes;
        this.meetingName = meetingName;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
    }
    
    /**
     * Converts a given {@code Meeting} into this class for Jackson use.
     */
    public JsonAdaptedMeeting(Meeting source) {
        contactIndexes = source.convertContactIndexesToString(source.getContactIndexes());
        meetingName = source.getMeetingName();
        meetingDate = source.getMeetingDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        meetingTime = source.getMeetingTime().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
    
    /**
     * Converts this Jackson-friendly adapted meeting object into the model's {@code Meeting} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */

    public Meeting toModelType() throws IllegalValueException {
        if (contactIndexes == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "contact indexes"));
        }
        if (!contactIndexes.matches("\\d+(,\\d+)*")) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final List<Integer> modelContactIndexes = Arrays.stream(contactIndexes.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        if (meetingName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "meeting name"));
        }
        final String modelMeetingName = meetingName;
        if (meetingDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "meeting date"));
        }
        if (!meetingDate.matches("^\\d{4}-\\d{2}-\\d{2}$\n")) {
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
