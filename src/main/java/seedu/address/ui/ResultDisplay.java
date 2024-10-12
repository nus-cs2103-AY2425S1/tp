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

    public static final String ERROR_STYLE_CLASS = "error-result";
    public static final String SUCCESS_STYLE_CLASS = "success-result";
    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    /**
     * Creates a {@code ResultDisplay}.
     */
    public ResultDisplay() {
        super(FXML);
        // calls #setStyleToDefault() whenever there is a change to the text of the result display.
        resultDisplay.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Displays the feedback to user and change style to reflect successful result.
     */
    public void setSuccessFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
        setStyleToIndicateResultSuccess();
    }

    /**
     * Displays the feedback to user and change style to reflect failed result.
     */
    public void setErrorFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
        setStyleToIndicateResultFailure();
    }

    /**
     * Sets the result text area style to use the default style.
     */
    private void setStyleToDefault() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
        resultDisplay.getStyleClass().remove(SUCCESS_STYLE_CLASS);
    }

    /**
     * Sets the result text area style to indicate a failed result.
     */
    private void setStyleToIndicateResultFailure() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the result text area style to indicate a successful result.
     */
    private void setStyleToIndicateResultSuccess() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(SUCCESS_STYLE_CLASS)) {
            return;
        }

        styleClass.add(SUCCESS_STYLE_CLASS);
    }

}
