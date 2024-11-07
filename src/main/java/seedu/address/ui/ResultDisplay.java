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

    private static final String FXML = "ResultDisplay.fxml"; // your FXML file path

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
     * load and set up the user interface. Additionally, it calls {@link #enableMouseResize()}
     * to enable the mouse resizing functionality, allowing the user to resize the {@code resultDisplay}
     * area interactively using the mouse.
     * </p>
     */
    public ResultDisplay() {
        super(FXML);
        enableMouseResize();
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

    /**
     * Enables mouse resizing of the resultDisplay vertically (height only).
     */
    private void enableMouseResize() {
        resultDisplay.setOnMousePressed((MouseEvent event) -> {
            if (event.getY() > resultDisplay.getHeight() - 50) {
                initialY = event.getSceneY();
                initialHeight = resultDisplay.getHeight();
            }
        });

        resultDisplay.setOnMouseDragged((MouseEvent event) -> {
            if (event.getY() > resultDisplay.getHeight() - 10) {
                double deltaY = event.getSceneY() - initialY;

                double newHeight = initialHeight + deltaY;

                resultDisplay.setPrefHeight(Math.max(newHeight, 100));

                placeHolder.setPrefHeight(Math.max(newHeight, 100));
            }
        });
    }

}
