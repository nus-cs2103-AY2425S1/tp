package seedu.address.ui;

import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.volunteer.Volunteer;

/**
 * An UI component that displays information of a {@code Volunteer}.
 */
public class VolunteerCard extends UiPart<Region> {

    private static final String FXML = "VolunteerListCard.fxml";

    public final Volunteer volunteer;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label date;
    @FXML
    private Label involvedIn;

    /**
     * Creates a {@code VolunteerCard} with the given {@code Volunteer} and index to display.
     */
    public VolunteerCard(Volunteer volunteer, int displayedIndex) {
        super(FXML);
        this.volunteer = volunteer;

        // Set the text values from the volunteer data.
        id.setText(displayedIndex + ". ");
        name.setText(volunteer.getName().fullName);
        phone.setText(volunteer.getPhone().value);
        email.setText(volunteer.getEmail().value);
        date.setText(volunteer.getAvailableDate().toString());

        // Bind the "involvedIn" label to update automatically when events change.
        involvedIn.textProperty().bind(
                Bindings.createStringBinding(() -> "Events: " + getEventsAsString(volunteer.getEvents()),
                        volunteer.getEvents() // ObservableList to monitor changes
                )
        );
    }

    /**
     * Converts the list of events to a single string.
     *
     * @param events The list of events.
     * @return A string representation of the events.
     */
    public String getEventsAsString(List<String> events) {
        return events.stream()
                .map(event -> event.toString()) // Ensure it’s properly retrieved as a string
                .collect(Collectors.joining(", "));
    }
}
