package seedu.address.ui;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.patient.Appt;
import seedu.address.model.patient.Patient;

/**
 * An UI component that displays information of a {@code Patient}.
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
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label nric;
    @FXML
    private Label sex;
    @FXML
    private Label appointmentDateTime;

    /**
     * Creates a {@code PatientCard} with the given {@code Patient} and index to display.
     */
    public PatientCard(Patient patient) {
        super(FXML);
        this.patient = patient;
        name.setText(patient.getName().fullName);
        nric.setText(patient.getNric().value);
        sex.setText(patient.getSex().value);
        appointmentDateTime.setText(Optional.ofNullable(patient.getMostRecentFutureAppt())
                .map(Appt::toString)
                .orElse("No upcoming appointment"));
    }
}
