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
import hallpointer.address.model.point.Point;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.session.SessionDate;
import hallpointer.address.model.session.SessionName;

/**
 * Deletes a session identified using its name from the member(s) identified using its displayed index number(s).
 */

public class DeleteSessionCommand extends Command {

    public static final String COMMAND_WORD = "delete_session";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the session from the displayed member(s) with the given index(es).\n"
            + "Parameters: "
            + PREFIX_SESSION_NAME + "NAME "
            + PREFIX_MEMBER + "INDEX "
            + "[" + PREFIX_MEMBER + "INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SESSION_NAME + "Rehearsal "
            + PREFIX_MEMBER + "1";

    public static final String MESSAGE_SUCCESS = "Deleted Session: %1$s from %2$s members.";
    public static final String MESSAGE_INVALID_INDEX = "Error: Invalid index specified.";
    public static final String MESSAGE_SESSION_NOT_IN_MEMBER = "Error: Session %1$s does not exist in member %2$s.";

    private final SessionName sessionName;
    private final Set<Index> memberIndexes;


    /**
     * Creates a DeleteSessionCommand to delete the specified session from the specified list of members.
     *
     * @param sessionName The name of the session to delete.
     * @param memberIndexes The indexes of the selected members.
     */
    public DeleteSessionCommand(SessionName sessionName, Set<Index> memberIndexes) {
        requireNonNull(sessionName);
        requireNonNull(memberIndexes);
        assert !memberIndexes.isEmpty() : "memberIndexes should not be empty";
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
            requireNonNull(member);
            if (member.getSessions().stream().noneMatch(
                    // dummy values to avoid duplicating isSameSession or add circular dependencies via SessionBuilder
                    element -> element.isSameSession(
                            new Session(sessionName, new SessionDate("01 Dec 2010"), new Point("3"))
                    ))) {
                throw new CommandException(String.format(MESSAGE_SESSION_NOT_IN_MEMBER, sessionName.toString(),
                        member.getName().toString()));
            }
            memberToUpdate.add(member);
        }
        for (Member member : memberToUpdate) {
            member.removeSession(sessionName);
            model.setMember(member, member);
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, sessionName.toString(), memberIndexes.size())
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
