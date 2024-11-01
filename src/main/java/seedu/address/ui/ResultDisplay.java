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

    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().addListener((observable, oldValue, newValue) -> adjustTextAreaHeight());
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

    private void adjustTextAreaHeight() {
        double minHeight = 113.0;
        double maxHeight = 225.0;
        System.out.println(resultDisplay.getFont().getSize());
        System.out.println(resultDisplay.getParagraphs().size());
        double textHeight = (resultDisplay.getFont().getSize() + 3.75) * (resultDisplay.getParagraphs().size() + 1);
        System.out.println(textHeight);
        resultDisplay.setPrefHeight(Math.min(maxHeight, Math.max(minHeight, textHeight)));
    }

}
