package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

public class ClientWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(ClientWindow.class);
    private static final String FXML = "ClientWindow.fxml";

    @FXML
    private Label clientName;

    @FXML
    private Label clientPhone;

    @FXML
    private Label clientEmail;

    @FXML
    private Label clientAddress;

    @FXML
    private Label clientCondition;

    @FXML
    private Label clientSchedule;

    /**
     * Creates a new ClientWindow.
     *
     * @param root Stage to use as the root of the ClientWindow.
     */
    public ClientWindow(Stage root, Person client) {
        super(FXML, root);
        setClient(client);
    }

    /**
     * Creates a new ClientWindow.
     */
    public ClientWindow(Person client) {
        this(new Stage(), client);
    }

    /**
     * Fills in client info into the local variables
     */
    private void setClient(Person client) {
        clientName.setText(client.getName().fullName);
        clientPhone.setText(client.getPhone().value);
        clientEmail.setText(client.getEmail().value);
        clientAddress.setText(client.getAddress().value);
        // might have to change this later on after condition is compulsory
        if (client.getTags().toString().isEmpty()) {
            clientCondition.setText("no condition");
        } else {
            clientCondition.setText(client.getTags().toString());
        }
        clientSchedule.setText(client.getSchedules().toString());
    }

    /**
     * Shows the client window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing information page about the client.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the client window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the client window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the client window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
