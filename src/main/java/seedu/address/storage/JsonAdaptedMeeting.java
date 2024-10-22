package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.client.Name;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Type;

/**
 * Jackson-friendly version of {@link Meeting}.
 */
class JsonAdaptedMeeting {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meeting's %s field is missing!";

    private final String meetingTitle;
    private final String meetingDate;
    private final String buyer;
    private final String seller;
    private final String type;
    private final String postalCode;

    /**
     * Constructs a {@code JsonAdaptedMeeting} with the given meeting details.
     */
    @JsonCreator
    public JsonAdaptedMeeting(@JsonProperty("meetingTitle") String meetingTitle,
                              @JsonProperty("meetingDate") String meetingDate,
                              @JsonProperty("buyer") String buyer,
                              @JsonProperty("seller") String seller,
                              @JsonProperty("type") String type,
                              @JsonProperty("postalCode") String postalCode) {
        this.meetingDate = meetingDate;
        this.meetingTitle = meetingTitle;
        this.buyer = buyer;
        this.seller = seller;
        this.type = type;
        this.postalCode = postalCode;
    }

    /**
     * Converts a given {@code Meeting} into this class for Jackson use.
     */
    public JsonAdaptedMeeting(Meeting source) {
        meetingTitle = source.getMeetingTitle().value;
        meetingDate = source.getMeetingDate().value;
        buyer = source.getBuyer().toString();
        seller = source.getSeller().toString();
        type = source.getType().value;
        postalCode = source.getPostalCode().value;
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
        if (this.buyer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (this.seller == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (this.type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (this.postalCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PostalCode.class.getSimpleName()));
        }
        final Name buyerName = new Name(this.buyer);
        final Name sellerName = new Name(this.seller);
        final Type type = new Type(this.type);
        final PostalCode postalCode = new PostalCode(this.postalCode);
        return new Meeting(modelMeetingTitle, modelMeetingDate, buyerName, sellerName, type, postalCode);
    }

}
