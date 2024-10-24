package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Controller for a help page
 */
public class PatientInfoPanel extends UiPart<Region> {

        private static final Logger logger = LogsCenter.getLogger(PatientInfoPanel.class);
        private static final String FXML = "PatientInfoPanel.fxml";

        @FXML
        private Label header;

        @FXML
        private Label basicInfoHeader;

        @FXML
        private Label name;

        @FXML
        private Label phone;

        @FXML
        private Label email;

        @FXML
        private Label nric;

        @FXML
        private Label birthdate;

        @FXML
        private Label sex;

        @FXML
        private Label address;

        @FXML
        private Label healthServiceHeader;

        @FXML
        private FlowPane healthServices;

        @FXML
        private Label appointmentHeader;

        @FXML
        private Label appointments;

        @FXML
        private Label furtherDetailsHeader;

        @FXML
        private Label allergy;

        @FXML
        private Label bloodType;

        @FXML
        private Label healthRisk;

        @FXML
        private Label healthRecord;

        @FXML
        private Label nokName;

        @FXML
        private Label nokPhone;

        @FXML
        private Label note;

        /**
         * Creates a new PatientInfoPanel.
         *
         * @param patient
         */
        public PatientInfoPanel(Person patient) {
                super(FXML);
                header.setText("Patient Information");
                basicInfoHeader.setText("Basic Information");
                healthServiceHeader.setText("Health Services");
                appointmentHeader.setText("Appointments");
                furtherDetailsHeader.setText("Further Details");
                setPatientInfoContent(patient);
        }

        private void setPatientInfoContent(Person patient) {
                name.setText("Name: " + (patient.getName() == null ? "" : patient.getName().toString()));
                nric.setText("NRIC: " + patient.getNric().toString());
                birthdate.setText("Birth Date: " + patient.getBirthdate().toString());
                sex.setText("Sex: " + patient.getSex().toString());
                /*
                 * patient.getHealthServices().stream()
                 * .sorted(Comparator.comparing(healthservice ->
                 * healthservice.healthServiceName))
                 * .forEach(healthservice -> healthServices.getChildren().add(new
                 * Label(healthservice.healthServiceName)));
                 */
                phone.setText("Phone Number: " + (patient.getPhone() == null ? "" : patient.getPhone().toString()));
                email.setText("Email: " + (patient.getEmail() == null ? "" : patient.getEmail().toString()));
                address.setText("Address: " + (patient.getAddress() == null ? "" : patient.getAddress().toString()));
                appointments.setText(
                                "Appointments: " + (patient.getAppts() == null ? "" : patient.getApptsString()));
                allergy.setText("Allergies: " + (patient.getAllergy() == null ? "" : patient.getAllergy().toString()));
                bloodType.setText("Blood Type: "
                                + (patient.getBloodType() == null ? "" : patient.getBloodType().toString()));
                healthRisk
                                .setText("Health Risk: " + (patient.getHealthRisk() == null ? ""
                                                : patient.getHealthRisk().toString()));
                healthRecord.setText(
                                "Health Record: " + (patient.getHealthRecord() == null ? ""
                                                : patient.getHealthRecord().toString()));
                nokName.setText("Next of Kin Name: "
                                + (patient.getNokName() == null ? "" : patient.getNokName().toString()));
                nokPhone.setText(
                                "Next of Kin Phone Number: " + (patient.getNokPhone() == null ? ""
                                                : patient.getNokPhone().toString()));
                note.setText("Additional Notes: " + (patient.getNote() == null ? "" : patient.getNote().toString()));
        }
}
