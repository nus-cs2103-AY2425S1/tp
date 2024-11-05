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
        resultDisplay.textProperty().addListener((observable, oldValue, newValue) -> adjustHeight());
    }

    /**
     * Sets the feedback to be displayed to the user.
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
        adjustHeight();
    }

    /**
     * Adjusts the height of the result display based on the number of lines of text.
     * Ensures that all text is visible without having to scroll.
     */
    private void adjustHeight() {
        resultDisplay.setPrefHeight(resultDisplay.getText().split("\n").length * 24 + 20);
    }
}
