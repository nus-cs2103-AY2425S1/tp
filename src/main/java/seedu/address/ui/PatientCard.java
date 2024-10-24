package seedu.address.ui;

import java.util.Comparator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * A UI component that displays information of a {@code Patient}.
 */
public class PatientCard extends UiPart<Region> {

    private static final String FXML = "PatientListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Patient patient;

    @FXML
    private HBox cardPane;
    @FXML
    private Label patientFlag;
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
    private Label dob;
    @FXML
    private Label gender;
    @FXML
    private FlowPane tags;
    @FXML
    private Label appointmentsLabel;
    @FXML
    private ListView<String> appointments;

    /**
     * Creates a {@code PatientCode} with the given {@code Patient} and index to display.
     */
    public PatientCard(Patient patient, int displayedIndex) {
        super(FXML);
        this.patient = patient;
        id.setText(displayedIndex + ". ");
        patientFlag.setText("[PATIENT]");
        name.setText(patient.getName().fullName);
        phone.setText(patient.getPhone().value);
        address.setText(patient.getAddress().value);
        email.setText(patient.getEmail().value);
        dob.setText(patient.getDateOfBirth().value);
        gender.setText(patient.getGender().value);
        patient.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        Set<Appointment> appointmentSet = patient.getAppointments();
        appointmentsLabel.setText("Appointments: " + appointmentSet.size());

        ObservableList<String> appointmentStrings = FXCollections.observableArrayList(appointmentSet.stream()
                .map(appointment -> "Appointment " + appointment.getId() + ": With Dr. "
                        + appointment.getDoctorName() + " on "
                        + appointment.getDate().toString() + "(" + appointment.getTime().toString() + "hrs)")
                .toArray(String[]::new));

        appointments.setItems(appointmentStrings);

        if (appointmentSet.isEmpty()) {
            appointments.setPrefHeight(0);
        } else {
            appointments.setPrefHeight(50);
        }
    }
}
