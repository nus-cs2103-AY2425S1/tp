package seedu.address.logic.commands;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;

/**
 * Enable rsvp toggling for guests
 */
public class RsvpCommand extends Command {
    public static final String COMMAND_WORD = "rsvp";

    public static final String MESSAGE_SUCCESS = "Guest RSVP'ed";
    public static final String MESSAGE_USAGE = "Toggle /Untoggle RSVP for a guest\n";
    private final Index index;
    private final boolean rsvp;


    public RsvpCommand(Index index, boolean rsvp) {
        this.index = index;
        this.rsvp = rsvp;
    }
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
