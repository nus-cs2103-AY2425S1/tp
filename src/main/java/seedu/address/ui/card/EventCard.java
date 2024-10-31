package seedu.address.ui.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Event}.
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
    private Label eventName;
    @FXML
    private Label id;
    @FXML
    private Label time;
    @FXML
    private Label venue;
    @FXML
    private FlowPane celebrity;
    @FXML
    private VBox contactsBox;

    /**
     * Creates a {@code EventCard} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        eventName.setText(event.getName().getEventName());
        time.setText(event.getTime().getTime());
        venue.setText(event.getVenue().getVenue());
        celebrity.getChildren().add(new Label(event.getCelebrity().getName().fullName));
        event.getContacts().stream()
                .forEach(contact -> contactsBox.getChildren().add(contactCard(contact)));
    }

    /**
     * Creates a HBox for each contact in the event.
     */
    public HBox contactCard(Person contact) {
        HBox contactCard = new HBox();
        contactCard.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        contactCard.setSpacing(10);
        Label tag = new Label(contact.getTagsString());
        tag.setBackground(new Background(new BackgroundFill(Color.DODGERBLUE, new CornerRadii(5), null)));
        tag.setTextFill(Color.WHITE);
        tag.setPadding(new javafx.geometry.Insets(2, 5, 2, 5));
        tag.setAlignment(javafx.geometry.Pos.CENTER);
        tag.setPrefWidth(100);
        Label name = new Label(contact.getName().fullName);
        name.setPrefWidth(120);
        contactCard.getChildren().addAll(tag, name, new Label(contact.getPhone().value));
        contactCard.setPadding(new javafx.geometry.Insets(5, 0, 0, 0));
        return contactCard;
    }
}
