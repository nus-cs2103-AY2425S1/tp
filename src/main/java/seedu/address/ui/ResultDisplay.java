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
     * Constructor to construct result display box.
     */
    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().addListener((observable, oldValue, newValue) -> adjustTextAreaHeight());
    }

    /**
     * Sets the result of command as feedback to the user.
     * @param feedbackToUser The feedback to be displayed.
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

    /**
     * Adjusts the height of result display if the text gets too long.
     */
    private void adjustTextAreaHeight() {
        double minHeight = 113.0;
        double maxHeight = 225.0;
        double textHeight = (resultDisplay.getFont().getSize() + 3.75) * (resultDisplay.getParagraphs().size() + 1);
        resultDisplay.setPrefHeight(Math.min(maxHeight, Math.max(minHeight, textHeight)));
    }

}
