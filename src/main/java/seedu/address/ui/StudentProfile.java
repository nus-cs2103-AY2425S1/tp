package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.student.Student;

/**
 * A UI component that displays detailed information about a {@code Student}.
 */
public class StudentProfile extends UiPart<Region> {

    private static final String FXML = "StudentProfile.fxml";

    private static Student student;

    @FXML
    private Label nameLabel;
    @FXML
    private Label studentIdLabel;
    @FXML
    private Label tutorialIdLabel;
    @FXML
    private FlowPane attendanceFlowPane;

    /**
     * Creates a {@code StudentProfile} to display the details of a student.
     */
    public StudentProfile() {
        super(FXML);
    }

    /**
     * Updates the profile with the details of the given {@code Student}.
     * @param student The student whose details should be displayed.
     */
    public void setStudent(Student student) {
        if (student == null) {
            clearProfile();
            return;
        }
        this.student = student;

        nameLabel.setText(student.getName().fullName);
        studentIdLabel.setText(student.getStudentId().value);
        tutorialIdLabel.setText(student.getTutorialId().toString());

        attendanceFlowPane.getChildren().clear();
        student.getPresentDates().getDates().forEach(date -> {
            Label dateLabel = new Label(date.toString());
            dateLabel.getStyleClass().add("attendance-date-label");
            attendanceFlowPane.getChildren().add(dateLabel);
        });
    }

    /**
     * Clears the profile details.
     */
    private void clearProfile() {
        nameLabel.setText("");
        studentIdLabel.setText("");
        tutorialIdLabel.setText("");
        attendanceFlowPane.getChildren().clear();
    }

    /**
     * Checks if the current student selected equals the specified student.
     * @param otherStudent The other student to be compared.
     * @return A boolean value representing whether the current student selected equals the specified student.
     */
    public static boolean isSameStudent(Student otherStudent) {
        if (otherStudent == null) {
            return false;
        }
        return otherStudent.equals(student);
    }
}
