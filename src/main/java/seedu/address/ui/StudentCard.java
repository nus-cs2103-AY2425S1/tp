package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CheckAssignmentCommand;
import seedu.address.model.student.Student;

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
        updateAttendanceLabels();
        CheckAssignmentCommand.isCheckingAssignmentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                updateCardColorBasedOnAssignment();
            } else {
                cardPane.getStyleClass().removeAll("student-card-done", "student-card-not-done");
            }
        });

        // Add click listener to the card
        cardPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleCardClick());
    }

    private void updateAttendanceLabels() {
        attendanceFlowPane.getChildren().clear();
        student.getPresentDates().getDates().forEach(date -> {
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
            attendanceFlowPane.getStyleClass().add("student-card-done");
        } else {
            cardPane.getStyleClass().add("student-card-not-done");
            attendanceFlowPane.getStyleClass().add("student-card-not-done");
        }
        cardPane.applyCss();
    }

    /**
     * Handles the click event on the student card and updates the profile view.
     */
    private void handleCardClick() {
        studentProfile.setStudent(student);
    }
}
