package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PersonHaveSubjectPredicate;

/**
 * Finds and lists all persons in address book who teaches or learns the specified subject
 * Keyword matching is case insensitive.
 */
public class FindSubjectCommand extends Command {

    public static final String COMMAND_WORD = "findSubject";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who teaches or learns "
            + "the specified subject (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: SUBJECT [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " english";

    private final PersonHaveSubjectPredicate predicate;

    public FindSubjectCommand(PersonHaveSubjectPredicate predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size())
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindSubjectCommand)) {
            return false;
        }

        FindSubjectCommand otherFindCommand = (FindSubjectCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate subject", predicate)
                .toString();
    }
}
