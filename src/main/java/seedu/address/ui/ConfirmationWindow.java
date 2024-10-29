package seedu.address.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Confirmation Window Ui to obtain users' confirmation.
 */
public class ConfirmationWindow extends UiPart<Stage> {
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final String title;
    private final String contentText;
    private Optional<ButtonType> userResponse = Optional.empty();

    /**
     * Constructs a confirmation window.
     * @param title Title of the confirmation window.
     * @param contentText Content of the confirmation window.
     */
    public ConfirmationWindow(String title, String contentText) {
        super();
        this.title = title;
        this.contentText = contentText;
    }

    /**
     * Shows a confirmation alert dialog with the specified title and content text
     * and stores the user's response.
     */
    public void show() {
        logger.info("Showing confirmation window..");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        alert.getDialogPane()
                .setStyle("-fx-background-color: #e0f7fa; -fx-font-size: 14px;");
        alert.getDialogPane().lookupButton(ButtonType.OK)
                .setStyle("-fx-background-color: #005b96; -fx-text-fill: #ffffff;");
        alert.getDialogPane().lookupButton(ButtonType.CANCEL)
                .setStyle("-fx-background-color: #99DDF8; -fx-text-fill: #000000;");

        userResponse = alert.showAndWait();
    }

    /**
     * Returns true if the user clicked OK; false otherwise.
     */
    public boolean isConfirmed() {
        return userResponse.isPresent() && userResponse.get() == ButtonType.OK;
    }
}
