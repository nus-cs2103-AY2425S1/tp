package tahub.contacts.ui;

import java.util.Comparator;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import tahub.contacts.logic.Logic;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;

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

    private AttendanceWindow attendanceWindow;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    @FXML
    private Label matric;

    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    @FXML
    private Button attendance;

    @FXML
    private void onClickHandler(ActionEvent event) {
        event.consume ();
        if (!attendanceWindow.isShowing ()) {
            attendanceWindow.show ();
        } else {
            attendanceWindow.focus ();
        }
    }

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person , int displayedIndex , Logic logic) {
        super (FXML);
        this.person = person;
        attendanceWindow = new AttendanceWindow (logic , person);
        id.setText (displayedIndex + ". ");
        name.setText (person.getName ().fullName);
        phone.setText (person.getPhone ().value);
        matric.setText ("(" + person.getMatricNumber ().value + ")");
        address.setText (person.getAddress ().value);
        email.setText (person.getEmail ().value);
        attendance.setText ("Attendance");

        // Get all courses the student is enrolled in
        ObservableList<StudentCourseAssociation> scaList = logic.getStudentScas (person)
                .getByMatric(person.getMatricNumber().value);

        // Add course codes as tags
        for (StudentCourseAssociation sca : scaList) {
            Label courseLabel = new Label(sca.getCourse().courseCode.toString());
            courseLabel.getStyleClass().add("course-tag"); // Add this style class for course tags
            tags.getChildren().add(courseLabel);
        }
    }
}
