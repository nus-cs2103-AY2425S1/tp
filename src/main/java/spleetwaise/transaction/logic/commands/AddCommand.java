package spleetwaise.transaction.logic.commands;

import spleetwaise.address.logic.commands.CommandResult;
import spleetwaise.transaction.model.Model;

/**
 * This class represents a command for adding transactions.
 * TODO: probably need to write parser for this
 */
public class AddCommand extends Command {

    /**
     * The word used to trigger this command in the input.
     */
    public static final String COMMAND_WORD = "addTxn";

    /**
     * The message usage string that explains how to use this command.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a new transaction.\n"
        + "Example: TODO"; // TODO: implement add


    /**
     * This method executes the add command.
     *
     * @param model the model of the transactions
     * @return the result of the execution
     */
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("TODO: implement add", false, false);
    }

}
