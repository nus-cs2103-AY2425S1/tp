package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";
    private final HostServices hostServices;

    @FXML
    private TextArea resultDisplay;

    @FXML
    private Button resultVisitUG;

    /**
     * Creates a {@code ResultDisplay}.
     */
    public ResultDisplay(HostServices hostServices) {
        super(FXML);
        this.hostServices = hostServices;
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
        resultVisitUG.setVisible(false);
    }

    public void setUserGuideButton(String hyperlink) {
        resultVisitUG.setVisible(true);
        resultVisitUG.setOnAction(e -> hostServices.showDocument(hyperlink));
    }

}
