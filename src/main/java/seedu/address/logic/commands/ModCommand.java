package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ModuleName;
import seedu.address.model.person.Person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

public class ModCommand extends Command{

    public static final String COMMAND_WORD = "mod";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the module of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "m/ [MOD]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "m/ CS1101S";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Module: %2$s";
    public static final String MESSAGE_ADD_MODULE_NAME_SUCCESS = "Added module name to Person: %1$s";
    public static final String MESSAGE_DELETE_MODULE_NAME_SUCCESS = "Removed module name from Person: %1$s";

    private final Index index;
    private final ModuleName moduleName;

    /**
     * @param index of the person in the filtered person list to edit the module of
     * @param moduleName of the person to be updated to
     */
    public ModCommand(Index index, ModuleName moduleName) {
        requireAllNonNull(index, moduleName);

        this.index = index;
        this.moduleName = moduleName;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(
                personToEdit.getContactType(), personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getTelegramHandle(), moduleName, personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whetehr the module name is added to or removed from
     * {@code personToEdit}
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !(moduleName.getModuleName().isEmpty())
                ? MESSAGE_ADD_MODULE_NAME_SUCCESS
                : MESSAGE_DELETE_MODULE_NAME_SUCCESS;
        return String.format(message, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModCommand)) {
            return false;
        }

        ModCommand e = (ModCommand) other;
        return index.equals(e.index)
                && moduleName.equals(e.moduleName);
    }
}
