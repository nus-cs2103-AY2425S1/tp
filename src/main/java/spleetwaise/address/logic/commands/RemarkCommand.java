package spleetwaise.address.logic.commands;

import static spleetwaise.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static spleetwaise.address.model.AddressBookModel.PREDICATE_SHOW_ALL_PERSONS;
import static spleetwaise.commons.logic.commands.CommandUtil.getPersonByFilteredPersonListIndex;
import static spleetwaise.commons.util.CollectionUtil.requireAllNonNull;

import spleetwaise.address.model.person.Person;
import spleetwaise.address.model.person.Remark;
import spleetwaise.commons.core.index.Index;
import spleetwaise.commons.logic.commands.Command;
import spleetwaise.commons.logic.commands.CommandResult;
import spleetwaise.commons.logic.commands.exceptions.CommandException;
import spleetwaise.commons.model.CommonModelManager;

/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n" + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK]\n" + "Example: " + COMMAND_WORD + " 1 " + PREFIX_REMARK + "Likes to swim.";
    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";

    private final Index index;
    private final Remark remark;

    /**
     * @param index  of the person in the filtered person list to edit the remark
     * @param remark of the person to be updated to
     */
    public RemarkCommand(Index index, Remark remark) {
        requireAllNonNull(index, remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult execute() throws CommandException {
        CommonModelManager model = CommonModelManager.getInstance();

        Person personToEdit = getPersonByFilteredPersonListIndex(model, index);

        Person editedPerson = new Person(personToEdit.getId(), personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), remark, personToEdit.getTags()
        );

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !remark.value.isEmpty() ? MESSAGE_ADD_REMARK_SUCCESS : MESSAGE_DELETE_REMARK_SUCCESS;
        return String.format(message, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand e)) {
            return false;
        }

        // state check
        return index.equals(e.index) && remark.equals(e.remark);
    }
}
