package seedu.address.ui;

import java.util.Comparator;

import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.logic.commands.CheckAssignmentCommand;
import seedu.address.model.student.Student;
import seedu.address.model.tut.TutDate;

/**
 * A UI component that displays information of a {@code Student}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";
    private final Student student;
    private final StudentProfile studentProfile;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label studentId;
    @FXML
    private Label tutorialId;
    @FXML
    private FlowPane attendanceFlowPane;
    @FXML
    private VBox attendanceBox;

    /**
     * Creates a {@code StudentCard} with the given {@code Student} and index to display.
     */
    public StudentCard(Student student, int displayedIndex, StudentProfile studentProfile) {
        super(FXML);
        this.student = student;
        this.studentProfile = studentProfile;
        name.setText(displayedIndex + ". " + student.getName().fullName);
        studentId.setText(student.getStudentId().value);
        tutorialId.setText(student.getTutorialId().toString());
        // Initialize attendance labels
        updateAttendanceLabels();
        // Add a listener to automatically update attendance labels when attendance changes
        student.getPresentDates().getDates()
                .addListener((SetChangeListener<TutDate>) change -> updateAttendanceLabels());
        // Add click listener to the card
        cardPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleCardClick());
        if (CheckAssignmentCommand.isCheckingAssignment()) {
            updateCardColorBasedOnAssignment();
        }
        CheckAssignmentCommand.isCheckingAssignmentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                updateCardColorBasedOnAssignment();
            } else {
                cardPane.getStyleClass()
                        .removeAll("student-card-done", "student-card-not-done");
                attendanceFlowPane.getStyleClass()
                        .removeAll("attendanceFlowPane-done", "attendanceFlowPane-not-done");
                attendanceBox.getStyleClass()
                        .removeAll("attendanceFlowPane-done", "attendanceFlowPane-not-done");
            }
        });
    }

    private void updateAttendanceLabels() {
        attendanceFlowPane.getChildren().clear();
        student.getPresentDates().getDates().stream()
                .sorted(Comparator.comparing(TutDate::getDate))
                .forEach(date -> {
                    Label dateLabel = new Label(date.toString());
                    dateLabel.getStyleClass().add("attendance-date-label");
                    attendanceFlowPane.getChildren().add(dateLabel);
                });
    }

    private void updateCardColorBasedOnAssignment() {
        boolean isCompleted = student.hasCompletedAssignment();
        attendanceFlowPane.getStyleClass().removeAll("attendance-data-label");
        if (isCompleted) {
            cardPane.getStyleClass().add("student-card-done");
            attendanceFlowPane.getStyleClass().add("attendanceFlowPane-done");
            attendanceBox.getStyleClass().add("attendanceFlowPane-done");

        } else {
            cardPane.getStyleClass().add("student-card-not-done");
            attendanceFlowPane.getStyleClass().add("attendanceFlowPane-not-done");
            attendanceBox.getStyleClass().add("attendanceFlowPane-not-done");
        }
    }

    /**
     * Handles the click event on the student card and updates the profile view.
     */
    private void handleCardClick() {
        studentProfile.setStudent(student);
    }
}
