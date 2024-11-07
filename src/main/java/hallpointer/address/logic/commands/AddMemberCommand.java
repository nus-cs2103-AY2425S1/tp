package hallpointer.address.logic.commands;

import static hallpointer.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_TAG;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static java.util.Objects.requireNonNull;

import hallpointer.address.commons.util.ToStringBuilder;
import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.model.Model;
import hallpointer.address.model.member.Member;

/**
 * Adds a member to HallPointer.
 */
public class AddMemberCommand extends Command {

    public static final String COMMAND_WORD = "add_member";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a member to Hall Pointer.\n"
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_TELEGRAM + "TELEGRAM_HANDLE "
        + PREFIX_ROOM + "ROOM_NUMBER "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_ROOM + "4-3-301 "
        + PREFIX_TELEGRAM + "johndoe123 "
        + PREFIX_TAG + "logistics";

    public static final String MESSAGE_SUCCESS = "Member %1$s with room %2$s and Telegram username %3$s "
        + "added successfully.";
    public static final String MESSAGE_DUPLICATE_MEMBER = "This member already exists in Hall Pointer.";

    private final Member toAdd;

    /**
     * Creates an AddMemberCommand to add the specified {@code Member} to HallPointer.
     */
    public AddMemberCommand(Member member) {
        requireNonNull(member);
        toAdd = member;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasMember(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEMBER);
        }

        // Add member to the model
        model.addMember(toAdd);

        // Format the success message with the member's name, room number, and Telegram username
        return new CommandResult(
            String.format(
                MESSAGE_SUCCESS,
                toAdd.getName().value,
                toAdd.getRoom().value,
                toAdd.getTelegram().value
            )
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddMemberCommand)) {
            return false;
        }

        AddMemberCommand otherAddMemberCommand = (AddMemberCommand) other;
        return toAdd.equals(otherAddMemberCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
