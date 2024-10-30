package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String HELP_MESSAGE = "ClinicConnect provides you with the commands shown below.\n"
            + "For more information on any specific command, type help <command keyword>";
    private static final String KEYWORDS_HEADER = "Command Keywords";
    private static final String FUNCTIONS_HEADER = "Functions";
    private static final String HELP_KEYWORDS = "add\naddf\nappt\nclear\ndeleteappt\ndelete\nedit\nexit\nfilter\nhome"
            + "\nview";
    private static final String HELP_FUNCTIONS = """
            Adds a new patient record into the system
            Adds a new patient record (with additional information) into the system
            Records appointment times for registered patients into the system
            Clears all existing system records
            Deletes the specified appointment for the identified patient
            Deletes an existing patient record from the system
            Edits patient's detail(s) for an existing patient record in the system
            Exits the system
            Filters existing patient records based on the specified parameters
            Returns to home page
            Views full profile of identified patient in the system
            """;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private VBox helpContainer;

    @FXML
    private Label helpMessage;

    @FXML
    private Label commandKeywordsHeader;

    @FXML
    private Label functionsHeader;

    @FXML
    private Label commandKeywords;

    @FXML
    private Label functions;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        commandKeywordsHeader.setText(KEYWORDS_HEADER);
        functionsHeader.setText(FUNCTIONS_HEADER);
        commandKeywords.setText(HELP_KEYWORDS);
        functions.setText(HELP_FUNCTIONS);

        getRoot().setWidth(500);
        getRoot().setHeight(400);

        closeWindowKeyboardShortcut();
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     *
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

    /**
     * Closes the help window.
     */
    public void closeWindowKeyboardShortcut() {
        Stage stage = (Stage) getRoot().getScene().getWindow();
        if (stage != null) {
            stage.addEventHandler(KeyEvent.KEY_PRESSED, t -> {
                if (t.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                    t.consume();
                }
            });
        }
    }
}
