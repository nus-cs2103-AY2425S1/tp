package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.meeting.Meeting;

/**
 * A UI component that displays information of a {@code Meeting}.
 */
public class MeetingCard extends UiPart<Region> {

    private static final String FXML = "MeetingCard.fxml";

    public final Meeting meeting;

    @FXML
    private HBox meetingCardPane;
    @FXML
    private Label id;
    @FXML
    private Label meetingTitle;

    @FXML
    private Label meetingDate;

    @FXML
    private Label buyer;

    @FXML
    private Label seller;

    @FXML
    private Label type;

    @FXML
    private Label postalCode;

    /**
     * Creates a {@code MeetingCard} with the given {@code Meeting} and index to display.
     */
    public MeetingCard(Meeting meeting, int displayedIndex) {
        super(FXML);
        this.meeting = meeting;
        id.setText(displayedIndex + ". ");
        meetingTitle.setText("Title: " + meeting.getMeetingTitle().value);
        meetingDate.setText("Date: " + meeting.getMeetingDate().value);
        buyer.setText("Buyer's Phone Number: " + meeting.getBuyerPhone().toString());
        seller.setText("Seller's Phone Number: " + meeting.getSellerPhone().toString());
        type.setText("Type: " + meeting.getType().value);
        postalCode.setText("PostalCode: " + meeting.getPostalCode().value);
    }

    public HBox getMeetingCardPane() {
        return this.meetingCardPane;
    }
}
