package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * A detailed view that displays the full details of a {@code Person}.
 * This view updates dynamically when a new {@code Person} is provided.
 */
public class PersonDetailView extends UiPart<Region> implements DetailView<Person> {

    private static final String FXML = "PersonDetailView.fxml";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM d, yyyy");

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private VBox personDetailView;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label relationship;
    @FXML
    private GridPane eventsAttending;
    private ObservableList<Event> eventsList;
    private Person lastUpdatedPerson;

    /**
     * Constructs a {@code PersonDetailView} to display detailed information
     * about a {@code Person}, including their name, phone, email, relationship, and
     * the events they are attending. The view updates automatically when there are changes
     * in the provided list of {@code Event}s.
     *
     */
    public PersonDetailView(ObservableList<Event> eventsList) {
        super(FXML);
        this.eventsList = eventsList;

        // Add listener to update view whenever the event list changes
        this.eventsList.addListener((Observable observable) -> updateAttendingEvents(lastUpdatedPerson));
    }

    @Override
    public void update(Person person) {
        requireNonNull(person);
        getRoot().setVisible(true);
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        relationship.setText(person.getRelationship().relationship);

        lastUpdatedPerson = person;
        updateAttendingEvents(person);
    }

    /**
     * Updates the events section of the {@code PersonDetailView} to display the events that the provided
     * {@code Person} is attending.
     * This method clears any existing event details in the {@code GridPane} (except for the header and separator),
     * and repopulates it with the upcoming events associated with the specified person.
     * <p>
     * If the person is not attending any events, a "No upcoming events" message will be displayed.
     */
    private void updateAttendingEvents(Person person) {
        // Clear any existing children in the GridPane, except the header and separator, when viewing a different person
        eventsAttending.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null
                && GridPane.getRowIndex(node) > 1);

        int rowIndex = 2; // Start after the header and separator (which are row 0 and 1)

        List<Event> sortedEventsList = new ArrayList<>(eventsList); // Create a modifiable copy
        sortedEventsList.sort(Comparator.comparing(Event::getStartDate)); // Sort the copy by start date

        for (Event event : sortedEventsList) {
            if (event.isPersonAttending(person)) {
                Label eventName = new Label(event.getEventName());
                eventName.getStyleClass().add("grid-content-name");
                Label eventLocation = new Label(event.getLocation().toString());
                eventLocation.getStyleClass().add("grid-content-location");
                Label eventStartDate = new Label(event.getStartDate().format(DATE_FORMATTER));
                eventStartDate.getStyleClass().add("grid-content-date");


                eventsAttending.add(eventName, 0, rowIndex);
                eventsAttending.add(eventLocation, 1, rowIndex);
                eventsAttending.add(eventStartDate, 2, rowIndex);

                rowIndex++;
            }
        }

        if (rowIndex == 2) {
            Label noEventsLabel = new Label("No upcoming events");
            noEventsLabel.getStyleClass().add("grid-content-name");
            eventsAttending.add(noEventsLabel, 0, rowIndex, 3, 1);
            GridPane.setHalignment(noEventsLabel, HPos.CENTER);
        }
    }
}
