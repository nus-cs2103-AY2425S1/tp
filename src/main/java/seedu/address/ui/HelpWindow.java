package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListAttendanceCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.UnmarkAttendanceCommand;
import seedu.address.logic.commands.ViewCommand;

/**
 * Controller for a help page.
 */
public class HelpWindow extends UiPart<Stage> {
    public static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
    public static final String HELP_MESSAGE = "Copy the link for the complete user guide  ---> ";
    public static final String HELP_USAGE;
    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    private static final String[] commandMessageUsages = {
        AddCommand.MESSAGE_USAGE,
        ClearCommand.MESSAGE_USAGE,
        DeleteCommand.MESSAGE_USAGE,
        EditCommand.MESSAGE_USAGE,
        ExitCommand.MESSAGE_USAGE,
        FindCommand.MESSAGE_USAGE,
        HelpCommand.MESSAGE_USAGE,
        ListAttendanceCommand.MESSAGE_USAGE,
        ListCommand.MESSAGE_USAGE,
        MarkAttendanceCommand.MESSAGE_USAGE,
        SortCommand.MESSAGE_USAGE,
        SwitchCommand.MESSAGE_USAGE,
        UnmarkAttendanceCommand.MESSAGE_USAGE,
        ViewCommand.MESSAGE_USAGE
    };

    static {
        StringBuilder helpMessageBuilder = new StringBuilder("""
                ===============================
                Command Summary
                ===============================
                """);
        for (int i = 0; i < commandMessageUsages.length; i++) {
            String[] parts = commandMessageUsages[i].split(":", 2);
            helpMessageBuilder.append(String.format("%d. %s:\n", i + 1, parts[0].trim()));
            if (parts.length > 1) {
                helpMessageBuilder.append("\t").append(parts[1].trim().replace("\n", "\n\t")).append("\n\n");
            } else {
                helpMessageBuilder.append("\n");
            }
        }
        HELP_USAGE = helpMessageBuilder.toString();
    }

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private Label helpUsage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        helpUsage.setText(HELP_USAGE);
        root.setMinWidth(600);
        root.setMinHeight(400);
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
