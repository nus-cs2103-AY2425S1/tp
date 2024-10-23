package seedu.ddd.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.ddd.model.event.common.Event;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventCard.fxml";

    public final Event event;

    @FXML
    private Label name;

    @FXML
    private Label description;

    @FXML
    private Label id;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;

        name.setText(String.format("%s. %s", displayedIndex, "Dummy Name"));
        id.setText(String.format("Event: #%d", event.getEventId().eventId));

        description.setText(event.getDescription().description);
    }
}
