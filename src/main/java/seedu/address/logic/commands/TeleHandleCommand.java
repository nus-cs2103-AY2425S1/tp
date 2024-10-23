package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.TelegramHandle;

/**
 * Adds the telegram handle of a person
 */
public class TeleHandleCommand extends Command {

    public static final String COMMAND_WORD = "tele";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the telegram handle of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing telegram handle will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "h/ [TELEGRAM HANDLE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "h/@bob123";

    public static final String MESSAGE_ADD_TELEHANDLE_SUCCESS = "Added telegram handle to Person: %1$s";
    public static final String MESSAGE_DELETE_TELEHANDLE_SUCCESS = "Removed remark from Person: %1$s";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET =
            "TeleHandle command not implemented yet";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Telegram Handle: %2$s";

    private final Index index;
    private final TelegramHandle telegramHandle;

    /**
     * @param index of the person in the filtered person list to edit the telegram handle
     * @param telegramHandle of the person to be updated to
     */
    public TeleHandleCommand(Index index, TelegramHandle telegramHandle) {
        requireAllNonNull(index, telegramHandle);

        this.index = index;
        this.telegramHandle = telegramHandle;
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
                telegramHandle, personToEdit.getModuleName(), personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    private String generateSuccessMessage(Person personToEdit) {
        String message = !telegramHandle.value.isEmpty()
                ? MESSAGE_ADD_TELEHANDLE_SUCCESS
                : MESSAGE_DELETE_TELEHANDLE_SUCCESS;
        return String.format(message, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TeleHandleCommand)) {
            return false;
        }

        TeleHandleCommand e = (TeleHandleCommand) other;
        return index.equals(e.index) && telegramHandle.equals(e.telegramHandle);
    }
}
