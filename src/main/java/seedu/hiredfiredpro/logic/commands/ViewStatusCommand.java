package seedu.hiredfiredpro.logic.commands;

import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.hiredfiredpro.logic.Messages;
import seedu.hiredfiredpro.logic.commands.exceptions.CommandException;
import seedu.hiredfiredpro.model.Model;
import seedu.hiredfiredpro.model.person.Job;
import seedu.hiredfiredpro.model.person.Name;
import seedu.hiredfiredpro.model.person.Person;

/**
 * Views the status of an existing candidate in HiredFiredPro.
 */
public class ViewStatusCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_VIEW_SUCCESS = "Viewing status of candidate: %1$s";
    public static final String MESSAGE_VIEW_FAILURE = "There are no matching candidates with name: %1$s"
            + " and job: %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the status of a candidate in HiredFiredPro. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_JOB + "JOB \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_JOB + "Software Engineer ";


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

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        return findMatchingPerson(lastShownList);
    }

    /**
     * Finds person with matching name and job in lastShownList
     *
     * @return CommandResult with success message and message with matching person's name and job or
     *     failure message
     */
    private CommandResult findMatchingPerson(List<Person> lastShownList) {
        for (Person person: lastShownList) {
            if (person.hasJobAndStatus(this.name, this.job)) {
                return new CommandResult(String.format(MESSAGE_VIEW_SUCCESS, Messages.formatStatus(person)), true,
                        person);
            }
        }

        return new CommandResult(String.format(MESSAGE_VIEW_FAILURE, this.name, this.job));
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
