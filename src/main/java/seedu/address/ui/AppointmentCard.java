package seedu.address.ui;

import static seedu.address.commons.util.DateUtil.FORMATTER;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;

/**
 * A UI component that displays information of a {@code Appointment}.
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

    public final Appointment appointment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label appointmentType;
    @FXML
    private Label id;
    @FXML
    private Label appointmentId;
    @FXML
    private Label personId;
    @FXML
    private Label dateTime;
    @FXML
    private Label personName;
    @FXML
    private Label sickness;
    @FXML
    private Label medicine;


    /**
     * Creates a {@code AppointmentCard} with the given {@code Appointment} and index to display.
     */
    public AppointmentCard(Appointment appointment, int displayedIndex) {
        super(FXML);
        this.appointment = appointment;

        id.setText(displayedIndex + ". ");
        appointmentId.setText(String.valueOf(appointment.getAppointmentId()));
        personId.setText(String.valueOf(appointment.getPersonId()));
        appointmentType.setText(appointment.getAppointmentType().value);
        dateTime.setText(appointment.getAppointmentDateTime().format(FORMATTER));
        personName.setText(appointment.getPerson().getName().fullName);
        sickness.setText(appointment.getSickness() != null ? appointment.getSickness().value : "null");
        medicine.setText(appointment.getMedicine() != null ? appointment.getMedicine().value : "null");
    }
}
