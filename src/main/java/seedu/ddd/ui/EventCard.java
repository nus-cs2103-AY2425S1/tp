package seedu.ddd.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.ddd.model.event.common.Event;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends DisplayedCard {

    private static final String FXML = "EventCard.fxml";

    public final Event event;

    @FXML
    private Label name;

    @FXML
    private Label description;

    @FXML
    private Label date;

    @FXML
    private FlowPane clients;

    @FXML
    private FlowPane vendors;

    @FXML
    private Label id;

    /**
     * Creates a {@code Event} with the given {@code event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;

        name.setText(String.format("%s. %s", displayedIndex, event.getName().fullName));
        id.setText(String.format("Event: #%d", event.getEventId().id));

        description.setText(event.getDescription().description);
        date.setText(String.format("📅  Date: %s", event.getDate().date));
        event.getClients().stream()
                .forEach(client -> clients.getChildren().add(new Label(client.getName().fullName)));
        event.getVendors().stream()
                .forEach(vendor -> vendors.getChildren().add(new Label(vendor.getName().fullName)));
    }
}
