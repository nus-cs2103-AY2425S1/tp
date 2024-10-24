package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;


/**
 * Sorts the persons in the address book by their appointment dates.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Sorted persons by appointment dates.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts persons by their appointment dates in ascending order.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.sortFilteredPersons();
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof SortCommand;
    }

    @Override
    public String toString() {

        return COMMAND_WORD;
    }

}
