package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";
    private static final String ERROR_STYLE = "error";

    @FXML
    private TextArea resultDisplay;

    /**
     * Creates a {@code ResultDisplay}
     */
    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    private void setStyleToDefault() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

    public void setErrorFeedbackToUser(String errorFeedbackToUser) {
        requireNonNull(errorFeedbackToUser);
        resultDisplay.setText(errorFeedbackToUser);

        ObservableList<String> styleClass = resultDisplay.getStyleClass();
        if (styleClass.contains(ERROR_STYLE)) {
            return;
        }
        styleClass.add(ERROR_STYLE);
    }

}
