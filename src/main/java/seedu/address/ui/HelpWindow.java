package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.InspectCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UnarchiveCommand;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-t12-3.github.io/tp/";
    public static final String HELP_MESSAGE = """
        LogiLink is a delivery management app for small logistics companies to track their deliveries.

        Below are the commands available in this window.
        The commands are clickable
        Refer to the user guide for more details:""" + " " + USERGUIDE_URL;

    // Help cards for default view. Update if there are new commands
    private static final ArrayList<CommandHelpCard> DEFAULT_HELP_CARDS = new ArrayList<>(Arrays.asList(
        new CommandHelpCard(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE_PERSON, AddCommand.ADD_PERSON_EXAMPLE),
        new CommandHelpCard(ClearCommand.COMMAND_WORD, "Clears address book", "clear"),
        new CommandHelpCard(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE, "delete 1 2"),
        new CommandHelpCard(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE, EditCommand.PERSON_EXAMPLE),
        new CommandHelpCard(ExitCommand.COMMAND_WORD, "Closes the app", "exit"),
        new CommandHelpCard(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE, "find bob"),
        new CommandHelpCard(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE, "help"),
        new CommandHelpCard(InspectCommand.COMMAND_WORD, InspectCommand.MESSAGE_USAGE, "inspect 1"),
        new CommandHelpCard(ListCommand.COMMAND_WORD, "Lists all persons", "list")
    ));

    // Help cards for inspect view. Update if there are new commands
    private static final ArrayList<CommandHelpCard> INSPECT_HELP_CARDS = new ArrayList<>(Arrays.asList(
        new CommandHelpCard(
            AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE_DELIVERY, AddCommand.ADD_DELIVERY_EXAMPLE
        ),
        new CommandHelpCard(ArchiveCommand.COMMAND_WORD, ArchiveCommand.MESSAGE_USAGE, "archive 1"),
        new CommandHelpCard(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE_DELIVERY, "delete 1 2"),
        new CommandHelpCard(EditCommand.COMMAND_WORD, EditCommand.INSPECT_MESSAGE_USAGE, EditCommand.DELIVERY_EXAMPLE),
        new CommandHelpCard(ExitCommand.COMMAND_WORD, "Closes the app", "exit"),
        new CommandHelpCard(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE, "help"),
        new CommandHelpCard(ListCommand.COMMAND_WORD, "Lists all persons", "list"),
        new CommandHelpCard(
            SortCommand.COMMAND_WORD_ASCENDING, SortCommand.MESSAGE_USAGE_ASCENDING_INSPECT, "asort by/ cost"
        ),
        new CommandHelpCard(
            SortCommand.COMMAND_WORD_DESCENDING, SortCommand.MESSAGE_USAGE_DESCENDING_INSPECT, "dsort by/ cost"
        ),
        new CommandHelpCard(UnarchiveCommand.COMMAND_WORD, UnarchiveCommand.MESSAGE_USAGE, "unarchive 1")
    ));

    private static HelpWindow theOne;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button copyButton;
    @FXML
    private Label helpMessage;
    @FXML
    private VBox commandHelpCardsContainer;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    private HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        scrollPane.setOnKeyPressed(this::handleKey);
    }

    /**
     * Creates a new HelpWindow.
     */
    private HelpWindow() {
        this(new Stage());
    }

    public static HelpWindow getInstance() {
        if (theOne == null) {
            theOne = new HelpWindow();
        }
        return theOne;
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
    public void show(boolean isInspect, CommandBox currentCommandBox) {
        logger.fine("Showing help page about the application.");
        setHelpCards(isInspect ? INSPECT_HELP_CARDS : DEFAULT_HELP_CARDS);
        CommandHelpCard.setCommandBox(currentCommandBox);
        CommandHelpCard.setHelpWindow(this);
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
     * Closes the help window
     */
    public void close() {
        getRoot().hide();
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

    private void handleKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            close();
        }
    }

    /**
     * Changes the focus to the card before the current
     */
    public void focusPreviousCard(CommandHelpCard card) {
        int idx = commandHelpCardsContainer.getChildren().indexOf(card.getRoot());
        if (idx > 0) {
            commandHelpCardsContainer.getChildren().get(idx - 1).requestFocus();
        }
    }

    /**
     * Changes the focus to the card after the current
     */
    public void focusNextCard(CommandHelpCard card) {
        int idx = commandHelpCardsContainer.getChildren().indexOf(card.getRoot());
        int size = commandHelpCardsContainer.getChildren().size();
        if (idx != -1 && idx < size - 1) {
            commandHelpCardsContainer.getChildren().get(idx + 1).requestFocus();
        }
    }

    private void setHelpCards(ArrayList<CommandHelpCard> helpCards) {
        commandHelpCardsContainer.getChildren().clear();
        for (CommandHelpCard helpCard : helpCards) {
            commandHelpCardsContainer.getChildren().add(helpCard.getRoot());
        }
    }
}
