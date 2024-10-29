package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.lesson.Lesson;

/**
 * A UI component that displays information of a {@code Lesson}.
 */
public class LessonCard extends UiPart<Region> {
    private static final String FXML = "LessonListCard.fxml";

    public final Lesson lesson;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private FlowPane students;

    /**
     * Creates a {@code StudentCode} with the given {@code Student} and index to display.
     */
    public LessonCard(Lesson lesson, int displayedIndex) {
        super(FXML);
        this.lesson = lesson;
        id.setText(displayedIndex + ". ");
        date.setText(lesson.getDate().toString() + ", ");
        time.setText(lesson.getTime().toString());
        lesson.getStudents().stream()
                .sorted(Comparator.comparing(student -> student.getName().fullName))
                .forEach(student -> {
                    if (student.getName().fullName == "Sky Lim") {
                        lesson.setAttendance(student, true);
                    }
                    System.out.println(student.toString() + lesson.getAttendance(student));

                    Label studentLabel = new Label(student.getName().fullName);
                    boolean hasAttendance = lesson.getAttendance(student);

                    // Set Student Background Colour based on Attendance
                    if (hasAttendance) {
                        studentLabel.setStyle("-fx-background-color: #275918"); // Green If Attendance
                    } else {
                        studentLabel.setStyle("-fx-background-color: #CF4748"); // Red If No Attendance
                    }

                    students.getChildren().add(studentLabel);
                });
    }
}
