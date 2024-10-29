package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * A popup that displays today's appointments for persons with appointments.
 */
public class AppointmentPopup extends UiPart<Stage> {

    private static final String FXML = "AppointmentPopup.fxml";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private VBox appointmentListContainer;

    private ObservableList<Person> filteredPersonList;

    /**
     * Constructs an {@code AppointmentPopup} with the specified {@code Stage} and {@code ObservableList}.
     *
     * @param root The root {@code Stage} for this popup.
     * @param filteredPersonList The list of persons that are filtered based on appointment date.
     */
    public AppointmentPopup(Stage root, ObservableList<Person> filteredPersonList) {
        super(FXML, root);
        this.filteredPersonList = filteredPersonList;
    }

    /**
     * Constructs an {@code AppointmentPopup} with a new {@code Stage} and the specified {@code ObservableList}.
     *
     * @param filteredPersonList The list of persons that are filtered based on appointment date.
     */
    public AppointmentPopup(ObservableList<Person> filteredPersonList) {
        this(new Stage(), filteredPersonList);
    }

    /**
     * Checks if there are any appointments scheduled for today among the filtered list of persons.
     * If appointments are found, they are displayed in the popup.
     *
     * @return {@code true} if there are appointments today, otherwise {@code false}.
     */
    public boolean hasAppointmentsToday() {
        LocalDate currentDate = LocalDate.now();
        List<Person> personsWithAppointments = filteredPersonList.stream()
                .filter(person -> person.getAppointment() != null
                        && person.getAppointment().isSameDate(currentDate))
                .sorted(Comparator.comparing(person -> person.getAppointment().getTime()))
                .collect(Collectors.toList());

        if (!personsWithAppointments.isEmpty()) {
            displayAppointments(personsWithAppointments);
            return true;
        }
        return false;
    }

    /**
     * Populates the {@code appointmentListContainer} with labels showing each person's name
     * and the time of their appointment.
     *
     * @param personsWithAppointments The list of persons with appointments scheduled for today.
     */
    private void displayAppointments(List<Person> personsWithAppointments) {
        appointmentListContainer.getChildren().clear();

        for (int i = 0; i < personsWithAppointments.size(); i++) {
            Person person = personsWithAppointments.get(i);

            Label nameLabel = new Label((i + 1) + ". " + person.getName().fullName);
            nameLabel.getStyleClass().add("name-label");

            Label timeLabel = new Label("Appointment Time: " + person.getAppointment()
                    .getTime().format(TIME_FORMATTER));
            timeLabel.getStyleClass().add("time-label");

            VBox appointmentDetails = new VBox(nameLabel, timeLabel);
            appointmentDetails.getStyleClass().add("appointment-details");

            appointmentListContainer.getChildren().add(appointmentDetails);
        }
    }

    /**
     * Displays the appointment popup on the screen and centers it.
     */
    public void show() {
        logger.fine("Showing patients with appointments today.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Checks if the appointment popup is currently displayed.
     *
     * @return {@code true} if the popup is visible, otherwise {@code false}.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the appointment popup.
     */
    public void hide() {
        getRoot().hide();
    }
}
