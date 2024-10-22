package seedu.academyassist.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.model.person.SubjectEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import seedu.academyassist.commons.core.LogsCenter;

/**
 * Controller for the subject tracking window
 */
public class TrackSubjectWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(TrackSubjectWindow.class);
    private static final String FXML = "TrackSubjectWindow.fxml";

    @FXML
    private VBox subjectStatsBox;

    private final Model model;

    public TrackSubjectWindow(Stage root, Model model) {
        super(FXML, root);
        this.model = model;
        updateSubjectStats();
    }

    public TrackSubjectWindow(Model model) {
        this(new Stage(), model);
    }

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

    public void show() {
        logger.fine("Showing subject statistics.");
        updateSubjectStats();
        getRoot().show();
        getRoot().centerOnScreen();
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }

    public void hide() {
        getRoot().hide();
    }

    public void focus() {
        getRoot().requestFocus();
    }
}