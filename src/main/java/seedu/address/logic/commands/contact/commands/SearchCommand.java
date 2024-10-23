package seedu.address.logic.commands.contact.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventManager;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonIsRolePredicate;

/**
 * Finds and lists all persons in address book who has any role equal to any of the role keywords.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for all persons who are of a certain role "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: ROLE [MORE_ROLES]...\n"
            + "Example: " + COMMAND_WORD + " attendee vendor";

    private final PersonIsRolePredicate predicate;

    public SearchCommand(PersonIsRolePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, EventManager eventManager) {
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
        if (!(other instanceof SearchCommand)) {
            return false;
        }

        SearchCommand otherSearchCommand = (SearchCommand) other;
        return predicate.equals(otherSearchCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
