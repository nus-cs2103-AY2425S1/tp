package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all meetings in the address book to the user.
 */
public class MeetingsCommand extends Command {

    public static final String COMMAND_WORD = "meetings";

    public static final String MESSAGE_SUCCESS = "Listed all meetings:";

    public static final String MESSAGE_NO_MEETINGS = "You currently have no meetings with anUdder. Go touch grass!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        StringBuilder message = new StringBuilder();
        message.append(model.listMeetings());

        CommandResult messageToReturn;

        if (message.isEmpty()) {
            messageToReturn = new CommandResult(MESSAGE_NO_MEETINGS);
        } else {
            messageToReturn = new CommandResult(MESSAGE_SUCCESS + "\n" + message);
        }
        return messageToReturn;
    }
}
