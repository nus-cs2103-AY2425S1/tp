package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Log;
import seedu.address.model.person.Person;

/**
 * Represents a view window in the user interface.
 * This window is used to display detailed information about a person.
 */
public class ViewWindow extends UiPart<Stage> {
    private static final String FXML = "ViewWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private StackPane feedbackDisplayPlaceholder;
    @FXML
    private Label feedback;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label remark;
    @FXML
    private Label nric;
    @FXML
    private Label appointment;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox logListContainer;

    private ObservableList<Log> logList;

    private ViewWindow(Stage root, String feedbackDisplayText, Person person) {
        super(FXML, root);

        feedback.setText(feedbackDisplayText);
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        remark.setText(person.getRemark().value);
        nric.setText(person.getNric().value);
        appointment.setText("Appointment on " + person.getAppointment().formatDateTime());
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        logList = FXCollections.observableArrayList(person.getLogEntries().getLogs());
    }

    /**
     * Creates a new ViewWindow with the given feedback display text and person.
     * @param feedbackDisplayText The text to display in the feedback label.
     * @param person The person whose details to display.
     */
    public ViewWindow(String feedbackDisplayText, Person person) {
        this(new Stage(), feedbackDisplayText, person);
    }

    /**
     * Shows the view window.
     */
    public void show() {
        displayLogs();

        logger.fine("Showing view page on a patient.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the view window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the view window.
     */
    public void hide() {
        getRoot().hide();
    }

    private void displayLogs() {
        logListContainer.getChildren().clear();

        for (Log log : logList) {
            Label logLabel = new Label(log.toString());
            logLabel.getStyleClass().add("label-bright");
            logListContainer.getChildren().add(logLabel);
        }
    }
}
