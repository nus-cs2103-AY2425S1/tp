package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Log;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NricMatchesPredicate;

/**
 * Adds a log entry to a person's log list.
 */
public class LogCommand extends Command {
    public static final String COMMAND_WORD = "log";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs information of a patient.\n"
            + "Parameters: NRIC, "
            + "TIMESTAMP (in the format DD-MM-YYYY HH:MM),"
            + "INFO (non-empty)\n"
            + "Example: " + COMMAND_WORD + " S1234567A 25-12-2024 14:30 Attended appointment";

    private final Nric nric;
    private final Log log;
    private final NricMatchesPredicate predicate;

    /**
     * Creates a LogCommand to add the specified {@code Log} to the person at the specified {@code Index}.
     *
     * @param targetNric The nric of the person in the filtered person list.
     * @param log The log entry to add.
     */
    public LogCommand(Nric targetNric, Log log) {
        this.nric = targetNric;
        this.log = log;
        this.predicate = new NricMatchesPredicate(targetNric.toString());
    }

    /**
     * Executes the LogCommand and adds the log entry to the specified person.
     *
     * @param model The model containing the list of persons.
     * @return The result of the command execution.
     * @throws CommandException If the index is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPersonList(predicate);
        if (!model.getFilteredPersonList().isEmpty()) {
            Person personToEdit = model.getFilteredPersonList().get(0);
            Person editedPerson = new Person(
                    personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getNric(),
                    personToEdit.getAddress(), personToEdit.getTriage(), personToEdit.getRemark(),
                    personToEdit.getTags(), personToEdit.getAppointment(), personToEdit.getLogEntries().addLog(log));
            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult("Log added to " + personToEdit.getName());
        } else {
            throw new CommandException(Messages.MESSAGE_NO_PERSON_FOUND);
        }
    }

    /**
     * Checks if this LogCommand is equal to another object.
     *
     * @param other The other object to compare to.
     * @return True if the other object is equal to this LogCommand.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogCommand // instanceof handles nulls
                && nric.equals(((LogCommand) other).nric)
                && log.equals(((LogCommand) other).log));
    }

    /**
     * Returns the string representation of the LogCommand.
     *
     * @return The string representation of the LogCommand.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nric", nric)
                .add("log", log)
                .toString();
    }
}
