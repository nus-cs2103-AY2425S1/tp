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
    }

    // /**
    // * Initializes the UI.
    // */
    // @FXML
    // public void initialize() {
    //     resultDisplay.prefWidthProperty().bind(resultDisplay.getParent().widthProperty());
    //     resultDisplay.prefHeightProperty().bind(resultDisplay.getParent().heightProperty());
    // }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

}
