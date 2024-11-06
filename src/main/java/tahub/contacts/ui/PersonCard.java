package tahub.contacts.ui;

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

    /**
     * Creates a {@code PersonCard} with the given {@code Person}, {@code Logic} and index to display.
     *
     * @param logic The logic component to handle operations
     * @param person The person to display
     * @param displayedIndex The display index of the person
     */
    public PersonCard(Logic logic, Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        attendanceWindow = new AttendanceWindow(person, logic);
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        matric.setText("(" + person.getMatricNumber().value + ")");
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        attendance.setText("Attendance");

        // Get all courses the student is enrolled in
        ObservableList<StudentCourseAssociation> scaList = logic.getStudentScas(person)
                .getByMatric(person.getMatricNumber().value);

        // Add course codes as tags
        for (StudentCourseAssociation sca : scaList) {
            Label courseLabel = new Label(sca.getCourse().courseCode.toString());
            courseLabel.getStyleClass().add("course-tag"); // Add this style class for course tags
            tags.getChildren().add(courseLabel);
        }
    }

    @FXML
    private void onClickHandler(ActionEvent event) {
        event.consume();
        if (!attendanceWindow.isShowing()) {
            attendanceWindow.show();
        } else {
            attendanceWindow.focus();
        }
    }
}
