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
public class FindAppointmentCard extends UiPart<Region> {

    private static final String FXML = "FindAppointmentCard.fxml";

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
    private Label nric;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane caregivers;
    @FXML
    private VBox appointments;


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public FindAppointmentCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        nric.setText(person.getNric().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getCaregivers().stream()
                .forEach(caregiver -> caregivers.getChildren().add(new Label(caregiver.toString())));
        person.getFilteredAppointments().stream()
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
