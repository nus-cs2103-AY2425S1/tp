package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
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
    private final String buyerPhone;
    private final String sellerPhone;
    private final String type;
    private final String postalCode;

    /**
     * Constructs a {@code JsonAdaptedMeeting} with the given meeting details.
     */
    @JsonCreator
    public JsonAdaptedMeeting(@JsonProperty("meetingTitle") String meetingTitle,
                              @JsonProperty("meetingDate") String meetingDate,
                              @JsonProperty("buyer") String buyerPhone,
                              @JsonProperty("seller") String sellerPhone,
                              @JsonProperty("type") String type,
                              @JsonProperty("postalCode") String postalCode) {
        this.meetingDate = meetingDate;
        this.meetingTitle = meetingTitle;
        this.buyerPhone = buyerPhone;
        this.sellerPhone = sellerPhone;
        this.type = type;
        this.postalCode = postalCode;
    }

    /**
     * Converts a given {@code Meeting} into this class for Jackson use.
     */
    public JsonAdaptedMeeting(Meeting source) {
        meetingTitle = source.getMeetingTitle().value;
        meetingDate = source.getMeetingDate().value;
        buyerPhone = source.getBuyerPhone().toString();
        sellerPhone = source.getSellerPhone().toString();
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
        if (this.buyerPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (this.sellerPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (this.type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (this.postalCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PostalCode.class.getSimpleName()));
        }
        final Phone buyerPhone = new Phone(this.buyerPhone);
        final Phone sellerPhone = new Phone(this.sellerPhone);
        final Type type = new Type(this.type);
        final PostalCode postalCode = new PostalCode(this.postalCode);
        return new Meeting(modelMeetingTitle, modelMeetingDate, buyerPhone, sellerPhone, type, postalCode);
    }

}
