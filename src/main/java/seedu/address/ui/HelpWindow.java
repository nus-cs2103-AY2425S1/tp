package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-t12-4.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide for more info: " + USERGUIDE_URL
            + "\n **Add**    \n" +
            "`add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [g/GAME]… [t/TAG]…\u200B`  \n" +
            "e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 g/Overwatch t/friend t/colleague`\n" +
            "\n" +
            "**Clear**  \n" +
            "`clear`\n" +
            "\n" +
            "**Delete**  \n" +
            "`delete INDEX`  \n" +
            "e.g., `delete 3`\n" +
            "\n" +
            "**Edit**  \n" +
            "`edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [g/Game]… [t/TAG]…\u200B`  \n" +
            "e.g., `edit 2 n/James Lee e/jameslee@example.com`\n" +
            "\n" +
            "**Edit Game**  \n" +
            "`editgame INDEX g/GAME [u/USERNAME] [s/SKILLLEVEL] [r/ROLE]\u200B`  \n" +
            "e.g., `editgame 1 g/Overwatch u/Potato`\n" +
            "\n" +
            "**Find**  \n" +
            "`find KEYWORD [MORE_KEYWORDS]`  \n" +
            "e.g., `find James Jake`\n" +
            "\n" +
            "**List**  \n" +
            "`list`\n" +
            "\n" +
            "**Help**  \n" +
            "`help`\n";


    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
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
