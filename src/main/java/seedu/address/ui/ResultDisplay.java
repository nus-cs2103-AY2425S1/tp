package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/userimage.png"));
    private final Image chatBotImage = new Image(this.getClass().getResourceAsStream("/images/storeclass.png"));

    @FXML
    private TextArea resultDisplay;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;

    public ResultDisplay() {
        super(FXML);
    }

    /**
     * Bind vvalue property of scroll pane so that it scrolls to the bottom after each command.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Outputs a feedback message in the chat panel in response to a user command.
     *
     * @param commandText Command sent by user.
     * @param feedbackToUser Response of chatbot to user.
     * @param isError Whether the command resulted in an error.
     */
    public void setFeedbackToUser(String commandText, String feedbackToUser, boolean isError) {
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(commandText, userImage, isError),
                DialogBox.getChatBotDialog(feedbackToUser, chatBotImage)
        );
    }

    /**
     * Outputs a greeting message from chatbot.
     *
     * @param message Message for chatbot to greet with.
     */
    @FXML
    public void greet(String message) {
        dialogContainer.getChildren().addAll(
                DialogBox.getChatBotDialog(message, chatBotImage)
        );
    }
}
