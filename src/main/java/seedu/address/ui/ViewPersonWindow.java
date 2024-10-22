package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

public class ViewPersonWindow extends UiPart<Stage>{


    public static final Logger logger = LogsCenter.getLogger(ViewPersonWindow.class);
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

    public ViewPersonWindow(ObservableList<Person> person) {
        super(FXML, new Stage());
        this.person = person;

        // Initialising values for the client
        // Using streams to get the first person and set values
        person.stream()
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

    public void show() {
        logger.fine("Viewing client details.");
        getRoot().show();
        getRoot().centerOnScreen();
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
