package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.event.Event;

/**
 * A UI component that displays detailed information about an {@code Event}.
 * This panel updates dynamically when a new {@code Event} is provided.
 */
public class EventDetailView extends UiPart<Region> implements DetailView<Event> {
    private static final String FXML = "EventDetailView.fxml";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM d, yyyy");
    private ObservableList<Event> eventsList;

    @FXML
    private Label title;

    @FXML
    private Label date;

    @FXML
    private Label locationLabel;

    @FXML
    private VBox attendees;

    public EventDetailView(ObservableList<Event> eventsList) {
        super(FXML);
        this.eventsList = eventsList;
        if (eventsList.isEmpty()) {
            this.getRoot().setVisible(false);
        }

        this.eventsList.addListener((Observable observable) -> {
            if (eventsList.isEmpty()) {
                this.getRoot().setVisible(false);
            } else {
                this.update(eventsList.get(0));
            }
        });
    }

    /**
     * Sets the details of the given {@code Event} to be displayed in this panel.
     *
     * @param event The event whose details are to be displayed.
     */
    @Override
    public void update(Event event) {
        this.getRoot().setVisible(true);
        title.setText(event.getEventName());
        date.setText(event.getDate().format(DATE_FORMATTER));
        locationLabel.setText(event.getLocation().value);
        attendees.getChildren().clear();
        event.getAttendees().forEach(person -> {
            Label attendeeLabel = new Label(person.getName().toString());
            attendeeLabel.getStyleClass().add("attendee-label");
            attendees.getChildren().add(attendeeLabel);
        });
    }
}

