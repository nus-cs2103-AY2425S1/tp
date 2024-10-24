package seedu.academyassist.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.academyassist.commons.core.LogsCenter;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.model.person.SubjectEnum;

/**
 * Controller for the subject tracking window that displays statistics about the number of students
 * taking each subject in the academy.
 */
public class TrackSubjectWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(TrackSubjectWindow.class);
    private static final String FXML = "TrackSubjectWindow.fxml";

    @FXML
    private VBox subjectStatsBox;

    private final Model model;

    /**
     * Creates a new TrackSubjectWindow.
     *
     * @param root Stage to use as the root of the TrackSubjectWindow.
     * @param model Model containing the subject data to display.
     */
    public TrackSubjectWindow(Stage root, Model model) {
        super(FXML, root);
        this.model = model;
        updateSubjectStats();
    }

    /**
     * Creates a new TrackSubjectWindow with a new Stage.
     *
     * @param model Model containing the subject data to display.
     */
    public TrackSubjectWindow(Model model) {
        this(new Stage(), model);
    }

    /**
     * Updates the subject statistics display with current data from the model.
     * Counts the number of students taking each subject and displays these counts
     * in labeled format.
     */
    private void updateSubjectStats() {
        Map<String, Integer> subjectCounts = new HashMap<>();

        // Initialize counts for all subjects to 0
        for (SubjectEnum subject : SubjectEnum.values()) {
            subjectCounts.put(subject.getSubjectName(), 0);
        }

        // Count students for each subject
        for (Person person : model.getFilteredPersonList()) {
            for (Subject subject : person.getSubjects()) {
                String subjectName = subject.toString();
                subjectCounts.put(subjectName, subjectCounts.get(subjectName) + 1);
            }
        }

        // Clear existing labels
        subjectStatsBox.getChildren().clear();

        // Add new labels for each subject
        for (Map.Entry<String, Integer> entry : subjectCounts.entrySet()) {
            Label statLabel = new Label(String.format("%s: %d students",
                    entry.getKey(), entry.getValue()));
            statLabel.getStyleClass().add("subject-stat-label");
            subjectStatsBox.getChildren().add(statLabel);
        }
    }

    /**
     * Shows the subject statistics window and updates the displayed statistics.
     * The window will be centered on the screen when shown.
     */
    public void show() {
        logger.fine("Showing subject statistics.");
        updateSubjectStats();
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the subject statistics window is currently being shown.
     *
     * @return boolean indicating if the window is showing
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the subject statistics window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the subject statistics window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
