package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * Controller for the confirmation dialog.
 */
public class ConfirmationDialogController {

    @FXML
    private VBox confirmationDialog;

    @FXML
    private Label headerLabel;

    @FXML
    private Label clientNameLabel;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    private boolean confirmed;

    /**
     * Initializes the controller after its root element has been processed.
     * Sets up event filters for keyboard navigation and button action handlers.
     */
    public void initialize() {
        confirmationDialog.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (yesButton.isFocused()) {
                    handleYesAction();
                } else if (noButton.isFocused()) {
                    handleNoAction();
                }
                event.consume();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                handleNoAction();
            }
        });

        yesButton.setOnAction(e -> handleYesAction());
        noButton.setOnAction(e -> handleNoAction());
    }

    /**
     * Sets the text of the header label.
     *
     * @param text The text to set.
     */
    public void setHeaderLabelText(String text) {
        headerLabel.setText(text);
    }

    /**
     * Sets the client name in the clientNameLabel.
     *
     * @param clientName The name of the client.
     */
    public void setClientName(String clientName) {
        clientNameLabel.setText(clientName);
    }

    /**
     * Handles the action when the "Yes" button is clicked.
     */
    private void handleYesAction() {
        confirmed = true;
        confirmationDialog.getScene().getWindow().hide();
    }

    /**
     * Handles the action when the "No" button is clicked.
     */
    private void handleNoAction() {
        confirmed = false;
        confirmationDialog.getScene().getWindow().hide();
    }

    /**
     * Returns whether the action was confirmed by the user.
     *
     * @return true if the user clicked "Yes", false otherwise.
     */
    public boolean isConfirmed() {
        return confirmed;
    }
}
