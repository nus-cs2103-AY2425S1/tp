package seedu.address.ui;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * An ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveLocationStatus;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public StatusBarFooter(Path saveLocation) {
        super(FXML);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
    }

    /**
     * Handles the action when the chat button is clicked.
     * <p>
     * This method is triggered by an action event, typically from a button click. It
     * loads the chat window FXML layout, creates a new stage for the chat window,
     * and displays it with a specified title and size. If an error occurs while loading
     * the FXML file, the exception is caught and printed to the standard error output.
     * </p>
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    public void handleChatButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChatWindow.fxml"));
            Parent chatRoot = loader.load();
            Stage chatStage = new Stage();
            chatStage.setTitle("Chat with us!");
            chatStage.setScene(new Scene(chatRoot, 450, 400));
            chatStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
