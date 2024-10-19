package seedu.address.ui;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.types.common.DateTimeUtil;
import seedu.address.model.types.event.Event;

/**
 * A UI component that displays information of a {@code Event}.
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
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label time;
    @FXML
    private Label address;
    @FXML
    private FlowPane tags;
    @FXML
    private Label statusLabel;

    /**
     * Creates a {@code EventCode} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        name.setText(event.getName().fullName);
        address.setText(event.getLocation().value);
        time.setText(event.getStartTime().value);
        event.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        setEventStatusLabel(event.getStartTime().value);
    }

    private void setEventStatusLabel(String startTime) {
        LocalDateTime eventStart = DateTimeUtil.parse(startTime);
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        Duration duration = Duration.between(now, eventStart);

        String statusText;
        if (duration.isNegative()) {
            statusText = "Completed";
            statusLabel.getStyleClass().add("event-status-completed");
        } else if (duration.isZero()) {
            statusText = "Ongoing";
            statusLabel.getStyleClass().add("event-status-ongoing");
        } else if (duration.toDays() > 0) {
            statusText = duration.toDays() + " days left";
            statusLabel.getStyleClass().add("event-status-incomplete");
        } else if (duration.toHours() > 0) {
            statusText = duration.toHours() + " hours left";
            statusLabel.getStyleClass().add("event-status-incomplete");
        } else {
            statusText = duration.toMinutes() + " minutes left";
            statusLabel.getStyleClass().add("event-status-incomplete");
        }

        statusLabel.setText(statusText);
    }
}