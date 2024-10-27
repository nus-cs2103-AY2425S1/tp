package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.filteredappointment.FilteredAppointment;
import seedu.address.model.patient.Appt;
import seedu.address.model.patient.Patient;


/**
 * An UI component that displays information of a {@code filteredappointment}
 */
public class FilteredApptCard extends UiPart<Region> {
    private static final String FXML = "FilteredApptListCard.fxml";

    public final Patient patient;
    public final Appt appt;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label appointmentDateTime;
    @FXML
    private Label name;
    @FXML
    private Label nric;


    /**
     * Creates a {@code FilteredApptCard} with the given {@code filteredappointment} and index to display
     */
    public FilteredApptCard(FilteredAppointment filteredAppointment, int displayedIndex) {
        super(FXML);
        this.appt = filteredAppointment.getAppt();
        this.patient = filteredAppointment.getPatient();
        id.setText(displayedIndex + ". ");
        appointmentDateTime.setText(appt.toString());
        name.setText(patient.getName().fullName);
        nric.setText(patient.getNric().value);
    }

}
