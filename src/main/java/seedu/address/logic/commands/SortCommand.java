package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;
import static seedu.address.model.Model.COMPARATOR_SORT_BY_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SortOption;
import seedu.address.model.Model;

/**
 * Sorts the person list based on the specified option.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the person list.\n"
            + "If no sort option is provided, the list is restored to its original order (i.e., order of addition).\n"
            + "Parameters: " + "[" + PREFIX_SORT + "SORT_OPTION]\n"
            + "Example: " + COMMAND_WORD + " s/name";

    public static final String MESSAGE_DEFAULT_SUCCESS = "Sorted by default order";
    public static final String MESSAGE_SORT_SUCCESS = "Sorted by %s";

    private final SortOption sortOption;

    public SortCommand() {
        this.sortOption = null;
    }

    public SortCommand(SortOption sortOption) {
        this.sortOption = sortOption;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (sortOption == null) {
            model.clearPersonSort();
            return new CommandResult(MESSAGE_DEFAULT_SUCCESS);
        }

        switch (sortOption.toString()) {
        case SortOption.SORT_NAME -> model.updatePersonListSort(COMPARATOR_SORT_BY_NAME);
        default -> throw new CommandException("Unsupported sort option: " + sortOption);
        }
        return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, sortOption));
    }

    @Override
    public boolean equals(Object other) {
        // If both commands have the same sortOption, they are considered equal
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherCommand = (SortCommand) other;
        return (sortOption == null && otherCommand.sortOption == null)
                || (sortOption != null && sortOption.equals(otherCommand.sortOption));
    }
}
