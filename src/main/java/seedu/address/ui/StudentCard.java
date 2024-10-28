package seedu.address.ui;

import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.student.Student;
import seedu.address.model.tut.TutDate;


/**
 * A UI component that displays information of a {@code Student}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";

    public final Student student;

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
    private FlowPane attendanceFlowPane; // Use FlowPane instead of a single Label

    /**
     * Creates a {@code StudentCard} with the given {@code Student} and index to display.
     */
    public StudentCard(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        studentId.setText(student.getStudentId().value);
        tutorialId.setText(student.getTutorialId().toString());

        // Populate attendance dates into the FlowPane
        updateAttendanceLabels();

        // Listen for changes in the presentDates property
        student.presentDatesProperty().addListener((observable, oldValue, newValue) -> {
            updateAttendanceLabels();
        });

        // Listen for changes in the dates set within presentDates
        student.getPresentDates().getDates().addListener((SetChangeListener<TutDate>) change -> {
            updateAttendanceLabels();
        });
    }

    /**
     * Updates the FlowPane with the student's attendance dates.
     */
    private void updateAttendanceLabels() {
        attendanceFlowPane.getChildren().clear();
        for (TutDate date : student.getPresentDates().getDates()) {
            Label dateLabel = new Label(date.toString());
            dateLabel.getStyleClass().add("attendance-date-label");
            attendanceFlowPane.getChildren().add(dateLabel);
        }
    }
}
