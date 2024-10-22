package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;


/**
 * An UI component that displays information of a {@code Person}.
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
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().value);
        patientId.setText("ID: " + person.getId().value);
        ward.setText("Ward: " + person.getWard().value);
        setAppointmentFields(person);
        /*
        diagnosis.setText("Diagnosis: " + person.getDiagnosis().value);
        medication.setText("Medication: " + person.getMedication().value);
        notes.setText("Notes: " + (person.getNotes().toString().isEmpty() ? "-" : person.getNotes().value));
        */

        /*
        id.setText(displayedIndex + ". ");
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

         */
    }

    private void setAppointmentFields(Person person) {
        // Law of Demeter is not maintained, however the purpose simply retrieves information through getters,
        // and there may be zero risk of modifications.
        if (person.getAppointment() != null) {
            appointmentDescription.setText(person.getAppointment().getDescription());
            appointmentStart.setText(person.getAppointment().getStart().toString());
            appointmentEnd.setText(person.getAppointment().getEnd().toString());
        } else {
            // Use of ChatGPT to see how to hide unwanted label
            // Prompt: How to remove label if appointment is null
            appointmentDescription.setVisible(false); // Hide the label
            appointmentStart.setVisible(false);
            appointmentEnd.setVisible(false);
        }
    }
}
