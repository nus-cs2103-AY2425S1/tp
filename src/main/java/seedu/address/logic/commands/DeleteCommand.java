package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameMatchesKeywordPredicate;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified from the address book, using index or keyword.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) or KEYWORD (the name of contact)\n"
            + "Example: " + COMMAND_WORD + " 1" + "or " + COMMAND_WORD + " alex";

    public static final String MESSAGE_DELETE_EMPTY_LIST_ERROR = "There is nothing to delete.";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DUPLICATE_HANDLING =
            "Please specify the index of the contact you want to delete.\n"
            + "Find the index from the list below and type delete INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;
    private final NameMatchesKeywordPredicate predicate;

    /**
     * Creates a Delete Command to delete the specified contact
     */
    public DeleteCommand(Index targetIndex, NameMatchesKeywordPredicate predicate) {
        this.targetIndex = targetIndex;
        this.predicate = predicate;
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

    /**
     * Performs delete command logic when the input is an index.
     *
     * @param model {@code Model} which the command should operate on
     * @return the person deleted
     * @throws CommandException if an invalid index is given
     */
    public Person deleteWithIndex(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_DELETE_EMPTY_LIST_ERROR);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    lastShownList.size()));
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return personToDelete;
    }

    /**
     * Performs delete command logic when the input is a {@code String}.
     *
     * @param model {@code Model} which the command should operate on
     * @return the person deleted
     * @throws CommandException if the list filtered using {@code targetKeyword} is empty
     */
    public Person deleteWithKeyword(Model model) throws CommandException {
        model.updateFilteredPersonList(predicate);
        List<Person> filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            throw new CommandException(MESSAGE_DELETE_EMPTY_LIST_ERROR);
        } else if (filteredList.size() == 1) {
            Person personToDelete = filteredList.get(0);
            model.deletePerson(personToDelete);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
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

        // Both commands have null fields
        boolean bothHaveNullIndex = targetIndex == null && otherDeleteCommand.targetIndex == null;
        boolean bothHaveNullPredicates = predicate == null && otherDeleteCommand.predicate == null;

        // Both commands have non-null fields
        boolean bothHaveIndex = targetIndex != null && otherDeleteCommand.targetIndex != null;
        boolean bothHavePredicates = predicate != null && otherDeleteCommand.predicate != null;

        // Case 1: Both have null targetIndex and null predicate
        if (bothHaveNullIndex && bothHaveNullPredicates) {
            return true;
        }

        // Case 2: Both have targetIndex but null predicate
        if (bothHaveIndex && bothHaveNullPredicates) {
            return targetIndex.equals(otherDeleteCommand.targetIndex);
        }

        // Case 3: Both have null targetIndex but have predicate
        if (bothHaveNullIndex && bothHavePredicates) {
            return predicate.equals(otherDeleteCommand.predicate);
        }

        // All other cases are false
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
