package seedu.address.ui;

import static seedu.address.commons.util.DateUtil.getDisplayableDateTime;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;


/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label patientId;
    @FXML
    private Label ward;
    @FXML
    private Label diagnosis;
    @FXML
    private Label medication;
    @FXML
    private Label notes;
    @FXML
    private Label appointmentDescription;
    @FXML
    private Label appointmentStart;
    @FXML
    private Label appointmentEnd;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().value);
        patientId.setText("ID: " + person.getId().value);
        ward.setText("Ward: " + person.getWard().value);
        setAppointmentFields(person);
    }

    private void setAppointmentFields(Person person) {
        if (person.getAppointment() != null) {
            showAppointmentFields(person);
        } else {
            hideAppointmentFields();
        }
    }

    private void showAppointmentFields(Person person) {
        appointmentDescription.setText(person.getAppointmentDescription());
        appointmentStart.setText(getDisplayableDateTime(person.getAppointmentStart()));
        appointmentEnd.setText(getDisplayableDateTime(person.getAppointmentEnd()));
    }

    private void hideAppointmentFields() {
        appointmentDescription.setVisible(false); // Hide the label
        appointmentStart.setVisible(false);
        appointmentEnd.setVisible(false);
    }
}
