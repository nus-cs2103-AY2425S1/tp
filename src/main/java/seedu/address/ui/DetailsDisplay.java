package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.student.Student;

/**
 * A UI component that displays details of a specific {@code Student}.
 */
public class DetailsDisplay extends UiPart<Region> {

    private static final String FXML = "DetailsDisplay.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label emergencyContact;
    @FXML
    private Label address;
    @FXML
    private Label note;
    @FXML
    private FlowPane subjects;
    @FXML
    private FlowPane level;
    @FXML
    private Label tasks;
    @FXML
    private FlowPane lessonTimes;

    /**
     * Creates a {@code StudentCard} with the given {@code Student} and index to display.
     */
    public DetailsDisplay(Student student) {
        super(FXML);
        this.student = student;
        name.setText(student.getName().fullName);
        phone.setText("Phone number: " + student.getPhone().value);
        emergencyContact.setText("Emergency Contact: " + student.getEmergencyContact().value);
        address.setText("Address: " + student.getAddress().value);
        note.setText(student.getNote().value);
        if (!student.getLevel().levelName.equals("NONE NONE")) {
            level.getChildren().add(new Label(student.getLevel().toString()));
        }
        student.getSubjects().stream()
                .sorted(Comparator.comparing(subject -> subject.subjectName))
                .forEach(subject -> subjects.getChildren()
                        .add(new Label(subject.subjectName)));
        tasks.setText(student.getTaskList().toDescription());
        student.getLessonTimes().forEach(lessonTime ->
                lessonTimes.getChildren().add(new Label(lessonTime.toString())));
    }
}

