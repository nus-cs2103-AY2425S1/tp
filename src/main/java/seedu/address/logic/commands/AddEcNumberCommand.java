package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.EcNumber;
import seedu.address.model.person.Person;

/**
 * Adds an emergency contact number to an existing student in the address book.
 */
public class AddEcNumberCommand extends Command {

    public static final String COMMAND_WORD = "addEcNumber";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an emergency contact number to the student "
            + "identified by the index.\n"
            + "Parameters: [INDEX] ep/[EMERGENCY_CONTACT_NUMBER]\n"
            + "Example: " + COMMAND_WORD + " 1 ep/91234567";
    public static final String MESSAGE_ADD_ECNUMBER_SUCCESS = "Added emergency contact number for %1$s\n"
            + "New Emergency Contact Number: %2$s";
    public static final String MESSAGE_DELETE_ECNUMBER_SUCCESS = "Removed emergency contact number for: %1$s\n%2$s";

    private static final Logger logger = LogsCenter.getLogger(AddEcNumberCommand.class);

    private final Index index;
    private final EcNumber ecNumber;

    /**
     * @param index index of the person in the filtered person list to edit the emergency contact phone.
     * @param ecNumber emergency contact number of the person to be updated to.
     */
    public AddEcNumberCommand(Index index, EcNumber ecNumber) {
        requireAllNonNull(index, ecNumber);

        this.index = index;
        this.ecNumber = ecNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.log(Level.WARNING, "Index is more than size of filtered list.");
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        assert personToEdit != null;

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRegisterNumber(), personToEdit.getSex(),
                personToEdit.getStudentClass(), personToEdit.getEcName(), ecNumber, personToEdit.getExams(),
                personToEdit.getTags(), personToEdit.getAttendances(), personToEdit.getSubmissions());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        logger.log(Level.INFO, "AddEcNumberCommand has been executed.");
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether
     * the emergency contact number is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        assert personToEdit != null;

        String message = !ecNumber.value.isEmpty() ? MESSAGE_ADD_ECNUMBER_SUCCESS : MESSAGE_DELETE_ECNUMBER_SUCCESS;
        return String.format(message, personToEdit.getName(), personToEdit.getEcNumber());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddEcNumberCommand)) {
            return false;
        }

        AddEcNumberCommand otherAddEcNumberCommand = (AddEcNumberCommand) other;
        return index.equals(otherAddEcNumberCommand.index)
                && ecNumber.equals(otherAddEcNumberCommand.ecNumber);
    }
}
