package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meetup.MeetUp;

/**
 * Adds a meetup to the address book.
 */
public class AddMeetUpCommand extends Command {

    public static final String COMMAND_WORD = "addm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meetup to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_INFO + "MEETUP INFO "
            + PREFIX_FROM + "FROM "
            + PREFIX_TO + "TO "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_INFO + "Meet with Eswen at 1200pm on Saturday";

    public static final String MESSAGE_SUCCESS = "New Meetup added: %1$s";

    private final MeetUp toAdd;

    /**
     * Creates an AddMeetUpCommand to add the specified {@code MeetUp}
     */
    public AddMeetUpCommand(MeetUp meetup) {
        requireNonNull(meetup);
        toAdd = meetup;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddMeetUpCommand otherAddMeetupCommand = (AddMeetUpCommand) other;
        return toAdd.equals(otherAddMeetupCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
