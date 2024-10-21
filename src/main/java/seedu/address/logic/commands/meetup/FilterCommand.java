package seedu.address.logic.commands.meetup;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.meetup.MeetUpContainsKeywordsPredicate;


/**
 * Finds and lists all meetings in meet-up list whose date is the same as the given date.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filterMeetup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all meet-up whose date matches "
            + " the given date and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD yyyy-mm-dd\n"
            + "Example: " + COMMAND_WORD + "2022-12-12";

    private final MeetUpContainsKeywordsPredicate predicate;

    public FilterCommand(MeetUpContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMeetUpList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_MEETUPS_LISTED_OVERVIEW, model.getFilteredMeetUpList().size()));
    }

}
