package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;



/**
 * View the status of an existing person in the address book.
 */
public class ViewStatusCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_ARGUMENTS = "Name: %1$s, Job: %2$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the status of a person in the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_JOB + "JOB ";

    public final Name name;
    public final Job job;

    /**
     * @param name Name of the candidate
     * @param job Job the candidate is applying for
     */
    public ViewStatusCommand(Name name, Job job) {
        this.name = name;
        this.job = job;
    }

    /**
     * Throws a simple exception
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, this.name, this.job));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewStatusCommand)) {
            return false;
        }

        ViewStatusCommand e = (ViewStatusCommand) other;
        return name.equals(e.name)
                && job.equals(e.job);
    }
}
