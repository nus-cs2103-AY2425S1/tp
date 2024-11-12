package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.patient.Patient;

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
    private Label nric;

    @FXML
    private Label birthdate;

    @FXML
    private Label sex;

    @FXML
    private Label appointmentHeader;

    @FXML
    private Label appointments;

    @FXML
    private Label allergiesHeader;

    @FXML
    private Label allergies;

    @FXML
    private Label furtherDetailsHeader;

    @FXML
    private Label email;

    @FXML
    private Label address;

    @FXML
    private Label nokName;

    @FXML
    private Label nokPhone;

    @FXML
    private Label bloodType;

    @FXML
    private Label healthRisk;

    @FXML
    private Label existingCondition;

    @FXML
    private Label note;

    /**
     * Creates a new PatientInfoPanel.
     *
     * @param patient Patient to display information of.
     */
    public PatientInfoPanel(Patient patient) {
        super(FXML);
        header.setText("Patient Information");
        basicInfoHeader.setText("Basic Information");
        appointmentHeader.setText("Appointments");
        allergiesHeader.setText("Allergies");
        furtherDetailsHeader.setText("Further Details");
        setPatientInfoContent(patient);

        logger.info("PatientInfoPanel initialized successfully for patient: " + patient.getNric());
    }

    /**
     * Sets the content of the PatientInfoPanel.
     * @param patient Patient to display information of.
     */
    private void setPatientInfoContent(Patient patient) {
        name.setText("Name: " + (patient.getName() == null ? "" : patient.getName().toString()));
        nric.setText("NRIC: " + patient.getNric().toString());
        birthdate.setText("Birth Date: " + patient.getBirthdate().toString());
        sex.setText("Sex: " + patient.getSex().toString());
        phone.setText("Phone Number: " + (patient.getPhone() == null ? "" : patient.getPhone().toString()));
        email.setText("Email: " + (patient.getEmail() == null ? "" : patient.getEmail().toString()));
        address.setText("Address: " + (patient.getAddress() == null ? "" : patient.getAddress().toString()));
        appointments.setText(
                patient.getImmutableApptList() == null ? "" : patient.getApptsString());
        allergies.setText(patient.getAllergies() == null ? "" : patient.getAllergiesString());
        bloodType.setText("Blood Type: "
                + (patient.getBloodType() == null ? "" : patient.getBloodType().toString()));
        healthRisk.setText("Health Risk: " + (patient.getHealthRisk() == null
                ? ""
                : patient.getHealthRisk().toString()));
        existingCondition.setText("Health Record: " + (patient.getExistingCondition() == null
                ? ""
                : patient.getExistingCondition().toString()));
        nokName.setText("Next of Kin Name: "
                + (patient.getNokName() == null ? "" : patient.getNokName().toString()));
        nokPhone.setText("Next of Kin Phone Number: " + (patient.getNokPhone() == null
                ? ""
                : patient.getNokPhone().toString()));
        note.setText("Additional Notes: " + (patient.getNote() == null ? "" : patient.getNote().toString()));
    }
}
