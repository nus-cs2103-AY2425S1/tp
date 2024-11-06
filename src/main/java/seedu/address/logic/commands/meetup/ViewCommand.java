package seedu.address.logic.commands.meetup;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETUPS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all meet-ups in the meet-up list.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "Viewing all meet-ups";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMeetUpList(PREDICATE_SHOW_ALL_MEETUPS);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true, false, false);
    }
}
