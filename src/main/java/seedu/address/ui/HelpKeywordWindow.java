package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpKeywordWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(HelpKeywordWindow.class);
    private static final String FXML = "HelpKeywordWindow.fxml";

    @FXML
    private VBox helpKeywordContainer;

    @FXML
    private Label header;

    @FXML
    private Label parametersHeader;

    @FXML
    private Label parameters;

    @FXML
    private Label usageHeader;

    @FXML
    private Label usage;

    @FXML
    private Label exampleHeader;

    @FXML
    private Label example;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpKeywordWindow(Stage root, String keyword) {
        super(FXML, root);
        setHelpKeywordContent(keyword);

        getRoot().setWidth(500);
        getRoot().setHeight(400);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpKeywordWindow(String keyword) {
        this(new Stage(), keyword);
    }

    private void setHelpKeywordContent(String keyword) {
        switch(keyword.toLowerCase()) {
        case "add":
            setTextAddCommand();
            break;
        case "appt":
            setTextApptCommand();
            break;
        case "delete":
            setTextDeleteCommand();
            break;
        }
    }

    private void setTextAddCommand() {
        header.setText("Add Command: Adds a new patient record into the database system.");
        parametersHeader.setText("Parameters:");
        parameters.setText("NAME | NRIC | SEX(M/F) | DATE OF BIRTH(YYYY-MM-DD) | HEALTH SERVICE");
        usageHeader.setText("Command Usage:");
        usage.setText("add n/[NAME] i/[NRIC] s/[SEX] d/[DATE OF BIRTH] h/[HEALTH SERVICE]");
        exampleHeader.setText("Example:");
        example.setText("add n/Abraham Tan i/S9758366N s/M d/1997-10-27 h/Blood Test");
    }

    private void setTextApptCommand() {
        header.setText("Add Command: ");
        parameters.setText("");
        usage.setText("");
        example.setText("");
    }

    private void setTextDeleteCommand() {
        header.setText("Add Command: ");
        parameters.setText("");
        usage.setText("");
        example.setText("");
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
