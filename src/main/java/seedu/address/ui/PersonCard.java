package seedu.address.ui;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    private final StringProperty attendanceDisplay;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label attendance;
    @FXML
    private Label phone;
    @FXML
    private Label telegram;
    @FXML
    private Label email;
    @FXML
    private FlowPane roles;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        telegram.setText("@" + person.getTelegram().value);
        email.setText(person.getEmail().value);
        attendance.setVisible(person.isMember());
        attendanceDisplay = this.hasAttendedToday(person)
                ? new SimpleStringProperty(" " + String.valueOf((char) 9745))
                : new SimpleStringProperty(" " + String.valueOf((char) 9744));
        attendance.textProperty().bind(attendanceDisplay);
        person.getRoles().stream()
                .sorted(Comparator.comparing(role -> role.roleName))
                .forEach(role -> roles.getChildren().add(new Label(role.roleName)));
    }

    /**
     * Function to check if a person has attended a session today
     * @param person Person of interest
     * @return boolean value to indicate if person has attended a session today
     */
    public boolean hasAttendedToday(Person person) {
        Set<Attendance> attendedDates = person.getAttendance();
        LocalDate today = LocalDate.now();
        return attendedDates.contains(new Attendance(today.toString()));
    }
}
