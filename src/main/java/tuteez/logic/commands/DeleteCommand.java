package tuteez.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import tuteez.commons.core.LogsCenter;
import tuteez.commons.core.index.Index;
import tuteez.commons.util.ToStringBuilder;
import tuteez.logic.Messages;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.person.Name;
import tuteez.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_WORD_ALT = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (short form: " + COMMAND_WORD_ALT + ")"
            + ": Deletes the person identified by the index number used in the displayed person list or by name.\n"
            + "Parameters: INDEX (must be a positive integer) or NAME (must be a valid name in the addressbook)\n"
            + "Example: " + COMMAND_WORD + " 1" + " or " + COMMAND_WORD + " John Doe";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;
    private final Name targetName;
    private final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    /**
     * Creates a DeleteCommand to delete the person at the specified {@code targetIndex} of the displayed list.
     * @param targetIndex the index of the person in the displayed list to delete
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    /**
     * Creates a DeleteCommand to delete the person with the specified {@code targetName}.
     * @param targetName the name of the person to delete
     */
    public DeleteCommand(Name targetName) {
        this.targetIndex = null;
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToDelete;
        if (targetIndex != null) {
            personToDelete = getPersonToDeleteByIndex(model, targetIndex);
        } else {
            personToDelete = getPersonToDeleteByName(model, targetName);
        }

        logger.info("Student deleted - " + personToDelete);
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    private Person getPersonToDeleteByIndex(Model model, Index index) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return lastShownList.get(index.getZeroBased());
    }

    private Person getPersonToDeleteByName(Model model, Name name) throws CommandException {
        Person personToDelete = model.findPersonByName(name);
        if (personToDelete == null) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME, name));
        }
        return personToDelete;
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
        boolean isIndexValid = targetIndex != null && targetIndex.equals(otherDeleteCommand.targetIndex);
        boolean isNameValid = targetName != null && targetName.equals(otherDeleteCommand.targetName);
        return isIndexValid || isNameValid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("targetName", targetName)
                .toString();
    }
}
