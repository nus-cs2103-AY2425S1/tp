package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.ContactTypePredicate;

/**
 * Command to filter contacts in the address book based on their contact type.
 * Supported contact types are "Work" and "Personal".
 */
public class FilterContactTypeCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters your contacts based on their ContactType (Work, Personal).\n"
            + "Parameters: ContactType (must one of the following: \"Work\", \"School\" or \"Personal\")\n"
            + "Example: " + COMMAND_WORD + " Work";

    private final ContactTypePredicate predicate;

    /**
     * Constructs a FilterContactTypeCommand with the given predicate.
     *
     * @param predicate The predicate used to filter the contacts based on contact type.
     */
    public FilterContactTypeCommand(ContactTypePredicate predicate) {
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
        if (!(other instanceof FilterContactTypeCommand)) {
            return false;
        }

        FilterContactTypeCommand otherFilterCommand = (FilterContactTypeCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
