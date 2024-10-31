package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;



/**
 * An UI component that displays information of a {@code Person}.
 */
public class FindPersonCard extends UiPart<Region> {

    private static final String FXML = "FindPersonCard.fxml";

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
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label nric;
    @FXML
    private FlowPane roles;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane caregivers;
    @FXML
    private FlowPane patients;
    @FXML
    private VBox appointments;
    @FXML
    private VBox notes;


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public FindPersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + "");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        nric.setText(person.getNric().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getCaregivers().stream()
                .forEach(caregiver -> caregivers.getChildren().add(new Label(caregiver.toString())));
        person.getPatients().stream()
                .forEach(patient -> patients.getChildren().add(new Label(patient.toString())));
        person.getRoles().stream()
                .sorted(Comparator.comparing(role -> role.name()))
                .forEach(role -> roles.getChildren().add(new Label(role.name())));
        person.getNotes().stream()
                .forEach(note -> {
                    Label noteLabel = new Label("- " + note.getContent());
                    noteLabel.setWrapText(true);
                    noteLabel.setMaxWidth(500);
                    noteLabel.getStyleClass().add("noteLabel");
                    notes.getChildren().add(noteLabel);
                });
        person.getAppointments().stream()
                .sorted(Comparator.comparing(appointment -> appointment.getStartTime()))
                .forEach(appointment -> {
                    Label appointmentLabel = new Label(appointment.getAppointmentDetails());
                    appointmentLabel.getStyleClass().add("appointmentLabel");
                    if (!appointment.isCompleted()) {
                        appointmentLabel.setStyle("-fx-text-fill: red;");
                    } else {
                        appointmentLabel.setStyle("-fx-text-fill: green;");
                    }
                    appointments.getChildren().add(appointmentLabel);
                });

    }
}
