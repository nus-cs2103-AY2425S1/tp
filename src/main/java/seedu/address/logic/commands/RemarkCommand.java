package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.person.predicates.NricMatchesPredicate;

/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String COMMAND_WORD_SHORT = "r";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remarks section of the patient identified "
            + "by the patient's NRIC. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: NRIC "
            + PREFIX_REMARK + "REMARK\n"
            + "Example: " + COMMAND_WORD + " S1234567A "
            + PREFIX_REMARK + "Wheelchair-bound";
    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";
    private final Nric nric;
    private final Remark remark;
    private final NricMatchesPredicate predicate;


    /**
     * @param nric of the person in the filtered person list to edit the remark
     * @param remark of the person to be updated to
     */
    public RemarkCommand(Nric nric, Remark remark) {
        requireAllNonNull(nric, remark);

        this.nric = nric;
        this.remark = remark;
        this.predicate = new NricMatchesPredicate(nric.toString());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPersonList(predicate);
        if (!model.getFilteredPersonList().isEmpty()) {
            Person personToEdit = model.getFilteredPersonList().get(0);
            Person editedPerson = new Person(
                    personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getNric(),
                    personToEdit.getAddress(), personToEdit.getTriage(), remark, personToEdit.getTags(),
                    personToEdit.getAppointment(), personToEdit.getLogEntries());

            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            return new CommandResult(generateSuccessMessage(editedPerson));
        } else {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(Messages.MESSAGE_NO_PERSON_FOUND);
        }
    }

    /**
     * Generates a command execution success message based on whether
     * the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !remark.value.isEmpty() ? MESSAGE_ADD_REMARK_SUCCESS : MESSAGE_DELETE_REMARK_SUCCESS;
        return String.format(message, personToEdit.getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        RemarkCommand e = (RemarkCommand) other;
        return nric.equals(e.nric)
                && remark.equals(e.remark);
    }
}
