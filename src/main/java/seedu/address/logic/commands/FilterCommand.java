package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PersonHasFeaturePredicate;

/**
 * Finds and lists all persons in address book according to their tag.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons with the specified tag and/or phone"
          + " number and displays them as a list with index numbers.\n"
          + "Parameters: PREFIX/PREFIX_NAME + [PREFIX/MORE_PREFIX_NAMES...]\n"
          + "Example: " + COMMAND_WORD + " t/High Risk p/88506657";

    public static final String MESSAGE_NO_MATCHES_FOUND = "No patients align with the provided details.";
    private final PersonHasFeaturePredicate predicate;

    public FilterCommand(PersonHasFeaturePredicate predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        if (model.getFilteredPersonList().isEmpty()) {

            return new CommandResult(MESSAGE_NO_MATCHES_FOUND);
        }
        return new CommandResult(
              String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFindCommand = (FilterCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
              .add("predicate", predicate)
              .toString();
    }

}
