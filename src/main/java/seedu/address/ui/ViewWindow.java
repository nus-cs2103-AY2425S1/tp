package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

public class ViewWindow extends UiPart<Stage>{
    private final Logger logger = LogsCenter.getLogger(getClass());
    private static final String FXML = "ViewWindow.fxml";

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
    private FlowPane tags;

    private ViewWindow(Stage root, String feedbackDisplayText, Person person) {
        super(FXML, root);

        feedback.setText(feedbackDisplayText);
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        remark.setText(person.getRemark().value);
        nric.setText(person.getNric().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    public ViewWindow(String feedbackDisplayText, Person person) {
        this(new Stage(), feedbackDisplayText, person);
    }

    public void show() {
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
}
