package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private Label resultDisplay;
    @FXML
    private ScrollPane scrollPane;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }


    /**
     * Sets the result display to use the default style.
     */
    public void setStyleToDefault() {
        scrollPane.setStyle("-fx-background-color: #EDF8ED");
        resultDisplay.setStyle("-fx-background-color: #EDF8ED; -fx-line-spacing: 5; -fx-font-size: 13");
    }

    /**
     * Sets the result display style to indicate a failed command.
     */
    public void setStyleToIndicateCommandFailure() {
        scrollPane.setStyle("-fx-background-color: #FCF0F0");
        resultDisplay.setStyle("-fx-background-color: #FCF0F0; -fx-line-spacing: 5; -fx-font-size: 13");
    }
}
