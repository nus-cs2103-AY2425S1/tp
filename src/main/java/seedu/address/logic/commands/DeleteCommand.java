package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) or KEYWORD (the name of contact)\n"
            + "Example: " + COMMAND_WORD + " 1" + "or " + COMMAND_WORD + " alex";

    public static final String DELETE_EMPTY_LIST_ERROR_MESSAGE = "There is nothing to delete";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DUPLICATE_HANDLING =
            "Please specify the index of the contact you want to delete.\n"
            + "Find the index from the list below and type delete INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;
    private final String targetKeyword;

    /**
     * Creates a Delete Command to delete the specified contact
     */
    public DeleteCommand(Index targetIndex, String targetKeyword) {
        this.targetIndex = targetIndex;
        this.targetKeyword = targetKeyword;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.targetIndex != null) {
            Person personToDelete = deleteWithIndex(model);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));

        } else {
            Person personToDelete = deleteWithKeyword(model);

            if (personToDelete != null) {
                return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
            } else {
                return new CommandResult(String.format(MESSAGE_DUPLICATE_HANDLING));
            }
        }
    }

    public Person deleteWithIndex(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(DELETE_EMPTY_LIST_ERROR_MESSAGE);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    lastShownList.size()));
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return personToDelete;
    }

    public Person deleteWithKeyword(Model model) throws CommandException {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Arrays.asList(this.targetKeyword));
        model.updateFilteredPersonList(predicate);
        List<Person> filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            throw new CommandException(DELETE_EMPTY_LIST_ERROR_MESSAGE);
        } else if (filteredList.size() == 1) {
            Person personToDelete = filteredList.get(0);
            model.deletePerson(personToDelete);
            return personToDelete;
        } else {
            return null;
        }
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

        if (targetIndex == null && otherDeleteCommand.targetIndex == null
                && targetKeyword == null && otherDeleteCommand.targetKeyword == null) {
            return true;
        }

        if (targetIndex != null && otherDeleteCommand.targetIndex != null
                && targetKeyword == null && otherDeleteCommand.targetKeyword == null) {
            return targetIndex.equals(otherDeleteCommand.targetIndex);
        }

        if (targetIndex == null && otherDeleteCommand.targetIndex == null
                && targetKeyword != null && otherDeleteCommand.targetKeyword != null) {
            return targetKeyword.equals(otherDeleteCommand.targetKeyword);
        }

        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
