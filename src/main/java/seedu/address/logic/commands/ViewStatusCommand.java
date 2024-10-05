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
import seedu.address.model.tag.Tag;


/**
 * View the status of an existing person in the address book.
 */
public class ViewStatusCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_VIEW_SUCCESS = "Viewing status of person: %1$s";
    public static final String MESSAGE_VIEW_FAILURE = "There are no matching persons with name: %1$s"
            + " and job: %2$s";
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
        List<Person> lastShownList = model.getFilteredPersonList();
        Person current;

        return findMatchingPerson(lastShownList);
    }

    /**
     * finds person with matching name and job in lastShownList
     *
     * @return CommandResult with success message and message with matching person's name and job or
     * failure message
     */
    private CommandResult findMatchingPerson(List<Person> lastShownList) {
        for (Person person: lastShownList) {
            if (foundMatchingName(person)) {
                return new CommandResult(String.format(MESSAGE_VIEW_SUCCESS, Messages.formatStatus(person)));
            }
        }

        return new CommandResult(String.format(MESSAGE_VIEW_FAILURE, this.name, this.job));
    }

    private boolean foundMatchingName(Person current) {
        return current.getName().equals(this.name);
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
