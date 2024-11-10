package seedu.address.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

        // Using text to solve UI constraint on Windows
        Text text = new Text(contentText);
        text.setWrappingWidth(500);

        // Using VBox to adjust position of text as it is not automatically done
        VBox contentBox = new VBox(text);
        contentBox.setPadding(new Insets(25, 25, 10, 10));
        alert.getDialogPane().setContent(contentBox);

        alert.getDialogPane()
                .setStyle("-fx-background-color: derive(#CAE9FF, 50%); -fx-font-size: 16px;");
        alert.getDialogPane().lookupButton(ButtonType.OK)
                .setStyle("-fx-background-color: #1B4965; -fx-text-fill: #ffffff;");
        alert.getDialogPane().lookupButton(ButtonType.CANCEL)
                .setStyle("-fx-background-color: #5FA8D3; -fx-text-fill: #000000;");

        // Center the dialog on the main application window and make it modal.
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.initStyle(StageStyle.UTILITY);

        userResponse = alert.showAndWait();
    }

    /**
     * Returns true if the user clicked OK; false otherwise.
     */
    public boolean isConfirmed() {
        return userResponse.isPresent() && userResponse.get() == ButtonType.OK;
    }
}
