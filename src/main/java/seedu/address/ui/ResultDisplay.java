package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
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

    public ResultDisplay() {
        super(FXML);
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

    TextArea getResultTextArea() {
        return resultDisplay;
    }

}
