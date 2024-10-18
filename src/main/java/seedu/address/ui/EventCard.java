package seedu.address.ui;

import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;

/**
 * A UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label loc;
    @FXML
    private Label time;
    @FXML
    private Label description;
    @FXML
    private Label volunteers;

    /**
     * Creates a {@code EventCard} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;

        id.setText(displayedIndex + ". ");
        name.setText(event.getName().toString());
        date.setText(event.getDate().toString());
        time.setText(event.getStartTime() + " - " + event.getEndTime());
        loc.setText(event.getLocation().toString());
        description.setText(event.getDescription().toString());

        volunteers.textProperty().bind(
                Bindings.createStringBinding(() -> "Volunteers: " + getVolunteersAsString(event.getVolunteers()),
                        event.getVolunteers() // ObservableList to monitor changes
                )
        );
    }

    /**
     * Converts the list of volunteers to a single string.
     *
     * @param volunteers The list of volunteers.
     * @return A string representation of the volunteers.
     */
    public String getVolunteersAsString(ObservableList<String> volunteers) {
        return volunteers.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
