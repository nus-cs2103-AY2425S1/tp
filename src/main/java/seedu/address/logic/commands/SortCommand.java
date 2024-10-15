package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_PARAM;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.sort.SortParam;

/**
 * Sorts contacts based on name or class.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts contacts based on name/class. "
            + "Parameters: "
            + PREFIX_SORT_PARAM + "'name'/'class' "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SORT_PARAM + "name";

    public static final String MESSAGE_SUCCESS = "Sorted by: %1$s";

    private SortParam sortParam;

    /**
     * Creates a SortCommand
     * @param sortParam SortParam.NAME or SortParam.CLASS
     */
    public SortCommand(SortParam sortParam) {
        requireAllNonNull(sortParam);
        this.sortParam = sortParam;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        //sort the contact book
        model.sortAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, sortParam.toString()));

    }
}
