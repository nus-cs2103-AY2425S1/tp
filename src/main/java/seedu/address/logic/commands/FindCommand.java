package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.ArgumentPredicate;

/**
 * Finds and lists all persons in address book who match parameters specified. Values matched are case insensitive
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who fit the parameters specified "
            + "(case-insensitive) and displays them as a list with index numbers.\n"
            + "At least 1 parameter must be specified\n"
            + "Parameters: [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] "
            + "[t/TAG]… [ps/PROJECT_STATUS] [py/PAYMENT_STATUS] [cs/CLIENT_STATUS] [d/deadline]\n"
            + "Example: " + COMMAND_WORD + " n/Alice ps/completed";

    private final ArgumentPredicate predicate;

    public FindCommand(ArgumentPredicate predicate) {
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
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
