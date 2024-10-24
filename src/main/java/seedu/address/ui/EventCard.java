package seedu.address.ui;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.types.common.DateTime;
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
        setEventStatusLabel(event.getStartTime());
        initializeUpdateTimeline();
    }

    private void initializeUpdateTimeline() {
        Timeline updateTimeline = DateTimeUtil.createTimeline(() -> {
            setEventStatusLabel(this.event.getStartTime());
        });
        updateTimeline.play();
    }

    private void setEventStatusLabel(DateTime startTime) {
        LocalDateTime eventStart = startTime.toLocalDateTime();
        LocalDateTime now = DateTimeUtil.getCurrentDateTime();
        Duration duration = Duration.between(now, eventStart);

        String statusText;
        String styleClass;

        if (duration.isNegative()) {
            statusText = "Completed";
            styleClass = "event-status-completed";
        } else if (duration.isZero()) {
            statusText = "Ongoing";
            styleClass = "event-status-ongoing";
        } else {
            styleClass = "event-status-incomplete";
            statusText = calculateRemainingTimeText(duration, now, eventStart);
        }

        statusLabel.setText(statusText);
        statusLabel.getStyleClass().add(styleClass);
    }

    private String calculateRemainingTimeText(Duration duration, LocalDateTime now, LocalDateTime eventStart) {
        long minutesLeft = duration.toMinutes();
        long hoursLeft = duration.toHours();
        long daysLeft = duration.toDays();

        if (daysLeft > 0) {
            boolean condition = hoursLeft % 24 > 0 && now.getHour() >= eventStart.getHour();
            long roundedDaysLeft = daysLeft + (condition ? 1 : 0);
            return roundedDaysLeft + (roundedDaysLeft == 1 ? " day left" : " days left");
        } else if (hoursLeft > 0) {
            long effectiveHoursLeft = hoursLeft + ((minutesLeft % 60 >= 30) ? 1 : 0);
            return effectiveHoursLeft + " hour" + (effectiveHoursLeft == 1 ? " left" : "s left");
        } else if (minutesLeft > 0) {
            long roundedMinutesLeft = minutesLeft + ((duration.getSeconds() % 60 >= 0) ? 1 : 0);
            return roundedMinutesLeft + " minute" + (roundedMinutesLeft == 1 ? " left" : "s left");
        } else {
            return "<1 minute left";
        }
    }
}
