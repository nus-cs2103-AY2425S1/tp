package seedu.address.model.command;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPropertyToBuyCommand;
import seedu.address.logic.commands.AddPropertyToSellCommand;
import seedu.address.logic.commands.BoughtPropertyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePropertyToBuyCommand;
import seedu.address.logic.commands.DeletePropertyToSellCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindBuyCommand;
import seedu.address.logic.commands.FindNameCommand;
import seedu.address.logic.commands.FindPhoneNumberCommand;
import seedu.address.logic.commands.FindSellCommand;
import seedu.address.logic.commands.FindTagContactCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.PinContactCommand;
import seedu.address.logic.commands.SoldPropertyCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortIndividualCommand;
import seedu.address.logic.commands.StatisticsCommand;
import seedu.address.logic.commands.UnpinContactCommand;

/**
 * Represents the Commands that the address book can execute.
 */
public class Commands {
    private ObservableList<String> commandList = FXCollections.observableArrayList();

    /**
     * Creates a new {@code Commands} and initialises the commandList with all available commands
     */
    public Commands() {
        String[] commands = {HelpCommand.COMMAND_WORD, StatisticsCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
            AddCommand.COMMAND_WORD, AddPropertyToBuyCommand.COMMAND_WORD,
            AddPropertyToSellCommand.COMMAND_WORD, EditCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD, DeletePropertyToSellCommand.COMMAND_WORD,
            DeletePropertyToBuyCommand.COMMAND_WORD, FindNameCommand.COMMAND_WORD,
            FindPhoneNumberCommand.COMMAND_WORD, FindTagContactCommand.COMMAND_WORD,
            FindBuyCommand.COMMAND_WORD, FindSellCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD, SortCommand.COMMAND_WORD,
            SortIndividualCommand.COMMAND_WORD, BoughtPropertyCommand.COMMAND_WORD,
            SoldPropertyCommand.COMMAND_WORD, PinContactCommand.COMMAND_WORD,
            UnpinContactCommand.COMMAND_WORD};
        commandList.addAll(commands);
    }

    public ObservableList<String> getListofCommands() {
        return this.commandList;
    }

    @Override
    public String toString() {
        return String.join("\n", commandList);
    }

}
