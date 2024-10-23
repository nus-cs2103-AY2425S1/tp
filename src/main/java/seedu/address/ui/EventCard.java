package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;


/**
 * An UI component that displays information of a {@code Person}.
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

    private Map<String, String> roleColors = new HashMap<>();

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label attendees;
    @FXML
    private Label vendors;
    @FXML
    private Label sponsors;
    @FXML
    private Label volunteers;
    @FXML
    private FlowPane roles;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        assert displayedIndex >= 1 : "displayedIndex for event card should be at least 1";
        this.event = event;
        id.setText(displayedIndex + ". ");
        name.setText(event.getName());
        attendees.setText("Attendees: " + event.getAttendees().size());
        vendors.setText("Vendors: " + event.getVendors().size());
        sponsors.setText("Sponsors: " + event.getSponsors().size());
        volunteers.setText("Volunteers: " + event.getVolunteers().size());
    }
}
