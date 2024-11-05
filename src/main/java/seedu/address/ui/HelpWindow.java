package seedu.address.ui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.FunctionalBrowser;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddGradeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GitHubCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UnmarkCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-t11-2.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Please refer to the user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    private static final CommandDetailCard[] COMMANDS = {
        new CommandDetailCard(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE),
        new CommandDetailCard(AddGradeCommand.COMMAND_WORD, AddGradeCommand.MESSAGE_USAGE),
        new CommandDetailCard(ClearCommand.COMMAND_WORD, ClearCommand.MESSAGE_USAGE),
        new CommandDetailCard(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE),
        new CommandDetailCard(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE),
        new CommandDetailCard(ExportCommand.COMMAND_WORD, ExportCommand.MESSAGE_USAGE),
        new CommandDetailCard(FilterCommand.COMMAND_WORD, FilterCommand.MESSAGE_USAGE),
        new CommandDetailCard(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE),
        new CommandDetailCard(GitHubCommand.COMMAND_WORD, GitHubCommand.MESSAGE_USAGE),
        new CommandDetailCard(ImportCommand.COMMAND_WORD, ImportCommand.MESSAGE_USAGE),
        new CommandDetailCard(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_USAGE),
        new CommandDetailCard(MarkCommand.COMMAND_WORD, MarkCommand.MESSAGE_USAGE),
        new CommandDetailCard(UnmarkCommand.COMMAND_WORD, UnmarkCommand.MESSAGE_USAGE),
        new CommandDetailCard(SortCommand.COMMAND_WORD, SortCommand.MESSAGE_USAGE),
        new CommandDetailCard(ViewCommand.COMMAND_WORD, ViewCommand.MESSAGE_USAGE),
        new CommandDetailCard(ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_USAGE),
    };

    private final ObservableList<CommandDetailCard> commandList = FXCollections.observableArrayList();

    @FXML
    private Button copyButton;

    @FXML
    private Button openLinkButton;
    @FXML
    private Label helpMessage;

    @FXML
    private ListView<CommandDetailCard> commandDetailCardListView;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);

        commandList.addAll(COMMANDS);
        commandDetailCardListView.setItems(commandList);
        commandDetailCardListView.setCellFactory(listView -> new CommandListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a command detail using a {@code CommandDetailCard}.
     */
    class CommandListViewCell extends ListCell<CommandDetailCard> {
        @Override
        protected void updateItem(CommandDetailCard card, boolean empty) {
            super.updateItem(card, empty);

            if (empty || card == null) {
                setGraphic(null);
            } else {
                setGraphic(card.getRoot());
            }
        }
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

    /**
     * Launches the URL of the user guide in the user's console
     *
     * @throws URISyntaxException
     * @throws IOException
     */
    @FXML
    private void openUrl() throws CommandException {
        FunctionalBrowser.getDesktop().launchUri(USERGUIDE_URL);
    }
}
