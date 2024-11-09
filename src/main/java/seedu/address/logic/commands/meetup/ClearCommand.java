package seedu.address.logic.commands.meetup;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.MeetUpList;
import seedu.address.model.Model;

/**
 * Clears the meet up list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Meet up list has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setMeetUpList(new MeetUpList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
