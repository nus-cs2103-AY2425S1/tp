package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Sort all persons in the address book according to name keeping pinned persons on top.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortByName();
        model.sortByPin();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
