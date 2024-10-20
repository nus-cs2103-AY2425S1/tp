package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";
    private static final double FONT_SCALE = 1.5;
    private static final int ADDITIONAL_HEIGHT = 5; // in px
    private final Logger resultLogger = Logger.getLogger(ResultDisplay.class.getName());

    @FXML
    private TextArea resultDisplay;

    /**
     * Creates a {@code ResultDisplay}.
     */
    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().addListener((obs, oldText, newText) -> {
            resultDisplay.setPrefHeight((newText.chars().filter(num -> num == '\n').count() + 1)
                    * resultDisplay.getFont().getSize() * FONT_SCALE + ADDITIONAL_HEIGHT);
            resultDisplay.setMinHeight((newText.chars().filter(num -> num == '\n').count() + 1)
                    * resultDisplay.getFont().getSize() * FONT_SCALE + ADDITIONAL_HEIGHT);
            resultLogger.log(Level.INFO, "Setting Height of ResultDisplay to "
                    + (newText.chars().filter(num -> num == '\n').count() + 1)
                        * resultDisplay.getFont().getSize() * FONT_SCALE + ADDITIONAL_HEIGHT);
        });
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

}
