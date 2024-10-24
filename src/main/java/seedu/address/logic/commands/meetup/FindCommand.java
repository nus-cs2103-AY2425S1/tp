package seedu.address.logic.commands.meetup;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.meetup.MeetUpContainsKeywordsPredicate;

/**
 * Finds and lists all meet-ups in meet-up list whose meet-up name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all meet-up whose meet-up names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "KEYWORD ";

    private final MeetUpContainsKeywordsPredicate predicate;

    public FindCommand(MeetUpContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMeetUpList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_MEETUPS_LISTED_OVERVIEW, model.getFilteredMeetUpList().size()),
                false, false, true, false, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.logic.commands.meetup.FindCommand)) {
            return false;
        }

        seedu.address.logic.commands.meetup.FindCommand otherFindCommand =
                (seedu.address.logic.commands.meetup.FindCommand) other;

        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
