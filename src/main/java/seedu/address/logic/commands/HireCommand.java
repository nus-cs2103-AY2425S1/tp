package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Changes the status of a candidate to hired.
 */
public class HireCommand extends Command {

    public static final String COMMAND_WORD = "hire";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the status of the candidate to hired. "
            + "Parameters: n/NAME j/JOB\n"
            + "Example: " + COMMAND_WORD + " n/John Doe j/Software Engineer";

    public static final String MESSAGE_SUCCESS = "Candidate %1$s has been successfully marked as hired.";
    public static final String MESSAGE_ALREADY_HIRED = "Error: Candidate %1$s is already marked as hired.";
    public static final String MESSAGE_NAME_NOT_SPECIFIED = "This candidate does not exist in the address book";
    public static final String MESSAGE_JOB_NOT_FOUND = "Error: Job not found.";

    private final Name name;
    private final Job job;

    /**
     * @param name of the candidate
     * @param job of the candidate
     */
    public HireCommand(Name name, Job job) {
        requireNonNull(name);
        requireNonNull(job);
        this.name = name;
        this.job = job;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToHire = model.findPersonByNameAndJob(this.name, job);

        if (personToHire == null) {
            if (!model.isJobPresent(job)) {
                throw new CommandException(MESSAGE_JOB_NOT_FOUND);
            }
            throw new CommandException(MESSAGE_NAME_NOT_SPECIFIED);
        }

        if (personToHire.isHired()) {
            throw new CommandException(String.format(MESSAGE_ALREADY_HIRED,
                    personToHire.getName()));
        }

        Person personHired = new Person(personToHire.getName(), personToHire.getJob(), personToHire.getPhone(),
                personToHire.getEmail(), personToHire.getSkills(), personToHire.getInterviewScore(),
                personToHire.getTags());
        personHired.markAsHired();
        model.setPerson(personToHire, personHired);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personHired.getName()));
    }

    public Name getName() {
        return name;
    }

    public Job getJob() {
        return job;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof HireCommand otherHireCommand)) {
            return false;
        }

        return name.equals(otherHireCommand.name) && job.equals(otherHireCommand.job);
    }
}
