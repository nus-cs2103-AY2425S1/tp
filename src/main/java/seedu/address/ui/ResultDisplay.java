package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A UI component that displays feedback to the user in the status bar.
 * The feedback text style changes based on the command's success or failure.
 */
public class ResultDisplay extends UiPart<Region> {
    public static final String SUCCESS_STYLE_CLASS = "success";
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    /**
     * Constructs a ResultDisplay UI component.
     */
    public ResultDisplay() {
        super(FXML);
    }

    /**
     * Displays success feedback to the user and applies the success style.
     *
     * @param feedbackToUser The feedback message to display to the user.
     */
    public void setSuccessFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        setFeedbackText(feedbackToUser);
        applySuccessStyle();
    }

    /**
     * Displays error feedback to the user and applies the error style.
     *
     * @param feedbackToUser The feedback message to display to the user.
     */
    public void setErrorFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        setFeedbackText(feedbackToUser);
        applyErrorStyle();
    }

    /**
     * Sets the text in the result display area.
     *
     * @param feedback The feedback text to display.
     */
    private void setFeedbackText(String feedback) {
        resultDisplay.setText(feedback);
    }

    /**
     * Applies the success style to the result display area by adding the
     * success style class and removing the error style class if present.
     */
    private void applySuccessStyle() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            styleClass.remove(ERROR_STYLE_CLASS);
        }

        if (styleClass.contains(SUCCESS_STYLE_CLASS)) {
            return;
        }

        styleClass.add(SUCCESS_STYLE_CLASS);
    }

    /**
     * Applies the error style to the result display area by adding the
     * error style class and removing the success style class if present.
     */
    private void applyErrorStyle() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(SUCCESS_STYLE_CLASS)) {
            styleClass.remove(SUCCESS_STYLE_CLASS);
        }

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }
}
