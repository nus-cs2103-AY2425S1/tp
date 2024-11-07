package hallpointer.address.logic.commands;

import static java.util.Objects.requireNonNull;

import hallpointer.address.commons.util.ToStringBuilder;
import hallpointer.address.logic.Messages;
import hallpointer.address.model.Model;
import hallpointer.address.model.member.NameContainsKeywordsPredicate;

/**
 * Finds and lists all members in the HallPointer whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindMemberCommand extends Command {

    public static final String COMMAND_WORD = "find_members";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all members whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " John Jane";

    private final NameContainsKeywordsPredicate predicate;

    public FindMemberCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMemberList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_MEMBERS_LISTED_OVERVIEW, model.getFilteredMemberList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindMemberCommand)) {
            return false;
        }

        FindMemberCommand otherFindMemberCommand = (FindMemberCommand) other;
        return predicate.equals(otherFindMemberCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
