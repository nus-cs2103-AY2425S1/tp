package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;

/**
 * Changes the remark of an existing person in the address book.
 */
public class ViewStatusCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": views the status of the specified candidate "
            + "by the name and job used in the last person listing. "
            + "Parameters: n/ [NAME]\n"
            + "j/ [JOB]\n"
            + "Example: " + COMMAND_WORD
            + "n/ John Doe."
            + "j/ Software Engineer";
    public final Name name;
    public final String job;

    /**
     * @param name Name of the candidate
     * @param job Job the candidate is applying for
     */
    public ViewStatusCommand(Name name, String job) {
        this.name = name;
        this.job = job;
    }

    /**
     * Throws a simple exception
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("Remark command not implemented yet");
    }
}
