package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.EcName;
import seedu.address.model.person.Person;

/**
 * Adds an emergency contact name to an existing person in the address book.
 */
public class AddEcNameCommand extends Command {

    public static final String COMMAND_WORD = "addEcName";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an emergency contact name to the student "
            + "identified by the index.\n"
            + "Parameters: [INDEX] en/EMERGENCY_CONTACT\n"
            + "Example: " + COMMAND_WORD + " 1 en/John Doe";

    public static final String MESSAGE_ADD_ECNAME_SUCCESS = "Added emergency contact name for Person: %1$s";
    public static final String MESSAGE_DELETE_ECNAME_SUCCESS = "Removed emergency contact name from Person: %1$s";

    private final Index index;
    private final EcName ecName;

    /**
     * @param index of the person in the filtered person list to edit the emergency contact name
     * @param ecName emergency contact name of the person to be updated to
     */
    public AddEcNameCommand(Index index, EcName ecName) {
        requireAllNonNull(index, ecName);

        this.index = index;
        this.ecName = ecName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRegisterNumber(), personToEdit.getSex(),
                personToEdit.getStudentClass(), ecName, personToEdit.getEcNumber(), personToEdit.getExams(),
                personToEdit.getTags(), personToEdit.getAttendances(), personToEdit.getSubmissions());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether
     * the emergency contact name is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !ecName.value.isEmpty() ? MESSAGE_ADD_ECNAME_SUCCESS : MESSAGE_DELETE_ECNAME_SUCCESS;
        return String.format(message, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddEcNameCommand)) {
            return false;
        }

        AddEcNameCommand e = (AddEcNameCommand) other;
        return index.equals(e.index)
                && ecName.equals(e.ecName);
    }
}

