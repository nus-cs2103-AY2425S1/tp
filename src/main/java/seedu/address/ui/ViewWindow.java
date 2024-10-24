package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Represents a controller for a page to view all information about a contact.
 */
public class ViewWindow extends UiPart<Stage> {

    private static final String FXML = "ViewWindow.fxml";

    private static final Logger logger = LogsCenter.getLogger(ViewWindow.class);

    private static final int FIRST_PERSON_INDEX = 0;

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

    /**
     * Creates a new ViewWindow.
     * Root of the ViewWindow will always be a new Stage.
     */
    public ViewWindow() {
        super(FXML, new Stage());
    }

    /**
     * Updates the ViewWindow with all the necessary information about a person.
     *
     * @param personList The filtered {@code ObservableList} from {@code Logic}. Will only view 1 {@code Person}.
     */
    public void view(ObservableList<Person> personList) {
        Person personToView = personList.get(FIRST_PERSON_INDEX);

        setNameLabel(personToView);
        setPhoneLabel(personToView);
        setAddressLabel(personToView);
        setEmailLabel(personToView);
        setTagsFlowpane(personToView);
        setDateOfLastVisitLabel(personToView);
        setEmergencyContactLabel(personToView);
        setRemarkLabel(personToView);
    }

    private void setRemarkLabel(Person personToView) {
        if (personToView.getRemark().isPresent()) {
            // the lack of a remark is denoted by an empty string
            remark.setText("Remarks: " + personToView.getRemark().value);
            remark.setManaged(true);
        } else {
            remark.setText("");
            remark.setManaged(false);
        }
    }

    private void setEmergencyContactLabel(Person personToView) {
        if (personToView.hasEmergencyContact()) {
            emergencyContact.setText("Emergency Contact: " + personToView.getEmergencyContact().get().value);
            emergencyContact.setManaged(true);
        } else {
            emergencyContact.setText("");
            emergencyContact.setManaged(false);
        }
    }

    private void setDateOfLastVisitLabel(Person personToView) {
        if (personToView.hasDateOfLastVisit()) {
            dateOfLastVisit.setText("Date last visited: " + personToView.getDateOfLastVisit().get().value);
            dateOfLastVisit.setManaged(true);
        } else {
            dateOfLastVisit.setText("");
            dateOfLastVisit.setManaged(false);
        }
    }

    private void setTagsFlowpane(Person personToView) {
        if (!tags.getChildren().isEmpty()) {
            tags.getChildren().clear();
            // prevents duplication of tags with repeated view commands
        }
        personToView.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void setEmailLabel(Person personToView) {
        if (personToView.hasEmail()) {
            email.setText(personToView.getEmail().get().value);
            email.setManaged(true);
        } else {
            email.setText("");
            email.setManaged(false);
        }
    }

    private void setAddressLabel(Person personToView) {
        if (personToView.hasAddress()) {
            address.setText(personToView.getAddress().get().value);
            address.setManaged(true);
        } else {
            address.setText("");
            address.setManaged(false);
        }
    }

    private void setPhoneLabel(Person personToView) {
        phone.setText(personToView.getPhone().value);
    }

    private void setNameLabel(Person personToView) {
        name.setText(personToView.getName().fullName);
    }

    /**
     * Shows the ViewWindow to the user.
     */
    public void show() {
        logger.fine("Showing view page on a person.");
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

    /**
     * Focuses on the view window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
