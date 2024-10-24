package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
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
     * Constructs a {@code ResultDisplay} instance and initializes the UI component
     * defined in {@code ResultDisplay.fxml}. Disables the scroll buttons in the
     * {@code TextArea} after the UI is rendered.
     */
    public ResultDisplay() {
        super(FXML);
        Platform.runLater(this::disableScrollbarButtons);
    }

    private void disableScrollbarButtons() {
        for (Node node : resultDisplay.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar scrollBar) {
                scrollBar.setDisable(true);
            }
        }
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }
}
