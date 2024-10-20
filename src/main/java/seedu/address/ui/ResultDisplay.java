package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";
    Logger resultLogger = Logger.getLogger(ResultDisplay.class.getName());

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().addListener((obs, oldText, newText) -> {
            resultDisplay.setPrefHeight((newText.chars().filter(num -> num == '\n').count() + 1)
                    * resultDisplay.getFont().getSize() * 1.5 + 5);
            resultDisplay.setMinHeight((newText.chars().filter(num -> num == '\n').count() + 1)
                    * resultDisplay.getFont().getSize() * 1.5 + 5);
            resultLogger.log(Level.INFO, "Setting Height of ResultDisplay to "
                    + (newText.chars().filter(num -> num == '\n').count() + 1)
                        * resultDisplay.getFont().getSize() * 1.5 + 5);
        });
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

}
