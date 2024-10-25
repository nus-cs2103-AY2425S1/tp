package hallpointer.address.logic.commands;

import static hallpointer.address.logic.parser.CliSyntax.PREFIX_DATE;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_POINTS;
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
import hallpointer.address.model.session.Session;

/**
 * Adds a session to the address book.
 */
public class AddSessionCommand extends Command {
    public static final String COMMAND_WORD = "add_session";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a session to the address book. "
            + "Parameters: "
            + PREFIX_SESSION_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_POINTS + "POINTS "
            + "[" + PREFIX_MEMBER + "INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SESSION_NAME + "Rehearsal "
            + PREFIX_DATE + "24 Oct 2024 "
            + PREFIX_POINTS + "2 "
            + PREFIX_MEMBER + "1";

    public static final String MESSAGE_SUCCESS = "Session %1$s on %2$s for %3$s points "
            + "added successfully with %4$d member attending.";
    public static final String MESSAGE_DUPLICATE_SESSION = "Error: Session already exists.";
    public static final String MESSAGE_INVALID_INDEX = "Error: Invalid index specified.";
    private final Session toAdd;
    private final List<Index> memberIndexes;

    /**
     * Creates an AddSessionCommand to add the specified {@code Session}
     */
    public AddSessionCommand(Session session, Set<Index> memberIndexes) {
        requireNonNull(session);
        requireNonNull(memberIndexes);
        assert !memberIndexes.isEmpty();

        toAdd = session;
        this.memberIndexes = memberIndexes.stream().toList();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Member> lastShownList = model.getFilteredMemberList();
        List<Member> memberToUpdate = new ArrayList<>();
        for (Index index : memberIndexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEX);
            }
            Member member = lastShownList.get(index.getZeroBased());
            if (member.hasSession(toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_SESSION);
            }
            memberToUpdate.add(member);
        }
        for (Member member : memberToUpdate) {
            member.addSession(toAdd);
            model.setMember(member, member);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                toAdd.getSessionName().sessionName,
                toAdd.getDate().fullDate,
                toAdd.getPoints().points,
                memberIndexes.size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddSessionCommand)) {
            return false;
        }

        AddSessionCommand otherAddSessionCommand = (AddSessionCommand) other;
        return toAdd.equals(otherAddSessionCommand.toAdd)
                && memberIndexes.equals(otherAddSessionCommand.memberIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("toAdd", toAdd).toString();
    }

}
