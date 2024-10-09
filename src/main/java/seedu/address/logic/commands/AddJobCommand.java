package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.Job;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REQUIREMENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;

public class AddJobCommand extends Command {

    public static final String COMMAND_WORD = "add_job";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a job listing to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "JOBNAME "
            + PREFIX_COMPANY + "COMPANY "
            + PREFIX_SALARY + "SALARY "
            + "[" + PREFIX_REQUIREMENTS + "REQUIREMENTS] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Waiter "
            + PREFIX_COMPANY + "Starbucks "
            + PREFIX_SALARY + "2500 "
            + PREFIX_REQUIREMENTS + "Hands and feet "
            + PREFIX_DESCRIPTION + "At Starbucks, we are looking for someone who brings a lot to the table";

    public static final String MESSAGE_SUCCESS = "New job added: %1$s";
    public static final String MESSAGE_DUPLICATE_JOB = "This job already exists in the address book";

    private final Job toAdd;

    /**
     * Creates an AddJobCommand to add the specified {@code Job}
     */
    public AddJobCommand(Job job) {
        requireNonNull(job);
        toAdd = job;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasJob(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_JOB);
        }

        model.addJob(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddJobCommand)) {
            return false;
        }

        AddJobCommand otherAddJobCommand = (AddJobCommand) other;
        return toAdd.equals(otherAddJobCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
