package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Priority;

/**
 * Command to assign or update the priority of a patient in the address book.
 */
public class PriorityCommand extends Command {

    public static final String COMMAND_WORD = "setPriority";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets "
            + "priority to an existing patient in MediBase3.\n"
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + "!/Priority\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "T0123456F "
            + "!/HIGH";

    public static final String PRIORITY_ASSIGN_SUCCESS = "Assigned priority to Nric: %1$s";
    public static final String PATIENT_DOES_NOT_EXIST = "Patient does not exist in contact list";
    public static final String PRIORITY_SET_TO_NONE_SUCCESS = "Priority has been removed from Nric: %1$s";
    private final Priority priority;
    private final Nric nric;

    /**
     * Creates a PriorityCommand to assign or update a person's priority.
     *
     * @param nric    The NRIC of the person whose priority is to be updated.
     * @param priority The priority to be assigned to the person.
     */
    public PriorityCommand(Nric nric, Priority priority) {
        requireAllNonNull(nric, priority);

        this.priority = priority;
        this.nric = nric;
    }

    /**
     * Executes the command and updates the priority of the specified person in the model.
     * If the person is found in the address book, their priority will be updated and the filtered
     * list of persons will be refreshed. If the person does not exist, a {@code CommandException}
     * will be thrown.
     *
     * @param model The {@code Model} which contains the list of persons and is
     *              responsible for handling data operations.
     * @return A {@code CommandResult} containing the success message after updating the person's priority.
     * @throws CommandException If the person with the specified NRIC does not exist in the address book.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        NricMatchesPredicate nricPredicate = new NricMatchesPredicate(this.nric);
        Optional<Person> personOptional = model.fetchPersonIfPresent(nricPredicate);

        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            Person editedPerson = new Person(
                    person.getName(), person.getPhone(), person.getEmail(),
                    person.getNric(), person.getAddress(), person.getDateOfBirth(),
                    person.getGender(), person.getAllergies(), priority, person.getAppointments(),
                    person.getMedCons());
            model.setPerson(person, editedPerson);
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(generateSuccessMessage(editedPerson));
        } else {
            throw new CommandException(PATIENT_DOES_NOT_EXIST);
        }
    }

    /**
     * Generates a success message based on the edited person and their priority.
     *
     * @param editedPerson The person whose priority was updated.
     * @return A success message indicating the result of the priority update.
     */
    private String generateSuccessMessage(Person editedPerson) {
        String message = !priority.isEmpty() ? PRIORITY_ASSIGN_SUCCESS : PRIORITY_SET_TO_NONE_SUCCESS;
        return String.format(message, editedPerson.getNric().value);
    }

    /**
     * Checks if two PriorityCommand objects are equal.
     *
     * @param other The object to compare with.
     * @return True if both objects are the same or have the same NRIC and priority; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PriorityCommand)) {
            return false;
        }

        PriorityCommand p = (PriorityCommand) other;
        return nric.equals(p.nric)
                && priority.equals(p.priority);
    }
}
