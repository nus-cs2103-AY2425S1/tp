package seedu.eventfulnus.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.eventfulnus.model.event.Event;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class EventCard extends UiPart<Region> {
    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label value;
    @FXML
    private Label venue;
    @FXML
    private Label dateTime;
    @FXML
    private VBox participants;

    /**
     * Creates a {@code EventCard} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        value.setText(event.getName().toString());
        venue.setText(event.getVenue().toString());
        dateTime.setText(event.getDateTimeDisplayString());
        event.getParticipants().forEach(
                person -> participants.getChildren().add(new Label(person.getName().toString())));
    }


}

