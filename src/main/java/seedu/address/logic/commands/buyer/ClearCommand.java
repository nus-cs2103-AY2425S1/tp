package seedu.address.logic.commands.buyer;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.BuyerList;
import seedu.address.model.Model;

/**
 * Clears the buyer list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Buyer list has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setBuyerList(new BuyerList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
