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
import seedu.address.model.doctor.Doctor;

/**
 * A UI component that displays information of a {@code Doctor}.
 */
public class DoctorCard extends UiPart<Region> {

    private static final String FXML = "DoctorListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Doctor doctor;

    @FXML
    private HBox cardPane;
    @FXML
    private Label doctorFlag;
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
    private Label specialty;
    @FXML
    private FlowPane tags;
    @FXML
    private Label appointmentsLabel;
    @FXML
    private ListView<String> appointments;

    /**
     * Creates a {@code DoctorCode} with the given {@code Doctor} and index to display.
     */
    public DoctorCard(Doctor doctor, int displayedIndex) {
        super(FXML);
        this.doctor = doctor;
        id.setText(displayedIndex + ". ");
        doctorFlag.setText("[DOCTOR]");
        name.setText(doctor.getName().fullName);
        phone.setText(doctor.getPhone().value);
        address.setText(doctor.getAddress().value);
        email.setText(doctor.getEmail().value);
        specialty.setText(doctor.getSpeciality().value);
        doctor.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        Set<Appointment> appointmentSet = doctor.getAppointments();
        appointmentsLabel.setText("Appointments: " + appointmentSet.size());

        ObservableList<String> appointmentStrings = FXCollections.observableArrayList(appointmentSet.stream()
                .map(appointment -> "Appointment " + appointment.getId() + ": With Patient "
                        + appointment.getPatientName() + " on "
                        + appointment.getDate().toString() + " (" + appointment.getTime().toString() + "hrs)")
                .toArray(String[]::new));

        appointments.setItems(appointmentStrings);

        if (appointmentSet.isEmpty()) {
            appointments.setPrefHeight(0);
        } else {
            appointments.setPrefHeight(50);
        }
    }
}
