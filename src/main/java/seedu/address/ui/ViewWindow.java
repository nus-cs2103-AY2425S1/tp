package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Messages;
import seedu.address.model.person.Person;

/**
 * Represents a controller for a page to view all information about a contact.
 */
public class ViewWindow extends UiPart<Stage> {

    private static final String FXML = "ViewWindow.fxml";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);

    private static final int FIRST_PERSON_INDEX = 0;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label dateOfLastVisit;
    @FXML
    private Label emergencyContact;
    @FXML
    private Label remark;

    public ViewWindow() {
        super(FXML, new Stage());
    }

    public void view(ObservableList<Person> personList) {
        Person personToView = personList.get(FIRST_PERSON_INDEX);
        name.setText(personToView.getName().fullName);
        phone.setText(personToView.getPhone().value);
        if (personToView.hasAddress()) {
            address.setText(personToView.getAddress().get().value);
        } else {
            address.setText("");
            address.setManaged(false);
        }
        if (personToView.hasEmail()) {
            email.setText(personToView.getEmail().get().value);
        } else {
            email.setText("");
            email.setManaged(false);
        }
        if (!tags.getChildren().isEmpty()) {
            tags.getChildren().clear();
        }
        personToView.getTags().stream().sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        dateOfLastVisit.setText(personToView.hasDateOfLastVisit()
                ? "Date last visited: " + personToView.getDateOfLastVisit().get().value
                : "");
        emergencyContact.setText(personToView.hasEmergencyContact()
                ? "Emergency Contact: " + personToView.getEmergencyContact().get().value
                : "");
        remark.setText(personToView.hasRemark()
                ? "Remarks: " + personToView.getRemark().value
                : "");
    }

    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
