package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.DesiredRole;
import seedu.address.model.person.Person;

/**
 * Updates the desired role of an existing person in the address book.
 */
public class DesiredRoleCommand extends Command {

    public static final String COMMAND_WORD = "desiredrole";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the desired role of the person identified "
        + "by the index number used in the displayed person list. "
        + "Existing desired role will be overwritten by the input.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "dr/DESIRED_ROLE\n"
        + "Example: " + COMMAND_WORD + " 1 dr/Project Manager";

    public static final String MESSAGE_UPDATE_DESIRED_ROLE_SUCCESS = "Updated desired role for Person: %1$s";
    public static final String MESSAGE_ADD_DESIRED_ROLE_SUCCESS = "Added desired role to Person: %1$s";
    public static final String MESSAGE_DELETE_DESIRED_ROLE_SUCCESS = "Removed desired role from Person: %1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final DesiredRole desiredRole;

    /**
     * Constructs a {@code DesiredRoleCommand} with the specified index and desired role.
     *
     * @param index       The index of the person in the filtered person list to edit.
     * @param desiredRole The new desired role to set for the person.
     */
    public DesiredRoleCommand(Index index, DesiredRole desiredRole) {
        requireAllNonNull(index, desiredRole);
        this.index = index;
        this.desiredRole = desiredRole;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // Create a new Person object with the updated desired role
        Person editedPerson = createEditedPerson(personToEdit, desiredRole);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);

        // Determine which success message to show
        String successMessage = desiredRole.value.isEmpty()
            ? String.format(MESSAGE_DELETE_DESIRED_ROLE_SUCCESS, editedPerson)
            : String.format(MESSAGE_UPDATE_DESIRED_ROLE_SUCCESS, editedPerson);

        return new CommandResult(successMessage);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code desiredRole}.
     */
    private static Person createEditedPerson(Person personToEdit, DesiredRole desiredRole) {
        assert personToEdit != null;

        return new Person(
            personToEdit.getName(),
            personToEdit.getPhone(),
            personToEdit.getEmail(),
            personToEdit.getAddress(),
            desiredRole,
            personToEdit.getSkills(),
            personToEdit.getExperience(),
            personToEdit.getStatus(),
            personToEdit.getNote(),
            personToEdit.getTags()
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DesiredRoleCommand // instanceof handles nulls
            && index.equals(((DesiredRoleCommand) other).index)
            && desiredRole.equals(((DesiredRoleCommand) other).desiredRole));
    }
}
