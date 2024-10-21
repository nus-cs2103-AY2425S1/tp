package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.OwnedAppointment;

/**
 * UI component that displays information of a {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {

    private static final String FXML = "AppointmentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final OwnedAppointment appt;

    @FXML
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private Label timePeriod;
    @FXML
    private Label appointmentName;
    @FXML
    private Label personName;
    @FXML
    private Label personNric;

    /**
     * Creates a {@code AppointmentCard} with the given {@code Appointment} and index to display.
     */
    public AppointmentCard(OwnedAppointment appt) {
        super(FXML);
        this.appt = appt;
        date.setText(appt.appointment().getAppointmentDate());
        timePeriod.setText(appt.appointment().getAppointmentTimePeriod());
        appointmentName.setText(appt.appointment().getAppointmentName());
        personName.setText(appt.owner().getName().fullName);
        personNric.setText(appt.owner().getNric().value);
    }
}
