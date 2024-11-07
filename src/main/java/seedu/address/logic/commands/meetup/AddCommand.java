package seedu.address.logic.commands.meetup;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDED_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meetup.MeetUp;

/**
 * Adds a meet-up to the meet-up list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meet-up to the application. "
            + "Parameters: "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_INFO + "INFO "
            + PREFIX_FROM + "YYYY-MM-DD HH:mm "
            + PREFIX_TO + "YYYY-MM-DD HH:mm "
            + "[" + PREFIX_ADDED_BUYER + "BUYER NAME]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SUBJECT + "Discuss work plans "
            + PREFIX_INFO + "Meet with Eswen to discuss the March Project "
            + PREFIX_FROM + "2024-02-03 14:00 "
            + PREFIX_TO + "2024-02-03 15:30 "
            + PREFIX_ADDED_BUYER + "Alex Yeoh "
            + PREFIX_ADDED_BUYER + "David Li ";

    public static final String MESSAGE_SUCCESS = "New meet-up added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEETUP = "This meet-up already exists in the application";
    public static final String MESSAGE_INVALID_TO_FROM = "TO ($1%s) must be after FROM ($2%s)";

    private final MeetUp toAdd;

    /**
     * Creates an AddCommand to add the specified {@code MeetUp}
     */
    public AddCommand(MeetUp meetup) {
        requireNonNull(meetup);
        toAdd = meetup;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        requireNonNull(toAdd);

        if (model.hasMeetUp(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETUP);
        }

        if (!toAdd.hasValidToFrom()) {
            throw new CommandException(String.format(MESSAGE_INVALID_TO_FROM,
                    toAdd.getTo(), toAdd.getFrom()));
        }

        model.addMeetUp(toAdd);
        assert(model.hasMeetUp(toAdd)); // verify meet up successfully added
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)),
                false, false, true, false, false);
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

        AddCommand otherAddMeetupCommand = (AddCommand) other;
        return toAdd.equals(otherAddMeetupCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
