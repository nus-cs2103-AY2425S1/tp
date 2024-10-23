package seedu.address.ui.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.schedule.Meeting;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Meeting}.
 */
public class CalendarCard extends UiPart<Region> {

    private static final String FXML = "CalendarListCard.fxml";
    private final Meeting meeting;

    @FXML
    private HBox cardPane;
    @FXML
    private Label event;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private FlowPane associatedContacts;

    /**
     * Creates a {@code CalendarCard} with the given {@code meeting} and index to display.
     */
    public CalendarCard(Meeting meeting, int displayedIndex) {
        super(FXML);
        this.meeting = meeting;
        id.setText(displayedIndex + ".");
        event.setText(meeting.getMeetingName());
        date.setText(meeting.getMeetingDate().toString());
        time.setText(meeting.getMeetingTime().toString());
        meeting.getContactUids()
            .forEach(contact -> associatedContacts.getChildren().add(
                new Label(String.valueOf(contact))));
        // Manipulation to contact lambda can be done to get the string out instead of just having the index
    }
}
