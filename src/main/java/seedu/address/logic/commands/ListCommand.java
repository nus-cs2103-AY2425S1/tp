package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;
import static seedu.address.model.Model.COMPARATOR_SORT_BY_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SortOption;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all persons in the address book.\n"
            + "Parameters: " + "[" + PREFIX_SORT + "SORT_OPTION]\n"
            + "Example: " + COMMAND_WORD + " s/name";

    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_SORT_SUCCESS = "Listed all persons sorted by %s";

    private final SortOption sortOption;

    public ListCommand() {
        this.sortOption = null;
    }

    public ListCommand(SortOption sortOption) {
        this.sortOption = sortOption;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (sortOption == null) {
            model.clearPersonSort();
            return new CommandResult(MESSAGE_SUCCESS);
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

        if (!(other instanceof ListCommand)) {
            return false;
        }

        ListCommand otherCommand = (ListCommand) other;
        return (sortOption == null && otherCommand.sortOption == null)
                || (sortOption != null && sortOption.equals(otherCommand.sortOption));
    }
}
