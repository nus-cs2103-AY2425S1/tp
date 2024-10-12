package hallpointer.address.logic.commands;

import static hallpointer.address.logic.parser.CliSyntax.PREFIX_DATE;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_POINTS;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_SESSION_NAME;
import static java.util.Objects.requireNonNull;

import hallpointer.address.commons.util.ToStringBuilder;
import hallpointer.address.logic.Messages;
import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.model.Model;
import hallpointer.address.model.session.Session;

public class AddSessionCommand extends Command {
    public static final String COMMAND_WORD = "add_session";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a session to the address book. "
            + "Parameters: "
            + PREFIX_SESSION_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_POINTS + "POINTS "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SESSION_NAME + "Rehearsal "
            + PREFIX_DATE + "2024-09-19 "
            + PREFIX_POINTS + "2";

    public static final String MESSAGE_SUCCESS = "Session %1$s on %2$s for %3$d points added successfully.";
    public static final String MESSAGE_DUPLICATE_SESSION = "Error: Session already exists.";
    private final Session toAdd;

    /**
     * Creates an AddSessionCommand to add the specified {@code Session}
     */
    public AddSessionCommand(Session session) {
        requireNonNull(session);
        toAdd = session;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasSession(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SESSION);
        }

        model.addSession(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getSessionName(), toAdd.getDate(), toAdd.getPoints()));
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
        return toAdd.equals(otherAddSessionCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}