package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.commands.BookingsCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListBackupsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateOperatingHoursCommand;
import seedu.address.model.CommandInfo;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-f11-3.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE =
            "For a full explanation of each command, refer to our user guide:\n" + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private VBox helpList;

    @FXML
    private HelpListPanel helpListPanel;

    @FXML
    private StackPane helpListPanelPlaceholder;


    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpListPanel = new HelpListPanel(FXCollections.observableArrayList(
                new CommandInfo(AddCommand.MESSAGE_USAGE),
                new CommandInfo(DeleteCommand.MESSAGE_USAGE),
                new CommandInfo(UpdateCommand.MESSAGE_USAGE),
                new CommandInfo(ListCommand.MESSAGE_USAGE),
                new CommandInfo(FindCommand.MESSAGE_USAGE),
                new CommandInfo(BookingsCommand.MESSAGE_USAGE),
                new CommandInfo(DeleteAppointmentCommand.MESSAGE_USAGE),
                new CommandInfo(UpdateOperatingHoursCommand.MESSAGE_USAGE),
                new CommandInfo(ClearCommand.MESSAGE_USAGE),
                new CommandInfo(BackupCommand.MESSAGE_USAGE),
                new CommandInfo(ListBackupsCommand.MESSAGE_USAGE),
                new CommandInfo(RestoreCommand.MESSAGE_USAGE),
                new CommandInfo(HelpCommand.MESSAGE_USAGE),
                new CommandInfo(ExitCommand.MESSAGE_USAGE))
        );
        helpListPanelPlaceholder.getChildren().add(helpListPanel.getRoot());

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
