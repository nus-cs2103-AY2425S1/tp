package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.MeetingBook;
import seedu.address.model.ReadOnlyMeetingBook;
import seedu.address.model.meeting.Meeting;

/**
 * An Immutable MeetingBook that is serializable to JSON format.
 */
@JsonRootName(value = "meetingbook")
class JsonSerializableMeetingBook {

    public static final String MESSAGE_DUPLICATE_MEETINGS = "Meetings list contains duplicate meeting(s).";

    private final List<JsonAdaptedMeeting> meetings = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMeetingBook} with the given meetings.
     */
    @JsonCreator
    public JsonSerializableMeetingBook(@JsonProperty("meetings") List<JsonAdaptedMeeting> meetings) {
        this.meetings.addAll(meetings);
    }

    /**
     * Converts a given {@code ReadOnlyMeetingBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMeetingBook}.
     */
    public JsonSerializableMeetingBook(ReadOnlyMeetingBook source) {
        meetings.addAll(source.getMeetingList().stream().map(JsonAdaptedMeeting::new).collect(Collectors.toList()));
    }

    /**
     * Converts this meeting book into the model's {@code MeetingBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public MeetingBook toModelType() throws IllegalValueException {
        MeetingBook meetingBook = new MeetingBook();
        for (JsonAdaptedMeeting jsonAdaptedMeeting : meetings) {
            Meeting meeting = jsonAdaptedMeeting.toModelType();
            if (meetingBook.hasMeeting(meeting)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEETINGS);
            }
            meetingBook.addMeeting(meeting);
        }
        return meetingBook;
    }

}
