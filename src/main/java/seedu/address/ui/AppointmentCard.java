package seedu.address.ui;

import static seedu.address.commons.util.DateUtil.getDisplayableDateTime;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;


/**
 * A UI component that displays information of a {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {

    private static final String FXML = "AppointmentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label patientId;
    @FXML
    private Label ward;
    @FXML
    private Label appointmentDescription;
    @FXML
    private Label appointmentDuration;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public AppointmentCard(Person person, int displayedIndex) {
        super(FXML);
        assert person != null;
        this.person = person;
        id.setText("A" + displayedIndex + ". ");
        name.setText(person.getName().value);
        patientId.setText("ID: " + person.getId().value);
        ward.setText("Ward: " + person.getWard().value);
        setAppointmentFields(person);
    }

    private void setAppointmentFields(Person person) {
        if (person.getAppointment() != null) {
            appointmentDescription.setText(person.getAppointmentDescription());
            String duration = getDisplayableDateTime(person.getAppointmentStart())
                    + " ~ "
                    + "\n"
                    + getDisplayableDateTime(person.getAppointmentEnd());
            appointmentDuration.setText(duration);
        } else {
            appointmentDescription.setVisible(false); // Hide the label
            appointmentDuration.setVisible(false);
        }
    }
}
