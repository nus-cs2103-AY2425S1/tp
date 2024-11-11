package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextFlow resultDisplay;

    @FXML
    private ScrollPane scrollPane = new ScrollPane();

    /**
     * Creates a ResultDisplay, along with a ScrollPane to scroll through the contents.
     */
    public ResultDisplay() {
        super(FXML);
        scrollPane.setContent(resultDisplay);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    /**
     * Sets the text of the result display.
     * Sets the style to white.
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.getChildren().clear();
        resultDisplay.setStyle("-fx-fill: white; -fx-padding: 10px;");
        Text text = new Text(feedbackToUser);
        text.setFill(javafx.scene.paint.Color.WHITE);
        resultDisplay.getChildren().add(text);
    }

    TextFlow getResultTextArea() {
        return resultDisplay;
    }

}
