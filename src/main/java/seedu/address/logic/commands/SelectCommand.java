package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSONS_DISPLAYED_INDEX;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.SelectPredicate;

/**
 * Selects the persons in the address book by the index numbers of the last shown list
 * Strictly only one space between the numbers
 */
public class SelectCommand extends Command {
    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Selects the persons based on the indexes \n"
            + "Parameters: INDEX [MORE_INDEXES]...\n"
            + "Example: " + COMMAND_WORD + " 1 2 7";

    private static final String LOG_EXECUTING_SELECT_COMMAND = "Executing SelectCommand with indexes: %s";
    private static final String LOG_INVALID_INDEXES = "Invalid indexes detected: ";
    private static final String LOG_SELECT_COMMAND_SUCCESS = "SelectCommand"
            + " executed successfully with selected persons: %s";

    private static final String ASSERT_INDEX_LIST_NOT_NULL = "Index list should not be null";
    private static final String ASSERT_INDEX_LIST_AT_LEAST_ONE_INDEX = "Index list should contain at least one index";
    private static final String ASSERT_INDEX_NOT_NULL = "Index should not be null";

    private static final Logger logger = LogsCenter.getLogger(SelectCommand.class);

    private List<Person> persons = new ArrayList<>();
    private List<Index> invalidIndexes = new ArrayList<>();
    private List<Index> indexes;
    private SelectPredicate selectPredicate;

    /**
     * Constructs a {@code SelectCommand} with the specified list of indexes to be selected.
     *
     * @param indexes A list of {@code Index} objects representing the indexes to be processed by this command.
     *                Must not be null.
     * @throws NullPointerException if {@code indexes} is null.
     */
    public SelectCommand(List<Index> indexes) {
        requireNonNull(indexes);
        this.indexes = indexes;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        assert !indexes.isEmpty() : ASSERT_INDEX_LIST_AT_LEAST_ONE_INDEX;

        logger.info(String.format(LOG_EXECUTING_SELECT_COMMAND, formatIndexes(indexes)));

        removeDuplicateIndexes();
        getInvalidIndexes(lastShownList, invalidIndexes);
        handleInvalidIndexes(invalidIndexes);
        addValidPersons(lastShownList);

        //update the displaying list based on the selection command
        selectPredicate = new SelectPredicate(persons);
        model.updateFilteredPersonList(selectPredicate);

        String selectedPersons = formatSelectedPersons(persons);
        logger.info(String.format(LOG_SELECT_COMMAND_SUCCESS, selectedPersons));

        return new CommandResult(
                String.format(Messages.MESSAGE_SELECT_PERSON_SUCCESS, selectedPersons));
    }

    /**
     * Collects persons from the given list based on valid indexes and adds them to the {@code persons} list.
     *
     * <p>This method iterates through each index in {@code indexes} and checks if it is within the bounds of the
     * {@code lastShownList}. For each valid index, the corresponding person is retrieved from {@code lastShownList}
     * and added to {@code persons}. The person's name is logged to track each valid addition.</p>
     *
     * @param lastShownList The list of persons displayed in the last filtered view. Expected to be non-null.
     */
    private void addValidPersons(List<Person> lastShownList) {
        for (Index index : indexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                continue;
            }

            Person person = lastShownList.get(index.getZeroBased());
            persons.add(person);
        }
    }

    /**
     * Removes duplicate entries from the {@code indexes} list and updates it to contain unique elements only.
     * This method modifies the class-level {@code indexes} variable directly.
     */
    private void removeDuplicateIndexes() {
        // Use a HashSet to eliminate duplicates and convert it back to a List
        indexes = new ArrayList<>(new HashSet<>(indexes));
    }

    /**
     * Identifies and adds any indexes that are out of bounds to the {@code invalidIndexes} list.
     *
     * <p>This method iterates through each index in {@code indexes} and checks whether the index is within the bounds
     * of the {@code lastShownList}. If an index is out of bounds, it is added to the {@code invalidIndexes} list.</p>
     *
     * <p>Note that an assertion ensures no {@code null} values are present in the {@code indexes} list.</p>
     *
     * @param lastShownList The list of persons displayed in the last filtered view. Expected to be non-null and have
     *                      the persons being indexed. Used to validate that the indexes are within bounds.
     * @param invalidIndexes The list to which invalid indexes (i.e., out-of-bounds indexes) will be added.
     *                       Should be initialized before calling this method.
     */
    private void getInvalidIndexes(List<Person> lastShownList, List<Index> invalidIndexes) {
        for (Index index : indexes) {
            assert index != null : ASSERT_INDEX_NOT_NULL;

            if (index.getZeroBased() < lastShownList.size()) {
                continue;
            }

            invalidIndexes.add(index);
        }
    }

    /**
     * Checks for any invalid indexes in the given list and handles them by logging a warning and throwing a
     * {@link CommandException} if invalid indexes are present.
     *
     * <p>This method logs the invalid indexes as a warning and throws an exception with details of the invalid indexes.
     * It should be used when validating user input to ensure that the selected indexes are within the bounds
     * of the list.</p>
     *
     * @param invalidIndexes The list of {@link Index} objects that are out of bounds or otherwise invalid.
     *                       If the list is empty, no action is taken.
     * @throws CommandException if invalidIndexes is not empty with a message displaying the indexes.
     */
    private void handleInvalidIndexes(List<Index> invalidIndexes) throws CommandException {
        if (invalidIndexes.isEmpty()) {
            return;
        }

        String invalidIndexesMessage = formatIndexes(invalidIndexes);

        logger.warning(LOG_INVALID_INDEXES + invalidIndexesMessage);

        throw new CommandException(
                String.format(MESSAGE_INVALID_PERSONS_DISPLAYED_INDEX, invalidIndexesMessage));
    }

    /**
     * Formats a list of indexes into a comma-separated string for Select Command result display.
     *
     * @param indexes The list of indexes to be formatted.
     * @return A comma-separated string of index numbers, or an empty string if the list is empty.
     */
    private static String formatIndexes(List<Index> indexes) {
        return indexes.stream()
                .map(Index::getOneBased)
                .map(String::valueOf)
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("none");
    }

    /**
     * Formats a list of selected persons' names into a comma-separated string for Select Command result display.
     *
     * @param persons The list of selected persons.
     * @return A comma-separated string of selected persons' names, or "none" if the list is empty.
     */
    private static String formatSelectedPersons(List<Person> persons) {
        return persons.stream()
                .map(person -> person.getName().toString()) // Convert Name object to String
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("none"); // Fallback if no persons are selected
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SelectCommand)) {
            return false;
        }

        SelectCommand otherSelectCommand = (SelectCommand) other;
        return indexes.equals(otherSelectCommand.indexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("indexes", indexes)
                .toString();
    }
}
