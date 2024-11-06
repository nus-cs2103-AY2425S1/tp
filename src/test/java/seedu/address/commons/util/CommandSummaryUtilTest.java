package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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


public class CommandSummaryUtilTest {

    @Test
    public void getCommandSummaryData_validData_correctSize() {
        ObservableList<String[]> data = CommandSummaryUtil.getCommandSummaryData();
        assertEquals(11, data.size());
    }

    @Test
    public void getCommandSummaryData_validData_correctContent() {
        ObservableList<String[]> data = CommandSummaryUtil.getCommandSummaryData();
        assertEquals(AddCommand.COMMAND_SUMMARY_ACTION, data.get(0)[0]);
        assertEquals(ClearCommand.COMMAND_SUMMARY_ACTION, data.get(1)[0]);
        assertEquals(DeleteCommand.COMMAND_SUMMARY_ACTION, data.get(2)[0]);
        assertEquals(EditCommand.COMMAND_SUMMARY_ACTION, data.get(3)[0]);
        assertEquals(QuitCommand.COMMAND_SUMMARY_ACTION, data.get(4)[0]);
        assertEquals(ExportCommand.COMMAND_SUMMARY_ACTION, data.get(5)[0]);
        assertEquals(FindCommand.COMMAND_SUMMARY_ACTION, data.get(6)[0]);
        assertEquals(HelpCommand.COMMAND_SUMMARY_ACTION, data.get(7)[0]);
        assertEquals(ListCommand.COMMAND_SUMMARY_ACTION, data.get(8)[0]);
        assertEquals(RedoCommand.COMMAND_SUMMARY_ACTION, data.get(9)[0]);
        assertEquals(UndoCommand.COMMAND_SUMMARY_ACTION, data.get(10)[0]);
    }

    @Test
    public void getCommandSummaryData_validData_correctFormat() {
        ObservableList<String[]> data = CommandSummaryUtil.getCommandSummaryData();
        assertEquals(AddCommand.COMMAND_SUMMARY_FORMAT, data.get(0)[1]);
        assertEquals(ClearCommand.COMMAND_SUMMARY_FORMAT, data.get(1)[1]);
        assertEquals(DeleteCommand.COMMAND_SUMMARY_FORMAT, data.get(2)[1]);
        assertEquals(EditCommand.COMMAND_SUMMARY_FORMAT, data.get(3)[1]);
        assertEquals(QuitCommand.COMMAND_SUMMARY_FORMAT, data.get(4)[1]);
        assertEquals(ExportCommand.COMMAND_SUMMARY_FORMAT, data.get(5)[1]);
        assertEquals(FindCommand.COMMAND_SUMMARY_FORMAT, data.get(6)[1]);
        assertEquals(HelpCommand.COMMAND_SUMMARY_FORMAT, data.get(7)[1]);
        assertEquals(ListCommand.COMMAND_SUMMARY_FORMAT, data.get(8)[1]);
        assertEquals(RedoCommand.COMMAND_SUMMARY_FORMAT, data.get(9)[1]);
        assertEquals(UndoCommand.COMMAND_SUMMARY_FORMAT, data.get(10)[1]);
    }

    @Test
    public void getCommandSummaryData_validData_correctExamples() {
        ObservableList<String[]> data = CommandSummaryUtil.getCommandSummaryData();
        assertEquals(AddCommand.COMMAND_SUMMARY_EXAMPLES, data.get(0)[2]);
        assertEquals(ClearCommand.COMMAND_SUMMARY_EXAMPLES, data.get(1)[2]);
        assertEquals(DeleteCommand.COMMAND_SUMMARY_EXAMPLES, data.get(2)[2]);
        assertEquals(EditCommand.COMMAND_SUMMARY_EXAMPLES, data.get(3)[2]);
        assertEquals(QuitCommand.COMMAND_SUMMARY_EXAMPLES, data.get(4)[2]);
        assertEquals(ExportCommand.COMMAND_SUMMARY_EXAMPLES, data.get(5)[2]);
        assertEquals(FindCommand.COMMAND_SUMMARY_EXAMPLES, data.get(6)[2]);
        assertEquals(HelpCommand.COMMAND_SUMMARY_EXAMPLES, data.get(7)[2]);
        assertEquals(ListCommand.COMMAND_SUMMARY_EXAMPLES, data.get(8)[2]);
        assertEquals(RedoCommand.COMMAND_SUMMARY_EXAMPLES, data.get(9)[2]);
        assertEquals(UndoCommand.COMMAND_SUMMARY_EXAMPLES, data.get(10)[2]);
    }
}
