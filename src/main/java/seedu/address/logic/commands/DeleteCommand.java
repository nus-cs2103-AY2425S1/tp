package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameMatchesKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * Deletes a person identified from the address book, using index or keyword.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list or keyword.\n"
            + "OR deletes wedding jobs identified by index number used in displayed wedding list "
            + "from specified person.\n"
            + "Parameters: INDEX (must be a positive integer) or KEYWORD (the name of contact)\n"
            + "Examples:\n" + COMMAND_WORD + " 1\n" + COMMAND_WORD + " alex\n"
            + COMMAND_WORD + "1 w/1 w/2";

    public static final String MESSAGE_DELETE_EMPTY_PERSON_LIST_ERROR = "There is no person to delete.";
    public static final String MESSAGE_DELETE_EMPTY_WEDDING_LIST_ERROR =
            "There is no wedding to assign as the wedding list is empty.\n"
                    + "Please refresh the list with a command (e.g. list, vieww).";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public static final String MESSAGE_REMOVE_WEDDING_JOBS_SUCCESS = "Removed wedding jobs from person: %1$s";

    public static final String MESSAGE_DUPLICATE_HANDLING =
            "Please specify the index of the contact you want to delete.\n"
                    + "Find the index from the list below and type delete INDEX\n"
                    + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_PERSON_IS_CLIENT =
            "Cannot delete this person as they are a client in a wedding.\n"
                    + "Please delete their wedding first.";


    public static final String MESSAGE_PERSON_NOT_ASSIGNED_WEDDING =
            "Cannot unassign wedding(s) from this person because they are not assigned to the specified wedding(s)";

    private final Index targetIndex;
    private final NameMatchesKeywordPredicate predicate;
    private final Set<Index> weddingIndices;


    /**
     * Creates a DeleteCommand object to delete the person at the specified {@code Index} or keyword.
     */
    public DeleteCommand(Index targetIndex, NameMatchesKeywordPredicate predicate, Set<Index> weddingIndices) {
        this.targetIndex = targetIndex;
        this.predicate = predicate;
        this.weddingIndices = weddingIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToDelete;

        if (this.targetIndex != null) {
            personToDelete = getPersonByIndex(model);
        } else {
            personToDelete = getPersonByKeyword(model);
        }

        boolean isDeleteWedding = weddingIndices != null;

        if (personToDelete == null) {
            return new CommandResult(String.format(MESSAGE_DUPLICATE_HANDLING));
        }

        if (isDeleteWedding) {
            checkValidWeddingIndices(model);
            // check if person was assigned to those weddings
            checkIsAssignedWeddings(model, personToDelete);
            // delete those weddings
            removeWeddingJobs(personToDelete, model);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS); // Reset filter
            return new CommandResult(String.format(MESSAGE_REMOVE_WEDDING_JOBS_SUCCESS,
                    Messages.format(personToDelete)));
        } else {
            validatePersonIsNotClient(personToDelete);
            model.deletePerson(personToDelete);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS); // Reset filter
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
        }
    }

    /**
     * Gets the person by index without deleting them.
     */
    private Person getPersonByIndex(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_DELETE_EMPTY_PERSON_LIST_ERROR);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    lastShownList.size()));
        }

        return lastShownList.get(targetIndex.getZeroBased());
    }

    /**
     * Gets the person by keyword without deleting them.
     */
    private Person getPersonByKeyword(Model model) throws CommandException {
        model.updateFilteredPersonList(predicate);
        List<Person> filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            throw new CommandException(MESSAGE_DELETE_EMPTY_PERSON_LIST_ERROR);
        } else if (filteredList.size() == 1) {
            return filteredList.get(0);
        } else {
            return null;
        }
    }

    /**
     * Validates that the person to be deleted is not a client in any wedding.
     *
     * @throws CommandException if the person is a client in a wedding
     */
    private void validatePersonIsNotClient(Person person) throws CommandException {
        if (person.getOwnWedding() != null) {
            throw new CommandException(MESSAGE_PERSON_IS_CLIENT);
        }
    }

    /**
     * Check if wedding indices inputs are valid
     *
     * @param model {@code Model} which the command should operate on
     * @throws CommandException if the list is empty or if the index is invalid
     */
    public void checkValidWeddingIndices(Model model) throws CommandException {
        List<Wedding> lastShownList = model.getFilteredWeddingList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_DELETE_EMPTY_WEDDING_LIST_ERROR);
        }

        for (Index index : weddingIndices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(String.format(Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX,
                        lastShownList.size()));
            }
        }
    }

    /**
     * Checks if person is even assigned to weddings before attempting to remove them.
     *
     * @param personToDelete person to check assigned weddings
     * @param model The model containing the list of weddings.
     * @throws CommandException if person is not assigned weddings
     */
    public void checkIsAssignedWeddings(Model model, Person personToDelete) throws CommandException {
        List<Wedding> weddingList = model.getFilteredWeddingList();
        for (Index index : weddingIndices) {
            Wedding wedding = weddingList.get(index.getZeroBased());
            if (!personToDelete.isAssignedToWeddingNonClient(wedding)) {
                throw new CommandException(MESSAGE_PERSON_NOT_ASSIGNED_WEDDING);
            }
        }
    }

    /**
     * Removes specified wedding jobs from person
     *
     * @param personToDelete person to remove wedding jobs
     * @param model The model containing the list of weddings.
     */
    public void removeWeddingJobs(Person personToDelete, Model model) {
        List<Wedding> weddingList = model.getFilteredWeddingList();
        for (Index index : weddingIndices) {
            personToDelete.removeWeddingJob(weddingList.get(index.getZeroBased()));
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
        if (this.targetIndex != null) {
            return new ToStringBuilder(this)
                    .add("targetIndex", targetIndex)
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("targetKeywords", predicate.toString())
                    .toString();
        }
    }
}
