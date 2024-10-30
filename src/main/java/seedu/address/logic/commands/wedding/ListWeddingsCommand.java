package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WEDDINGS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResult.SwitchView;
import seedu.address.model.Model;

/**
 * Lists all weddings in the address book to the user.
 */
public class ListWeddingsCommand extends Command {

    public static final String COMMAND_WORD = "list-weddings";

    public static final String MESSAGE_SUCCESS = "Listed all weddings";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
        return new CommandResult(MESSAGE_SUCCESS, SwitchView.WEDDING);
    }
}
