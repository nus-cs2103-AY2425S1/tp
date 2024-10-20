package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.HelpCommand;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    private static class HelpCommand {
        private final String command;
        private final String description;

        public HelpCommand(String command, String description) {
            this.command = command;
            this.description = description;
        }

        public String getHelpCommand() {
            return command;
        }

        public String getDescription() {
            return description;
        }
    }

    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103t-t12-4.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide for more info: " + USERGUIDE_URL;


    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;
    @FXML
    private TableView<HelpCommand> helpTable;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        TableView<HelpCommand> table = new TableView<>();

        TableColumn<HelpCommand, String> commandColumn = new TableColumn<>("HelpCommand");
        commandColumn.setCellValueFactory(new PropertyValueFactory<>("helpCommand"));

        TableColumn<HelpCommand, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.getColumns().add(commandColumn);
        table.getColumns().add(descriptionColumn);

        // Add data to the table
        table.getItems().addAll(
                new HelpCommand("Add", "`add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [g/GAME]… [t/TAG]…`"),
                new HelpCommand("Clear", "`clear`"),
                new HelpCommand("Delete", "`delete INDEX` e.g., `delete 3`"),
                new HelpCommand("Edit", "`edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [g/Game]… [t/TAG]…`"),
                new HelpCommand("Edit Game", "`editgame INDEX g/GAME [u/USERNAME] [s/SKILLLEVEL] [r/ROLE]​`"),
                new HelpCommand("Find", "`find KEYWORD [MORE_KEYWORDS]` e.g., `find James Jake`"),
                new HelpCommand("List", "`list`"),
                new HelpCommand("Help", "`help`")
        );

        helpTable = helpTable();
        helpMessage.setText(HELP_MESSAGE);
    }


    @FXML
    private TableView<HelpCommand> helpTable() {
        TableView<HelpCommand> table = new TableView<>();

        TableColumn<HelpCommand, String> commandColumn = new TableColumn<>("HelpCommand");
        commandColumn.setCellValueFactory(new PropertyValueFactory<>("helpCommand"));

        TableColumn<HelpCommand, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.getColumns().add(commandColumn);
        table.getColumns().add(descriptionColumn);

        // Add data to the table
        table.getItems().addAll(
                new HelpCommand("Add", "`add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [g/GAME]… [t/TAG]…`"),
                new HelpCommand("Clear", "`clear`"),
                new HelpCommand("Delete", "`delete INDEX` e.g., `delete 3`"),
                new HelpCommand("Edit", "`edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [g/Game]… [t/TAG]…​`"),
                new HelpCommand("Edit Game", "`editgame INDEX g/GAME [u/USERNAME] [s/SKILLLEVEL] [r/ROLE]​`"),
                new HelpCommand("Find", "`find KEYWORD [MORE_KEYWORDS]` e.g., `find James Jake`"),
                new HelpCommand("List", "`list`"),
                new HelpCommand("Help", "`help`")
        );
        return table;
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
