package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.student.Student;

/**
 * Represents a card containing information about a student.
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
    private Label id;
    @FXML
    private Label studentId;
    @FXML
    private Label tutorialId;
    @FXML
    private FlowPane attendanceFlowPane;

    /**
     * Creates a {@code StudentCard} with the given {@code Student} and index to display.
     * @param student The student to display.
     * @param displayedIndex The index number to display on the card.
     * @param studentProfile The profile to update when this card is clicked.
     */
    public StudentCard(Student student, int displayedIndex, StudentProfile studentProfile) {
        super(FXML);
        this.student = student;
        this.studentProfile = studentProfile;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        studentId.setText(student.getStudentId().value);
        tutorialId.setText(student.getTutorialId().toString());

        updateAttendanceLabels();

        // Add click listener to update the profile
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

    /**
     * Updates the profile with the selected student's details.
     */
    private void handleCardClick() {
        studentProfile.setStudent(student);
    }
}
