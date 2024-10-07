package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.model.person.Person;

/**
 * Controller for view client page
 */
public class ViewClientWindow extends UiPart<Stage> {

    private static final String FXML = "ClientDetailsWindow.fxml";

    @FXML
    private Label clientNameLabel;

    @FXML
    private Label clientIdLabel;

    @FXML
    private Label clientPhoneLabel;

    @FXML
    private Label clientEmailLabel;

    @FXML
    private Label clientAddressLabel;

    @FXML
    private Label clientCarDetailsLabel;

    /**
     * Creates a new ClientDetailsWindow.
     *
     * @param root Stage to use as the root of the ClientDetailsWindow.
     */
    public ViewClientWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new ClientDetailsWindow.
     */
    public ViewClientWindow() {
        this(new Stage());
    }

    /**
     * Shows the client details window.
     * @throws IllegalStateException if this method is called on a thread other than the JavaFX Application Thread.
     */
    public void show(Person client) {
        fillClientDetails(client);
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the client details window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the client details window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the client details window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Fills the client details labels with the given client's details.
     */
    private void fillClientDetails(Person client) {
        clientNameLabel.setText("Name: " + client.getName());
        clientPhoneLabel.setText("Phone: " + client.getPhone());
        clientEmailLabel.setText("Email: " + client.getEmail());
        clientAddressLabel.setText("Address: " + client.getAddress());
        clientCarDetailsLabel.setText("Car Details: " + client.getCar());
    }

    /**
     * Releases resources allocated to the client details window.
     */
    public void handleClose() {
        getRoot().close();
    }
}
