package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REQUIREMENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.Job;

/**
 * Adds a job to the address book.
 */
public class AddJobCommand extends AddCommand<Job> {

    public static final String ENTITY_WORD = "job";
    public static final String FULL_COMMAND = COMMAND_WORD + " " + ENTITY_WORD;
    public static final String MESSAGE_USAGE = FULL_COMMAND + ": Adds a job listing to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_COMPANY + "COMPANY "
            + PREFIX_SALARY + "SALARY "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_REQUIREMENTS + "REQUIREMENTS]\n"
            + "Example: " + FULL_COMMAND + " "
            + PREFIX_NAME + "Waiter "
            + PREFIX_COMPANY + "Starbucks "
            + PREFIX_SALARY + "2500 "
            + PREFIX_DESCRIPTION + "Looking for someone who brings a lot to the table "
            + PREFIX_REQUIREMENTS + "Strong "
            + PREFIX_REQUIREMENTS + "Pleasant";

    public static final String MESSAGE_SUCCESS = "New job added: %1$s";
    public static final String MESSAGE_DUPLICATE_JOB = "This job already exists in the address book";

    /**
     * Creates an AddJobCommand to add the specified {@code Job}
     */
    public AddJobCommand(Job job) {
        super(job);
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

}
