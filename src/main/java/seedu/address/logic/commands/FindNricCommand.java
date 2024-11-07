package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NricMatchesPredicate;

/**
 * Find and list a patient in the address book whose nric matches the specified nric.
 */
public class FindNricCommand extends Command {

    public static final String COMMAND_WORD = "findNric";
    public static final String COMMAND_WORD_INSENSITIVE = "findnric";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds a patient whose nric matches "
            + "the specified nric (case-insensitive) and displays them.\n"
            + "Parameters: NRIC\n"
            + "Example: " + COMMAND_WORD + " T0123456F";

    private final NricMatchesPredicate predicate;

    /**
     * Creates a FindNricCommand to find the specified {@code Nric}
     *
     * @param predicate the predicate to find the specified {@code Nric}.
     */
    public FindNricCommand(NricMatchesPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindNricCommand)) {
            return false;
        }

        FindNricCommand otherFindNricCommand = (FindNricCommand) other;
        return predicate.equals(otherFindNricCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
