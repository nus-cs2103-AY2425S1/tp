package seedu.address.testutil;

import seedu.address.model.client.Phone;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Type;

/**
 * A utility class to help with building Meeting objects.
 */
public class MeetingBuilder {

    public static final String DEFAULT_MEETINGTITLE = "Testing";
    public static final String DEFAULT_MEETINGDATE = "10-10-2024";
    public static final String DEFAULT_BUYER = "94351253";
    public static final String DEFAULT_SELLER = "98765432";
    public static final String DEFAULT_TYPE = "CONDO";
    public static final String DEFAULT_POSTALCODE = "654321";
    private MeetingTitle meetingTitle;
    private MeetingDate meetingDate;
    private Phone buyerPhone;
    private Phone sellerPhone;
    private Type type;
    private PostalCode postalCode;

    /**
     * Creates a {@code MeetingBuilder} with the default details.
     */
    public MeetingBuilder() {
        meetingTitle = new MeetingTitle(DEFAULT_MEETINGTITLE);
        meetingDate = new MeetingDate(DEFAULT_MEETINGDATE);
        buyerPhone = new Phone(DEFAULT_BUYER);
        sellerPhone = new Phone(DEFAULT_SELLER);
        type = new Type(DEFAULT_TYPE);
        postalCode = new PostalCode(DEFAULT_POSTALCODE);
    }

    /**
     * Initializes the MeetingBuilder with the data of {@code meetingToCopy}.
     */
    public MeetingBuilder(Meeting meetingToCopy) {
        meetingTitle = meetingToCopy.getMeetingTitle();
        meetingDate = meetingToCopy.getMeetingDate();
        buyerPhone = meetingToCopy.getBuyerPhone();
        sellerPhone = meetingToCopy.getSellerPhone();
        type = meetingToCopy.getType();
        postalCode = meetingToCopy.getPostalCode();
    }

    /**
     * Sets the {@code MeetingTitle} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withMeetingTitle(String meetingTitle) {
        this.meetingTitle = new MeetingTitle(meetingTitle);
        return this;
    }
    /**
     * Sets the {@code MeetingDate} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withMeetingDate(String meetingDate) {
        this.meetingDate = new MeetingDate(meetingDate);
        return this;
    }

    /**
     * Sets the {@code Buyer} of the {@code Meeting} that we are building.
     *
     * @param buyerPhone the phone number of the buyer
     * @return the updated MeetingBuilder object
     */
    public MeetingBuilder withBuyer(String buyerPhone) {
        this.buyerPhone = new Phone(buyerPhone);
        return this;
    }

    /**
     * Sets the {@code Seller} of the {@code Meeting} that we are building.
     *
     * @param sellerPhone the phone number of the seller
     * @return the updated MeetingBuilder object
     */
    public MeetingBuilder withSeller(String sellerPhone) {
        this.sellerPhone = new Phone(sellerPhone);
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code Meeting} that we are building.
     *
     * @param type the type of the meeting
     * @return the updated MeetingBuilder object
     */
    public MeetingBuilder withType(String type) {
        this.type = new Type(type);
        return this;
    }

    /**
     * Sets the {@code PostalCode} of the {@code Meeting} that we are building.
     *
     * @param postalCode the postal code where the meeting is located
     * @return the updated MeetingBuilder object
     */
    public MeetingBuilder withPostalCode(String postalCode) {
        this.postalCode = new PostalCode(postalCode);
        return this;
    }

    public Meeting build() {
        return new Meeting(meetingTitle, meetingDate, buyerPhone, sellerPhone, type, postalCode);
    }

}

