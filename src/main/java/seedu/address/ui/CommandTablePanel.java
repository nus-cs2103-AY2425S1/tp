package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddNotesCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ViewCommand;

/**
 * Panel containing the list of command help.
 */
public class CommandTablePanel extends UiPart<Region> {
    private static final String FXML = "CommandTablePanel.fxml";

    private static final double COMMAND_USAGE_COLUMN_SIZE = Integer.MAX_VALUE * 0.85; // 85% of table width
    private static final double COMMAND_WORD_COLUMN_SIZE = Integer.MAX_VALUE * 0.15; // 15% of table width

    private final Logger logger = LogsCenter.getLogger(CommandTablePanel.class);


    @FXML
    private TableView<CommandEntry> commandTable;

    @FXML
    private TableColumn<CommandEntry, String> actionColumn;

    @FXML
    private TableColumn<CommandEntry, String> formatColumn;

    /**
     * Creates a {@code CommandTablePanel}
     */
    public CommandTablePanel() {
        super(FXML);
        initializeTable();
        populateData();
    }

    /**
     * Initialize table formatting.
     */
    private void initializeTable() {
        commandTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        actionColumn.setCellValueFactory(new PropertyValueFactory<>("commandWord"));
        actionColumn.setMaxWidth(COMMAND_WORD_COLUMN_SIZE);

        formatColumn.setCellValueFactory(new PropertyValueFactory<>("commandUsage"));
        formatColumn.setCellFactory(column -> new CommandTableCell());
        formatColumn.setMaxWidth(COMMAND_USAGE_COLUMN_SIZE);

    }

    /**
     * Fills up the table with the commands.
     *
     * @param commandList list of commands to print.
     */
    private void populateData() {
        logger.fine("Showing a few command format of the application.");
        ObservableList<CommandEntry> commandList = FXCollections.observableArrayList(
            // Add more commands here as needed
            new CommandEntry(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE),
            new CommandEntry(AddNotesCommand.COMMAND_WORD, AddNotesCommand.MESSAGE_USAGE),
            new CommandEntry(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE),
            new CommandEntry(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE),
            new CommandEntry(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE),
            new CommandEntry(ViewCommand.COMMAND_WORD, ViewCommand.MESSAGE_USAGE)
        );
        commandTable.setItems(commandList);
    }

    /**
     * Custom table record that displays onto table.
     */
    public class CommandEntry {
        private String commandWord;
        private String commandUsage;

        /**
         * Creates a {@code CommandEntry} with the given command word and usage string.
         */
        public CommandEntry(String commandWord, String commandUsage) {
            this.commandWord = commandWord;
            this.commandUsage = commandUsage;
        }

        /**
         * Gets the command word.
         *
         * @return command word.
         */
        public String getCommandWord() {
            return this.commandWord;
        }

        /**
         * Gets the command usage syntax.
         *
         * @return command usage.
         */
        public String getCommandUsage() {
            return this.commandUsage;
        }
    }

    /**
     * Custom {@code TableCell} that displays the graphics of a command record.
     */
    class CommandTableCell extends TableCell<CommandEntry, String> {
        private final Text text = new Text();

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                text.setText(item);
                text.wrappingWidthProperty()
                        .bind(getTableColumn().widthProperty().subtract(10));
                setGraphic(text);
            }
        }
    };
}
