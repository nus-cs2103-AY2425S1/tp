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
    private static final String INITIAL_TEXT =
            "Welome to EZSTATE! Here are some commands to help you get started: \n"
            + "\t Add a buyer: buyer n/[NAME] p/[PHONE] e/[EMAIL]\n"
            + "\t Add a seller: seller n/[NAME] p/[PHONE] e/[EMAIL]\n"
            + "\t For more info: help";


    @FXML
    private TextArea resultDisplay;

    /**
     * Constructs a {@code ResultDisplay} UI component.
     * Sets up the FXML layout and initializes the result display with default text.
     */
    public ResultDisplay() {
        super(FXML);
        setInitialText();
    }

    private void setInitialText() {
        resultDisplay.setText(INITIAL_TEXT);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

}
