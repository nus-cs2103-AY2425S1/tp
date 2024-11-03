package seedu.address.ui.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays the simplified information of a {@code Event}.
 */
public class SimplifiedEventCard extends UiPart<Region> {

    private static final String FXML = "SimplifiedEventListCard.fxml";
    private static final String NULL_VALUE_STYLE_CLASS = "null_value";

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
    private Label eventName;
    @FXML
    private Label id;
    @FXML
    private Label time;
    @FXML
    private Label venue;
    @FXML
    private FlowPane celebrity;

    /**
     * Creates a {@code EventCard} with the given {@code Event} and index to display.
     */
    public SimplifiedEventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        eventName.setText(event.getName().getEventName());
        time.setText(event.getTime().getTime());
        if (event.getVenue().isPresent()) {
            venue.setText(event.getVenue().get().getVenue());
            venue.getStyleClass().remove(NULL_VALUE_STYLE_CLASS);
        } else {
            venue.setText("(Edit to add venue)");
            venue.getStyleClass().add(NULL_VALUE_STYLE_CLASS);
        }
        celebrity.getChildren().add(new Label(event.getCelebrity().getName().fullName));
    }
}
