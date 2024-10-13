package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.volunteer.Volunteer;

import java.util.Comparator;

/**
 * An UI component that displays information of a {@code Volunteer}.
 */
public class VolunteerCard extends UiPart<Region> {

    private static final String FXML = "VolunteerListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

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
    private Label time;

    /**
     * Creates a {@code VolunteerCode} with the given {@code Volunteer} and index to display.
     */
    public VolunteerCard(Volunteer volunteer, int displayedIndex) {
        super(FXML);
        this.volunteer = volunteer;
        id.setText(displayedIndex + ". ");
        name.setText(volunteer.getName().fullName);
        phone.setText(volunteer.getPhone().value);
        email.setText(volunteer.getEmail().value);
        date.setText(volunteer.getAvailableDate().toString());
        time.setText(volunteer.getStartTimeAvailability() + "-" + volunteer.getEndTimeAvailability());
    }
}
