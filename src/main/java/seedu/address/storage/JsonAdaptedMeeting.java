package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;

/**
 * Jackson-friendly version of {@link Meeting}.
 */
class JsonAdaptedMeeting {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meeting's %s field is missing!";

    private final String meetingTitle;
    private final String meetingDate;

    /**
     * Constructs a {@code JsonAdaptedMeeting} with the given meeting details.
     */
    @JsonCreator
    public JsonAdaptedMeeting(@JsonProperty("meetingTitle") String meetingTitle,
                              @JsonProperty("meetingDate") String meetingDate) {
        this.meetingDate = meetingDate;
        this.meetingTitle = meetingTitle;
    }

    /**
     * Converts a given {@code Meeting} into this class for Jackson use.
     */
    public JsonAdaptedMeeting(Meeting source) {
        meetingTitle = source.getMeetingTitle().value;
        meetingDate = source.getMeetingDate().value;
    }

    /**
     * Converts this Jackson-friendly adapted meeting object into the model's {@code Meeting} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meeting.
     */
    public Meeting toModelType() throws IllegalValueException {
        if (meetingTitle == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MeetingTitle.class.getSimpleName()));
        }
        if (!MeetingTitle.isValidMeetingTitle(meetingTitle)) {
            throw new IllegalValueException(MeetingTitle.MESSAGE_CONSTRAINTS);
        }
        final MeetingTitle modelMeetingTitle = new MeetingTitle(meetingTitle);

        if (meetingDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MeetingDate.class.getSimpleName()));
        }
        if (!MeetingDate.isValidMeetingDate(meetingDate)) {
            throw new IllegalValueException(MeetingDate.MESSAGE_CONSTRAINTS);
        }
        final MeetingDate modelMeetingDate = new MeetingDate(meetingDate);
        return new Meeting(modelMeetingTitle, modelMeetingDate);
    }

}
