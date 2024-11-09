package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Controller for a view person page
 */
public class ViewPersonWindow extends UiPart<Stage> {


    public static final Logger LOGGER = LogsCenter.getLogger(ViewPersonWindow.class);
    public static final String FXML = "ViewPersonWindow.fxml";

    public final ObservableList<Person> person;

    // Fields for the client
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label description;
    @FXML
    private FlowPane clientTypes;

    /**
     * Creates a new ViewPersonWindow.
     *
     * @param person ObservableList of persons to view
     */
    public ViewPersonWindow(ObservableList<Person> person) {
        super(FXML, new Stage());
        this.person = person;
    }

    /**
     * Updates the window to display the first person in the list.
     */
    private void updatePersonDisplay() {
        // Clear existing client types
        clientTypes.getChildren().clear();

        // Update with first person's details
        this.person.stream()
                .findFirst()
                .ifPresent(p -> {
                    name.setText(p.getName().fullName);
                    phone.setText(p.getPhone().value);
                    address.setText(p.getAddress().value);
                    email.setText(p.getEmail().value);
                    p.getClientTypes().forEach(clientType ->
                            clientTypes.getChildren().add(new Label(clientType.clientTypeName)));
                    description.setText(p.getDescription().description);
                });
    }

    /**
     * Shows the ViewPersonWindow.
     */
    public void show() {
        LOGGER.fine("Viewing client details.");
        updatePersonDisplay();
        getRoot().show();
        getRoot().centerOnScreen();

    }

    /**
     * Updates and shows the window with details of the specified person.
     */
    public void showPerson() {
        updatePersonDisplay();
        if (!isViewShowing()) {
            show();
        } else {
            focus();
        }
    }

    public boolean isViewShowing() {
        return getRoot().isShowing();
    }

    public void hide() {
        getRoot().hide();
    }

    public void focus() {
        getRoot().requestFocus();
    }
}
