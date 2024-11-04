package seedu.address.ui;

import java.time.LocalDateTime;
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

        // Combine date and time for comparison with current date and time
        LocalDateTime lessonDateTime = LocalDateTime.of(
                lesson.getDate().getLocalDateValue(),
                lesson.getTime().getLocalTimeValue());
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Apply the appropriate CSS class based on the lesson time
        if (lessonDateTime.isBefore(currentDateTime)) {
            if (displayedIndex % 2 == 0) {
                cardPane.getStyleClass().add("lesson-card-past-even");
            } else {
                cardPane.getStyleClass().add("lesson-card-past-odd");
            }
            id.getStyleClass().add("lesson-card-strikethrough");
            date.getStyleClass().add("lesson-card-strikethrough");
            time.getStyleClass().add("lesson-card-strikethrough");
        }

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
                    Label participationLabel = new Label(String.valueOf(lesson.getParticipation(student)));
                    participationLabel.getStyleClass().add("participation-score");

                    // Combine All Details into Student Box
                    HBox studentBox = new HBox(0);
                    studentBox.getChildren().addAll(studentNameLabel, participationLabel);
                    students.getChildren().add(studentBox);
                });
    }
}
