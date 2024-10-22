package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Filters the address book for persons who have the corresponding tag
 */
public class FilterTagCommand extends FilterCommand {
    public static final String MESSAGE_TAG_DOESNT_EXIST = "This tag does not exist!";
    private final TagContainsKeywordsPredicate predicate;

    /**
     * Creates a FilterTagCommand that checks if the user has a tag that matches the predicate
     */
    public FilterTagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasTag(predicate.getTag())) {
            throw new CommandException(MESSAGE_TAG_DOESNT_EXIST);
        }
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
        if (!(other instanceof FilterTagCommand)) {
            return false;
        }

        FilterTagCommand otherFilterTagCommand = (FilterTagCommand) other;
        return predicate.equals(otherFilterTagCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
