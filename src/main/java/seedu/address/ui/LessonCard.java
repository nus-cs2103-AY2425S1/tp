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
                    // Student Name
                    Label studentNameLabel = new Label(student.getName().fullName);
                    boolean hasAttendance = lesson.getAttendance(student);

                    // Student Attendance - Set Green (hasAttendance) or Red (NoAttendance)
                    if (hasAttendance) {
                        studentNameLabel.getStyleClass().add("lesson-card-has-attendance");
                    } else {
                        studentNameLabel.getStyleClass().add("lesson-card-no-attendance");
                    }

                    // Student Participation
                    Label participationLabel = new Label(String.valueOf(1));
                    participationLabel.getStyleClass().add("participation-score");

                    // Combine All Details into Student Box
                    HBox studentBox = new HBox(0);
                    studentBox.getChildren().addAll(studentNameLabel, participationLabel);
                    students.getChildren().add(studentBox);
                });
    }
}
