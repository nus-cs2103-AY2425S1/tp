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
    private final Logic logic;

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
        this.logic = logic;
        attendanceWindow = new AttendanceWindow(person, logic);
        updateCardContent(displayedIndex);

        // Get all courses the student is enrolled in
        ObservableList<StudentCourseAssociation> scaList = logic.getStudentScas(person)
                .getByMatric(person.getMatricNumber().value);
        // Add course codes as tags
        for (StudentCourseAssociation sca : scaList) {
            Label courseLabel = new Label(sca.getCourse().courseCode.toString());
            courseLabel.getStyleClass().add("course-tag");
            tags.getChildren().add(courseLabel);
        }
    }

    /**
     * Updates all content in the card
     */
    private void updateCardContent(int displayedIndex) {
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        matric.setText("(" + person.getMatricNumber().value + ")");
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        attendance.setText("Attendance");
        updateCourseTags();
    }

    /**
     * Updates the course tags immediately
     */
    private void updateCourseTags() {
        tags.getChildren().clear();

        // Get all courses the student is enrolled in
        ObservableList<StudentCourseAssociation> scaList = logic.getStudentScas(person)
                .getByMatric(person.getMatricNumber().value);

        // Add course codes as tags
        for (StudentCourseAssociation sca : scaList) {
            Label courseLabel = new Label(sca.getCourse().courseCode.toString());
            courseLabel.getStyleClass().add("course-tag");
            tags.getChildren().add(courseLabel);
        }

        // Force immediate layout update
        tags.requestLayout();
    }

    /**
     * Refreshes the card content
     */
    public void refresh(int displayedIndex) {
        updateCardContent(displayedIndex);
    }

    @FXML
    private void onClickHandler(ActionEvent event) {
        event.consume();
        if (attendanceWindow == null) {
            attendanceWindow = new AttendanceWindow(person, logic);
        }

        if (!attendanceWindow.isShowing()) {
            attendanceWindow.show();
        } else {
            attendanceWindow.focus();
        }
    }

    /**
     * Cleanup resources when the card is being removed.
     */
    public void cleanup() {
        if (attendanceWindow != null) {
            attendanceWindow.cleanup();
            attendanceWindow.hide();
            attendanceWindow = null;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonCard)) {
            return false;
        }

        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
