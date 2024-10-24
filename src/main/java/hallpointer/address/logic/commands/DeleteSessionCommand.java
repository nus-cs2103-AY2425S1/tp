package hallpointer.address.logic.commands;

import static hallpointer.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_SESSION_NAME;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.commons.util.ToStringBuilder;
import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.model.Model;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.session.SessionName;

/**
 * Deletes a session identified using its name from a member identified using its index from the address book.
 */


public class DeleteSessionCommand extends Command {
    public static final String COMMAND_WORD = "delete_session";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the session associated to the member identified by the index number used in the session list. "
            + "Parameters: "
            + PREFIX_SESSION_NAME + "NAME "
            + "[" + PREFIX_MEMBER + "INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SESSION_NAME + "Rehearsal "
            + PREFIX_MEMBER + "1";

    public static final String MESSAGE_DELETE_SESSION_SUCCESS = "Deleted Session: %1$s from %2$s sessions";
    public static final String MESSAGE_INVALID_INDEX = "Error: Invalid index specified.";
    public static final String MESSAGE_DELETE_SESSION_FAIL = "Error: Session %1$s does not exist in member %2$s.";

    private final Set<Index> memberIndexes;
    private final SessionName sessionName;

    /**
     * Creates a DeleteSessionCommand to delete the specified session from the specified list of users.
     *
     * @param sessionName The name of the session to delete.
     * @param memberIndexes The indexes of the members in the list the session can be found in.
     */
    public DeleteSessionCommand(SessionName sessionName, Set<Index> memberIndexes) {
        requireNonNull(sessionName);
        requireNonNull(memberIndexes);
        this.memberIndexes = memberIndexes;
        this.sessionName = sessionName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredMemberList();
        List<Member> memberToUpdate = new ArrayList<>();
        // Check if the index is valid
        for (Index index : memberIndexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEX);
            }
            Member member = lastShownList.get(index.getZeroBased());
            if (member.getSessions().stream().noneMatch(
                    element -> element.getSessionName().toString().equals(sessionName.toString()))) {
                throw new CommandException(String.format(MESSAGE_DELETE_SESSION_FAIL, sessionName.toString(),
                        member.getName().toString()));
            }
            memberToUpdate.add(member);
        }
        for (Member member : memberToUpdate) {
            member.removeSession(sessionName);
            model.setMember(member, member);
        }

        return new CommandResult(
                String.format(MESSAGE_DELETE_SESSION_SUCCESS, sessionName.toString(), memberIndexes.size())
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteSessionCommand)) {
            return false;
        }
        DeleteSessionCommand otherDeleteSessionCommand = (DeleteSessionCommand) other;
        return sessionName.equals(otherDeleteSessionCommand.sessionName)
                && memberIndexes.equals(otherDeleteSessionCommand.memberIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("delete", sessionName)
                .toString();
    }

}
