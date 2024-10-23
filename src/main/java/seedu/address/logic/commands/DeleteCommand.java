package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the NRIC number used in the displayed person list.\n"
            + "Parameters: NRIC (must be a valid NRIC)\n"
            + "Example: " + COMMAND_WORD + " S6483749D";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Nric targetNric;

    public DeleteCommand(Nric targetNric) {
        this.targetNric = targetNric;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (!personExistsByNric(targetNric, lastShownList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_NRIC);
        }

        Person personToDelete = findPersonByNric(targetNric, lastShownList);
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetNric.equals(otherDeleteCommand.targetNric);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetNric", targetNric)
                .toString();
    }

    /**
     * Finds a person by their Nric in the given list.
     *
     * @param nric The Nric to search for.
     * @param personList The list of persons to search in.
     * @return The person with the given Nric, or null if not found.
     */
    private Person findPersonByNric(Nric nric, List<Person> personList) {
        for (Person person : personList) {
            if (person.getNric().equals(nric)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Checks if a person with the given Nric exists in the given list.
     *
     * @param nric The Nric to search for.
     * @param personList The list of persons to search in.
     * @return True if a person with the given Nric exists, false otherwise.
     */
    private boolean personExistsByNric(Nric nric, List<Person> personList) {
        for (Person person : personList) {
            if (person.getNric().equals(nric)) {
                return true;
            }
        }
        return false;
    }
}
