package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    @FXML
    private StackPane placeHolder;

    private double initialY;
    private double initialHeight;

    /**
     * Constructs a {@code ResultDisplay} instance, initializes the UI by loading the FXML file,
     * and enables mouse-based resizing for the result display container.
     * <p>
     * This constructor uses the FXML file associated with the {@link ResultDisplay} class to
     * load and set up the user interface.
     * </p>
     */
    public ResultDisplay() {
        super(FXML);
    }

    public double getPrefHeight() {
        return resultDisplay.getPrefHeight();
    }

    public void setPrefHeight(double height) {
        resultDisplay.setPrefHeight(height);
        placeHolder.setPrefHeight(height);
    }
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

}
