package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-f08-1a.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide for more details: " + USERGUIDE_URL;
    public static final ObservableList<String[]> COMMAND_SUMMARY = FXCollections.observableArrayList(
            new String[]{"AddLesson", "addLesson TUTOR_INDEX TUTEE_INDEX \\s SUBJECT\ne.g., addLesson 1 2 \\s science"},
            new String[]{"AddTutee", "addTutee \\n NAME \\p PHONE_NUMBER \\e EMAIL \\a ADDRESS [\\h HOURS] "
                    + "[\\s SUBJECT]…\n e.g., addTutee \\n Evan Lee \\p 88889999 \\e evanlee@example.com \\a "
                    + "345, Clementi Rd, 123466 \\h 2 \\s english"},
            new String[]{"AddTutor", "addTutee \\n NAME \\p PHONE_NUMBER \\e EMAIL \\a ADDRESS [\\h HOURS] "
                    + "[\\s SUBJECT]…\n e.g., addTutor \\n James Ho \\p 22224444 \\e jamesho@example.com \\a "
                    + "123, Clementi Rd, 123465 \\h 7 \\s math"},
            new String[]{"Clear", "clear"},
            new String[]{"Delete", "delete INDEX\n e.g., delete 3"},
            new String[]{"DeleteLesson", "deleteLesson TUTOR_INDEX TUTEE_INDEX \\s SUBJECT\n e.g., deleteLesson 1 2 "
                    + "\\s science"},
            new String[]{"Edit", "edit INDEX [\\n NAME] [\\p PHONE_NUMBER] [\\e EMAIL] [\\a ADDRESS] [\\h HOURS] "
                    + "[\\s SUBJECT]…\n e.g., edit 2 \\n James Lee \\e jameslee@example.com"},
            new String[]{"Find", "find KEYWORD [MORE_KEYWORDS]…\n e.g., find James Jake"},
            new String[]{"FindSubject", "findSubject SUBJECT [MORE_SUBJECTS]…\n e.g., findSubject math science"},
            new String[]{"History", "history"},
            new String[]{"Import", "import \\f FILEPATH"},
            new String[]{"List", "list"},
            new String[]{"Undo", "undo"},
            new String[]{"Redo", "redo"},
            new String[]{"View", "view"},
            new String[]{"View Tutor Hours", "vtc"},
            new String[]{"Exit", "exit"},
            new String[]{"Help", "help"}
    );

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private TableView<String[]> tableView;

    @FXML
    private TableColumn<String[], String> action;

    @FXML
    private TableColumn<String[], String> format;


    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);

        action.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));
        format.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));
        tableView.setItems(COMMAND_SUMMARY);

        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
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

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
