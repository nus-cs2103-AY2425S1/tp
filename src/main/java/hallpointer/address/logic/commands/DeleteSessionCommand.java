package hallpointer.address.logic.commands;

import static hallpointer.address.logic.parser.CliSyntax.*;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static hallpointer.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static hallpointer.address.model.Model.PREDICATE_SHOW_ALL_MEMBERS;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.commons.util.ToStringBuilder;
import hallpointer.address.logic.Messages;
import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.model.Model;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.session.SessionName;

/**
 * Deletes a session identified using its name from a member identified using its index from the address book.
 */


public class DeleteSessionCommand extends Command {
    public static final String COMMAND_WORD = "delete_session";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the session associated to the member identified by the index number used in the session list. "
            + "Parameters: "
            + PREFIX_SESSION_NAME + "NAME "
            + "[" + PREFIX_MEMBER + "INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SESSION_NAME + "Rehearsal "
            + PREFIX_MEMBER + "1";

    public static final String MESSAGE_DELETE_SESSION_SUCCESS = "Deleted Session: %1$s from %2$s sessions";

    private final List<Index> memberIndexes;
    private final SessionName sessionName;

    /**
     * Creates a DeleteSessionCommand to delete the specified session from the specified list of users.
     *
     * @param targetIndex The index of the session to delete.
     */
    public DeleteSessionCommand(SessionName sessionName, List<Index> memberIndexes) {
        this.memberIndexes = memberIndexes;
        this.sessionName = sessionName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredMemberList();
        // Check if the index is valid
        for (Index index : memberIndexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException("part1");
            }
            Member memberToDeleteFrom = lastShownList.get(index.getZeroBased());
            if (memberToDeleteFrom.getSessions().stream().noneMatch(element -> element.getSessionName().toString().equals(sessionName.toString()))) {
                throw new CommandException(memberToDeleteFrom.toString());
            }
            try {
                memberToDeleteFrom.removeSession(sessionName);
                model.setMember(memberToDeleteFrom, memberToDeleteFrom);
            } catch (Exception e) {
                throw new CommandException("Failed to delete session: " + e.getMessage());
            }
        }
        model.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);


        return new CommandResult(String.format(MESSAGE_DELETE_SESSION_SUCCESS, sessionName.toString(), memberIndexes.size())
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
        return sessionName.equals(otherDeleteSessionCommand.sessionName) && memberIndexes.equals(otherDeleteSessionCommand.memberIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("delete", sessionName.toString())
                .toString();
    }

}
