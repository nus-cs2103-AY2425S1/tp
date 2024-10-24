package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Views the status of an existing person in the address book.
 */
public class ViewStatusCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_VIEW_SUCCESS = "Viewing status of person: %1$s";
    public static final String MESSAGE_VIEW_FAILURE = "There are no matching persons with name: %1$s"
            + " and job: %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the status of a person in the address book. "
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

    private CommandResult findMatchingPerson(List<Person> lastShownList) {
        for (Person person: lastShownList) {
            if (person.hasJobAndStatus(this.name, this.job)) {
                return new CommandResult(String.format(MESSAGE_VIEW_SUCCESS, Messages.formatStatus(person)), true,
                        person);
            }
        }

        return new CommandResult(String.format(MESSAGE_VIEW_FAILURE, this.name, this.job));
    }

    /**
     * Finds person with matching name and job in lastShownList
     *
     * @return CommandResult with success message and message with matching person's name and job or
     *     failure message
     */

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
