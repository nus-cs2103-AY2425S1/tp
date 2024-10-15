package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {
    private static final String FXML = "EventListCard.fxml";

    private static final int MAX_ATTENDEE_DISPLAY_SIZE = 2;

    private static final Image IMAGE_CALENDAR_DARK =
            new Image("/images/calendar_dark.png");
    private static final Image IMAGE_CALENDAR_LIGHT =
            new Image("/images/calendar_light.png");
    private static final Image IMAGE_PERSON_DARK =
            new Image("/images/person_dark.png");
    private static final Image IMAGE_PERSON_LIGHT =
            new Image("/images/person_light.png");

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
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label date;

    @FXML
    private FlowPane attendees;

    @FXML
    private ImageView calendarIcon;

    @FXML
    private ImageView personIcon;

    /**
     * Creates a {@code EventCard} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        name.setText(event.getEventName());
        date.setText(event.getDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
        List<Person> sortedAttendees = event.getAttendees().stream()
                .sorted(Comparator.comparing(person -> person.getName().toString()))
                .toList();

        int numOfAttendees = sortedAttendees.size();
        for (int i = 0; i < Math.min(MAX_ATTENDEE_DISPLAY_SIZE, numOfAttendees); i++) {
            String name = sortedAttendees.get(i).getName().toString();
            String formattedName = (i == MAX_ATTENDEE_DISPLAY_SIZE - 1)
                                   ? name
                                   : name + ", ";
            attendees.getChildren().add(new Label(formattedName));
        }

        if (numOfAttendees > MAX_ATTENDEE_DISPLAY_SIZE) {
            int remainingPeople = numOfAttendees - MAX_ATTENDEE_DISPLAY_SIZE;
            attendees.getChildren().add(new Label(", and " + remainingPeople + " more"));
        }

    }

    /**
     * Toggles the icons for the {@code calendarIcon} and {@code personIcon} based on selection state of event card.
     *
     * If the event card is selected, then the dark-themed versions of the icons are displayed.
     * Else, the light versions of the icons are displayed.
     *
     * @param selected {@code true} if the card is selected.
     *                 {@code false} if the card is not selected.
     */
    public void toggleIcons(boolean selected) {
        if (selected) {
            calendarIcon.setImage(IMAGE_CALENDAR_DARK);
            personIcon.setImage(IMAGE_PERSON_DARK);
        } else {
            calendarIcon.setImage(IMAGE_CALENDAR_LIGHT);
            personIcon.setImage(IMAGE_PERSON_LIGHT);
        }
    }
}
