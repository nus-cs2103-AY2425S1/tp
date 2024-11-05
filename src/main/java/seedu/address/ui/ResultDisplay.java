package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    /**
     * Creates a {@code ResultDisplay} with the given {@code String}.
     */
    public ResultDisplay() {
        super(FXML);
        resultDisplay.setWrapText(true);
        // Make text area auto-resize
        resultDisplay.textProperty().addListener((observable, oldValue, newValue) -> {
            resultDisplay.setPrefRowCount(Math.max(1, newValue.split("\n").length));
        });
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }
}
