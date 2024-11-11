package seedu.address.commons.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.QuitCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Utility class for generating command summary data.
 */
public class CommandSummaryUtil {

    /**
     * Retrieves the command summary data as an observable list.
     *
     * @return An observable list containing command summary data.
     */
    public static ObservableList<String[]> getCommandSummaryData() {
        return FXCollections.observableArrayList(
                new String[]{AddCommand.COMMAND_SUMMARY_ACTION, AddCommand.COMMAND_SUMMARY_FORMAT,
                             AddCommand.COMMAND_SUMMARY_EXAMPLES},
                new String[]{ClearCommand.COMMAND_SUMMARY_ACTION, ClearCommand.COMMAND_SUMMARY_FORMAT,
                             ClearCommand.COMMAND_SUMMARY_EXAMPLES},
                new String[]{DeleteCommand.COMMAND_SUMMARY_ACTION, DeleteCommand.COMMAND_SUMMARY_FORMAT,
                             DeleteCommand.COMMAND_SUMMARY_EXAMPLES},
                new String[]{EditCommand.COMMAND_SUMMARY_ACTION, EditCommand.COMMAND_SUMMARY_FORMAT,
                             EditCommand.COMMAND_SUMMARY_EXAMPLES},
                new String[]{QuitCommand.COMMAND_SUMMARY_ACTION, QuitCommand.COMMAND_SUMMARY_FORMAT,
                             QuitCommand.COMMAND_SUMMARY_EXAMPLES},
                new String[]{ExportCommand.COMMAND_SUMMARY_ACTION, ExportCommand.COMMAND_SUMMARY_FORMAT,
                             ExportCommand.COMMAND_SUMMARY_EXAMPLES},
                new String[]{FindCommand.COMMAND_SUMMARY_ACTION, FindCommand.COMMAND_SUMMARY_FORMAT,
                             FindCommand.COMMAND_SUMMARY_EXAMPLES},
                new String[]{HelpCommand.COMMAND_SUMMARY_ACTION, HelpCommand.COMMAND_SUMMARY_FORMAT,
                             HelpCommand.COMMAND_SUMMARY_EXAMPLES},
                new String[]{ListCommand.COMMAND_SUMMARY_ACTION, ListCommand.COMMAND_SUMMARY_FORMAT,
                             ListCommand.COMMAND_SUMMARY_EXAMPLES},
                new String[]{RedoCommand.COMMAND_SUMMARY_ACTION, RedoCommand.COMMAND_SUMMARY_FORMAT,
                             RedoCommand.COMMAND_SUMMARY_EXAMPLES},
                new String[]{UndoCommand.COMMAND_SUMMARY_ACTION, UndoCommand.COMMAND_SUMMARY_FORMAT,
                             UndoCommand.COMMAND_SUMMARY_EXAMPLES}
        );
    }
}
